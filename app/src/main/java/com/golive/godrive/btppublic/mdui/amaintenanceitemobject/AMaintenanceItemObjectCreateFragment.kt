package com.golive.godrive.btppublic.mdui.amaintenanceitemobject

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.databinding.FragmentAmaintenanceitemobjectCreateBinding
import com.golive.godrive.btppublic.mdui.BundleKeys
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.viewmodel.amaintenanceitemobjecttype.AMaintenanceItemObjectTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AMaintenanceItemObjectType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntityTypes
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader
import com.sap.cloud.mobile.odata.Property
import org.slf4j.LoggerFactory

/**
 * A fragment that is used for both update and create for users to enter values for the properties. When used for
 * update, an instance of the entity is required. In the case of create, a new instance of the entity with defaults will
 * be created. The default values may not be acceptable for the OData service.
 * This fragment is either contained in a [AMaintenanceItemObjectListActivity] in two-pane mode (on tablets) or a
 * [AMaintenanceItemObjectDetailActivity] on handsets.
 *
 * Arguments: Operation: [OP_CREATE | OP_UPDATE]
 *            AMaintenanceItemObjectType if Operation is update
 */
class AMaintenanceItemObjectCreateFragment : InterfacedFragment<AMaintenanceItemObjectType, FragmentAmaintenanceitemobjectCreateBinding>() {

    /** AMaintenanceItemObjectType object and it's copy: the modifications are done on the copied object. */
    private lateinit var aMaintenanceItemObjectTypeEntity: AMaintenanceItemObjectType
    private lateinit var aMaintenanceItemObjectTypeEntityCopy: AMaintenanceItemObjectType

    /** Indicate what operation to be performed */
    private lateinit var operation: String

    /** aMaintenanceItemObjectTypeEntity ViewModel */
    private lateinit var viewModel: AMaintenanceItemObjectTypeViewModel

    /** The update menu item */
    private lateinit var updateMenuItem: MenuItem

    private val isAMaintenanceItemObjectTypeValid: Boolean
        get() {
            var isValid = true
            fragmentBinding.createUpdateAmaintenanceitemobjecttype.let { linearLayout ->
                for (i in 0 until linearLayout.childCount) {
                    val simplePropertyFormCell = linearLayout.getChildAt(i) as SimplePropertyFormCell
                    val propertyName = simplePropertyFormCell.tag as String
                    val property = EntityTypes.aMaintenanceItemObjectType.getProperty(propertyName)
                    val value = simplePropertyFormCell.value.toString()
                    if (!isValidProperty(property, value)) {
                        simplePropertyFormCell.setTag(R.id.TAG_HAS_MANDATORY_ERROR, true)
                        val errorMessage = resources.getString(R.string.mandatory_warning)
                        simplePropertyFormCell.isErrorEnabled = true
                        simplePropertyFormCell.error = errorMessage
                        isValid = false
                    } else {
                        if (simplePropertyFormCell.isErrorEnabled) {
                            val hasMandatoryError = simplePropertyFormCell.getTag(R.id.TAG_HAS_MANDATORY_ERROR) as Boolean
                            if (!hasMandatoryError) {
                                isValid = false
                            } else {
                                simplePropertyFormCell.isErrorEnabled = false
                            }
                        }
                        simplePropertyFormCell.setTag(R.id.TAG_HAS_MANDATORY_ERROR, false)
                    }
                }
            }
            return isValid
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_edit_options

        arguments?.let {
            (it.getString(BundleKeys.OPERATION))?.let { operationType ->
                operation = operationType
                activityTitle = when (operationType) {
                    UIConstants.OP_CREATE -> resources.getString(R.string.title_create_fragment, EntityTypes.aMaintenanceItemObjectType.localName)
                    else -> resources.getString(R.string.title_update_fragment) + " " + EntityTypes.aMaintenanceItemObjectType.localName

                }
            }
        }

        activity?.let {
            (it as AMaintenanceItemObjectActivity).isNavigationDisabled = true
            viewModel = ViewModelProvider(it)[AMaintenanceItemObjectTypeViewModel::class.java]
            viewModel.createResult.observe(this) { result -> onComplete(result) }
            viewModel.updateResult.observe(this) { result -> onComplete(result) }

            aMaintenanceItemObjectTypeEntity = if (operation == UIConstants.OP_CREATE) {
                createAMaintenanceItemObjectType()
            } else {
                viewModel.selectedEntity.value!!
            }

            val workingCopy = when{ (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) -> {
                    savedInstanceState?.getParcelable<AMaintenanceItemObjectType>(KEY_WORKING_COPY, AMaintenanceItemObjectType::class.java)
                } else -> @Suppress("DEPRECATION") savedInstanceState?.getParcelable<AMaintenanceItemObjectType>(KEY_WORKING_COPY)
            }

            if (workingCopy == null) {
                aMaintenanceItemObjectTypeEntityCopy = aMaintenanceItemObjectTypeEntity.copy()
                aMaintenanceItemObjectTypeEntityCopy.entityTag = aMaintenanceItemObjectTypeEntity.entityTag
                aMaintenanceItemObjectTypeEntityCopy.oldEntity = aMaintenanceItemObjectTypeEntity
                aMaintenanceItemObjectTypeEntityCopy.editLink = aMaintenanceItemObjectTypeEntity.editLink
            } else {
                aMaintenanceItemObjectTypeEntityCopy = workingCopy
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        currentActivity.findViewById<ObjectHeader>(R.id.objectHeader)?.let {
            it.visibility = View.GONE
        }
        fragmentBinding.aMaintenanceItemObjectType = aMaintenanceItemObjectTypeEntityCopy
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAmaintenanceitemobjectCreateBinding.inflate(inflater, container, false)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.save_item -> {
                updateMenuItem = menuItem
                enableUpdateMenuItem(false)
                onSaveItem()
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(secondaryToolbar != null) secondaryToolbar!!.title = activityTitle else activity?.title = activityTitle
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_WORKING_COPY, aMaintenanceItemObjectTypeEntityCopy)
        super.onSaveInstanceState(outState)
    }

    /** Enables the update menu item based on [enable] */
    private fun enableUpdateMenuItem(enable : Boolean = true) {
        updateMenuItem.also {
            it.isEnabled = enable
            it.icon?.alpha = if(enable) 255 else 130
        }
    }

    /** Saves the entity */
    private fun onSaveItem(): Boolean {
        if (!isAMaintenanceItemObjectTypeValid) {
            return false
        }
        (currentActivity as AMaintenanceItemObjectActivity).isNavigationDisabled = false
        progressBar?.visibility = View.VISIBLE
        when (operation) {
            UIConstants.OP_CREATE -> {
                viewModel.create(aMaintenanceItemObjectTypeEntityCopy)
            }
            UIConstants.OP_UPDATE -> viewModel.update(aMaintenanceItemObjectTypeEntityCopy)
        }
        return true
    }

    /**
     * Create a new AMaintenanceItemObjectType instance and initialize properties to its default values
     * Nullable property will remain null
     * @return new AMaintenanceItemObjectType instance
     */
    private fun createAMaintenanceItemObjectType(): AMaintenanceItemObjectType {
        val entity = AMaintenanceItemObjectType(true)
        return entity
    }

    /** Callback function to complete processing when updateResult or createResult events fired */
    private fun onComplete(result: OperationResult<AMaintenanceItemObjectType>) {
        progressBar?.visibility = View.INVISIBLE
        enableUpdateMenuItem(true)
        if (result.error != null) {
            (currentActivity as AMaintenanceItemObjectActivity).isNavigationDisabled = true
            handleError(result)
        } else {
            if (operation == UIConstants.OP_UPDATE && !currentActivity.resources.getBoolean(R.bool.two_pane)) {
                viewModel.selectedEntity.value = aMaintenanceItemObjectTypeEntityCopy
            }
            (currentActivity as AMaintenanceItemObjectActivity).onBackPressedDispatcher.onBackPressed()
        }
    }

    /** Simple validation: checks the presence of mandatory fields. */
    private fun isValidProperty(property: Property, value: String): Boolean {
        return !(!property.isNullable && value.isEmpty())
    }

    /**
     * Notify user of error encountered while execution the operation
     *
     * @param [result] operation result with error
     */
    private fun handleError(result: OperationResult<AMaintenanceItemObjectType>) {
        val errorMessage = when (result.operation) {
            OperationResult.Operation.UPDATE -> getString(R.string.update_failed_detail)
            OperationResult.Operation.CREATE -> getString(R.string.create_failed_detail)
            else -> throw AssertionError()
        }
        showError(errorMessage)
    }


    companion object {
        private val KEY_WORKING_COPY = "WORKING_COPY"
        private val LOGGER = LoggerFactory.getLogger(AMaintenanceItemObjectActivity::class.java)
    }
}
