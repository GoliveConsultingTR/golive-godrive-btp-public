package com.golive.godrive.btppublic.mdui.aoutbdeliveryitemtext

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.databinding.FragmentAoutbdeliveryitemtextCreateBinding
import com.golive.godrive.btppublic.mdui.BundleKeys
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryitemtexttype.AOutbDeliveryItemTextTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryItemTextType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntityTypes
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader
import com.sap.cloud.mobile.odata.Property
import org.slf4j.LoggerFactory

/**
 * A fragment that is used for both update and create for users to enter values for the properties. When used for
 * update, an instance of the entity is required. In the case of create, a new instance of the entity with defaults will
 * be created. The default values may not be acceptable for the OData service.
 * This fragment is either contained in a [AOutbDeliveryItemTextListActivity] in two-pane mode (on tablets) or a
 * [AOutbDeliveryItemTextDetailActivity] on handsets.
 *
 * Arguments: Operation: [OP_CREATE | OP_UPDATE]
 *            AOutbDeliveryItemTextType if Operation is update
 */
class AOutbDeliveryItemTextCreateFragment : InterfacedFragment<AOutbDeliveryItemTextType, FragmentAoutbdeliveryitemtextCreateBinding>() {

    /** AOutbDeliveryItemTextType object and it's copy: the modifications are done on the copied object. */
    private lateinit var aOutbDeliveryItemTextTypeEntity: AOutbDeliveryItemTextType
    private lateinit var aOutbDeliveryItemTextTypeEntityCopy: AOutbDeliveryItemTextType

    /** Indicate what operation to be performed */
    private lateinit var operation: String

    /** aOutbDeliveryItemTextTypeEntity ViewModel */
    private lateinit var viewModel: AOutbDeliveryItemTextTypeViewModel

    /** The update menu item */
    private lateinit var updateMenuItem: MenuItem

    private val isAOutbDeliveryItemTextTypeValid: Boolean
        get() {
            var isValid = true
            fragmentBinding.createUpdateAoutbdeliveryitemtexttype.let { linearLayout ->
                for (i in 0 until linearLayout.childCount) {
                    val simplePropertyFormCell = linearLayout.getChildAt(i) as SimplePropertyFormCell
                    val propertyName = simplePropertyFormCell.tag as String
                    val property = EntityTypes.aOutbDeliveryItemTextType.getProperty(propertyName)
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
                    UIConstants.OP_CREATE -> resources.getString(R.string.title_create_fragment, EntityTypes.aOutbDeliveryItemTextType.localName)
                    else -> resources.getString(R.string.title_update_fragment) + " " + EntityTypes.aOutbDeliveryItemTextType.localName

                }
            }
        }

        activity?.let {
            (it as AOutbDeliveryItemTextActivity).isNavigationDisabled = true
            viewModel = ViewModelProvider(it)[AOutbDeliveryItemTextTypeViewModel::class.java]
            viewModel.createResult.observe(this) { result -> onComplete(result) }
            viewModel.updateResult.observe(this) { result -> onComplete(result) }

            aOutbDeliveryItemTextTypeEntity = if (operation == UIConstants.OP_CREATE) {
                createAOutbDeliveryItemTextType()
            } else {
                viewModel.selectedEntity.value!!
            }

            val workingCopy = when{ (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) -> {
                    savedInstanceState?.getParcelable<AOutbDeliveryItemTextType>(KEY_WORKING_COPY, AOutbDeliveryItemTextType::class.java)
                } else -> @Suppress("DEPRECATION") savedInstanceState?.getParcelable<AOutbDeliveryItemTextType>(KEY_WORKING_COPY)
            }

            if (workingCopy == null) {
                aOutbDeliveryItemTextTypeEntityCopy = aOutbDeliveryItemTextTypeEntity.copy()
                aOutbDeliveryItemTextTypeEntityCopy.entityTag = aOutbDeliveryItemTextTypeEntity.entityTag
                aOutbDeliveryItemTextTypeEntityCopy.oldEntity = aOutbDeliveryItemTextTypeEntity
                aOutbDeliveryItemTextTypeEntityCopy.editLink = aOutbDeliveryItemTextTypeEntity.editLink
            } else {
                aOutbDeliveryItemTextTypeEntityCopy = workingCopy
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        currentActivity.findViewById<ObjectHeader>(R.id.objectHeader)?.let {
            it.visibility = View.GONE
        }
        fragmentBinding.aOutbDeliveryItemTextType = aOutbDeliveryItemTextTypeEntityCopy
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAoutbdeliveryitemtextCreateBinding.inflate(inflater, container, false)

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
        outState.putParcelable(KEY_WORKING_COPY, aOutbDeliveryItemTextTypeEntityCopy)
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
        if (!isAOutbDeliveryItemTextTypeValid) {
            return false
        }
        (currentActivity as AOutbDeliveryItemTextActivity).isNavigationDisabled = false
        progressBar?.visibility = View.VISIBLE
        when (operation) {
            UIConstants.OP_CREATE -> {
                viewModel.create(aOutbDeliveryItemTextTypeEntityCopy)
            }
            UIConstants.OP_UPDATE -> viewModel.update(aOutbDeliveryItemTextTypeEntityCopy)
        }
        return true
    }

    /**
     * Create a new AOutbDeliveryItemTextType instance and initialize properties to its default values
     * Nullable property will remain null
     * @return new AOutbDeliveryItemTextType instance
     */
    private fun createAOutbDeliveryItemTextType(): AOutbDeliveryItemTextType {
        val entity = AOutbDeliveryItemTextType(true)
        return entity
    }

    /** Callback function to complete processing when updateResult or createResult events fired */
    private fun onComplete(result: OperationResult<AOutbDeliveryItemTextType>) {
        progressBar?.visibility = View.INVISIBLE
        enableUpdateMenuItem(true)
        if (result.error != null) {
            (currentActivity as AOutbDeliveryItemTextActivity).isNavigationDisabled = true
            handleError(result)
        } else {
            if (operation == UIConstants.OP_UPDATE && !currentActivity.resources.getBoolean(R.bool.two_pane)) {
                viewModel.selectedEntity.value = aOutbDeliveryItemTextTypeEntityCopy
            }
            (currentActivity as AOutbDeliveryItemTextActivity).onBackPressedDispatcher.onBackPressed()
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
    private fun handleError(result: OperationResult<AOutbDeliveryItemTextType>) {
        val errorMessage = when (result.operation) {
            OperationResult.Operation.UPDATE -> getString(R.string.update_failed_detail)
            OperationResult.Operation.CREATE -> getString(R.string.create_failed_detail)
            else -> throw AssertionError()
        }
        showError(errorMessage)
    }


    companion object {
        private val KEY_WORKING_COPY = "WORKING_COPY"
        private val LOGGER = LoggerFactory.getLogger(AOutbDeliveryItemTextActivity::class.java)
    }
}
