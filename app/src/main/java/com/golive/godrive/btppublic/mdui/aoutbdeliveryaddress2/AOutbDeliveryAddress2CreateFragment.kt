package com.golive.godrive.btppublic.mdui.aoutbdeliveryaddress2

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.databinding.FragmentAoutbdeliveryaddress2CreateBinding
import com.golive.godrive.btppublic.mdui.BundleKeys
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryaddress2type.AOutbDeliveryAddress2TypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryAddress2Type
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntityTypes
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader
import com.sap.cloud.mobile.odata.Property
import org.slf4j.LoggerFactory

/**
 * A fragment that is used for both update and create for users to enter values for the properties. When used for
 * update, an instance of the entity is required. In the case of create, a new instance of the entity with defaults will
 * be created. The default values may not be acceptable for the OData service.
 * This fragment is either contained in a [AOutbDeliveryAddress2ListActivity] in two-pane mode (on tablets) or a
 * [AOutbDeliveryAddress2DetailActivity] on handsets.
 *
 * Arguments: Operation: [OP_CREATE | OP_UPDATE]
 *            AOutbDeliveryAddress2Type if Operation is update
 */
class AOutbDeliveryAddress2CreateFragment : InterfacedFragment<AOutbDeliveryAddress2Type, FragmentAoutbdeliveryaddress2CreateBinding>() {

    /** AOutbDeliveryAddress2Type object and it's copy: the modifications are done on the copied object. */
    private lateinit var aOutbDeliveryAddress2TypeEntity: AOutbDeliveryAddress2Type
    private lateinit var aOutbDeliveryAddress2TypeEntityCopy: AOutbDeliveryAddress2Type

    /** Indicate what operation to be performed */
    private lateinit var operation: String

    /** aOutbDeliveryAddress2TypeEntity ViewModel */
    private lateinit var viewModel: AOutbDeliveryAddress2TypeViewModel

    /** The update menu item */
    private lateinit var updateMenuItem: MenuItem

    private val isAOutbDeliveryAddress2TypeValid: Boolean
        get() {
            var isValid = true
            fragmentBinding.createUpdateAoutbdeliveryaddress2type.let { linearLayout ->
                for (i in 0 until linearLayout.childCount) {
                    val simplePropertyFormCell = linearLayout.getChildAt(i) as SimplePropertyFormCell
                    val propertyName = simplePropertyFormCell.tag as String
                    val property = EntityTypes.aOutbDeliveryAddress2Type.getProperty(propertyName)
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
                    UIConstants.OP_CREATE -> resources.getString(R.string.title_create_fragment, EntityTypes.aOutbDeliveryAddress2Type.localName)
                    else -> resources.getString(R.string.title_update_fragment) + " " + EntityTypes.aOutbDeliveryAddress2Type.localName

                }
            }
        }

        activity?.let {
            (it as AOutbDeliveryAddress2Activity).isNavigationDisabled = true
            viewModel = ViewModelProvider(it)[AOutbDeliveryAddress2TypeViewModel::class.java]
            viewModel.createResult.observe(this) { result -> onComplete(result) }
            viewModel.updateResult.observe(this) { result -> onComplete(result) }

            aOutbDeliveryAddress2TypeEntity = if (operation == UIConstants.OP_CREATE) {
                createAOutbDeliveryAddress2Type()
            } else {
                viewModel.selectedEntity.value!!
            }

            val workingCopy = when{ (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) -> {
                    savedInstanceState?.getParcelable<AOutbDeliveryAddress2Type>(KEY_WORKING_COPY, AOutbDeliveryAddress2Type::class.java)
                } else -> @Suppress("DEPRECATION") savedInstanceState?.getParcelable<AOutbDeliveryAddress2Type>(KEY_WORKING_COPY)
            }

            if (workingCopy == null) {
                aOutbDeliveryAddress2TypeEntityCopy = aOutbDeliveryAddress2TypeEntity.copy()
                aOutbDeliveryAddress2TypeEntityCopy.entityTag = aOutbDeliveryAddress2TypeEntity.entityTag
                aOutbDeliveryAddress2TypeEntityCopy.oldEntity = aOutbDeliveryAddress2TypeEntity
                aOutbDeliveryAddress2TypeEntityCopy.editLink = aOutbDeliveryAddress2TypeEntity.editLink
            } else {
                aOutbDeliveryAddress2TypeEntityCopy = workingCopy
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        currentActivity.findViewById<ObjectHeader>(R.id.objectHeader)?.let {
            it.visibility = View.GONE
        }
        fragmentBinding.aOutbDeliveryAddress2Type = aOutbDeliveryAddress2TypeEntityCopy
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAoutbdeliveryaddress2CreateBinding.inflate(inflater, container, false)

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
        outState.putParcelable(KEY_WORKING_COPY, aOutbDeliveryAddress2TypeEntityCopy)
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
        if (!isAOutbDeliveryAddress2TypeValid) {
            return false
        }
        (currentActivity as AOutbDeliveryAddress2Activity).isNavigationDisabled = false
        progressBar?.visibility = View.VISIBLE
        when (operation) {
            UIConstants.OP_CREATE -> {
                viewModel.create(aOutbDeliveryAddress2TypeEntityCopy)
            }
            UIConstants.OP_UPDATE -> viewModel.update(aOutbDeliveryAddress2TypeEntityCopy)
        }
        return true
    }

    /**
     * Create a new AOutbDeliveryAddress2Type instance and initialize properties to its default values
     * Nullable property will remain null
     * @return new AOutbDeliveryAddress2Type instance
     */
    private fun createAOutbDeliveryAddress2Type(): AOutbDeliveryAddress2Type {
        val entity = AOutbDeliveryAddress2Type(true)
        return entity
    }

    /** Callback function to complete processing when updateResult or createResult events fired */
    private fun onComplete(result: OperationResult<AOutbDeliveryAddress2Type>) {
        progressBar?.visibility = View.INVISIBLE
        enableUpdateMenuItem(true)
        if (result.error != null) {
            (currentActivity as AOutbDeliveryAddress2Activity).isNavigationDisabled = true
            handleError(result)
        } else {
            if (operation == UIConstants.OP_UPDATE && !currentActivity.resources.getBoolean(R.bool.two_pane)) {
                viewModel.selectedEntity.value = aOutbDeliveryAddress2TypeEntityCopy
            }
            (currentActivity as AOutbDeliveryAddress2Activity).onBackPressedDispatcher.onBackPressed()
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
    private fun handleError(result: OperationResult<AOutbDeliveryAddress2Type>) {
        val errorMessage = when (result.operation) {
            OperationResult.Operation.UPDATE -> getString(R.string.update_failed_detail)
            OperationResult.Operation.CREATE -> getString(R.string.create_failed_detail)
            else -> throw AssertionError()
        }
        showError(errorMessage)
    }


    companion object {
        private val KEY_WORKING_COPY = "WORKING_COPY"
        private val LOGGER = LoggerFactory.getLogger(AOutbDeliveryAddress2Activity::class.java)
    }
}
