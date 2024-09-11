package com.golive.godrive.btppublic.mdui.ahandlingunititemdelivery

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.golive.godrive.btppublic.databinding.FragmentAhandlingunititemdeliveryDetailBinding
import com.golive.godrive.btppublic.mdui.EntityKeyUtil
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.ahandlingunititemdeliverytype.AHandlingUnitItemDeliveryTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AHandlingUnitItemDeliveryType
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader


/**
 * A fragment representing a single AHandlingUnitItemDeliveryType detail screen.
 * This fragment is contained in an AHandlingUnitItemDeliveryActivity.
 */
class AHandlingUnitItemDeliveryDetailFragment : InterfacedFragment<AHandlingUnitItemDeliveryType, FragmentAhandlingunititemdeliveryDetailBinding>() {

    /** AHandlingUnitItemDeliveryType entity to be displayed */
    private lateinit var aHandlingUnitItemDeliveryTypeEntity: AHandlingUnitItemDeliveryType

    /** Fiori ObjectHeader component used when entity is to be displayed on phone */
    private var objectHeader: ObjectHeader? = null

    /** View model of the entity type that the displayed entity belongs to */
    private lateinit var viewModel: AHandlingUnitItemDeliveryTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_view_options
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentBinding.handler = this
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAhandlingunititemdeliveryDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            currentActivity = it
            viewModel = ViewModelProvider(it)[AHandlingUnitItemDeliveryTypeViewModel::class.java]
            viewModel.deleteResult.observe(viewLifecycleOwner) { result ->
                onDeleteComplete(result)
            }

            viewModel.selectedEntity.observe(viewLifecycleOwner) { entity ->
                aHandlingUnitItemDeliveryTypeEntity = entity
                fragmentBinding.aHandlingUnitItemDeliveryType = entity
                setupObjectHeader()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.update_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aHandlingUnitItemDeliveryTypeEntity)
                true
            }
            R.id.delete_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_ASK_DELETE_CONFIRMATION,null)
                true
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }

    /**
     * Completion callback for delete operation
     *
     * @param [result] of the operation
     */
    private fun onDeleteComplete(result: OperationResult<AHandlingUnitItemDeliveryType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        result.error?.let {
            showError(getString(R.string.delete_failed_detail))
            return
        }
        listener?.onFragmentStateChange(UIConstants.EVENT_DELETION_COMPLETED, aHandlingUnitItemDeliveryTypeEntity)
    }


    /**
     * Set detail image of ObjectHeader.
     * When the entity does not provides picture, set the first character of the masterProperty.
     */
    private fun setDetailImage(objectHeader: ObjectHeader, aHandlingUnitItemDeliveryTypeEntity: AHandlingUnitItemDeliveryType) {
        if (aHandlingUnitItemDeliveryTypeEntity.getOptionalValue(AHandlingUnitItemDeliveryType.handlingUnitInternalID) != null && !aHandlingUnitItemDeliveryTypeEntity.getOptionalValue(AHandlingUnitItemDeliveryType.handlingUnitInternalID).toString().isEmpty()) {
            objectHeader.detailImageCharacter = aHandlingUnitItemDeliveryTypeEntity.getOptionalValue(AHandlingUnitItemDeliveryType.handlingUnitInternalID).toString().substring(0, 1)
        } else {
            objectHeader.detailImageCharacter = "?"
        }
    }

    /**
     * Setup ObjectHeader with an instance of aHandlingUnitItemDeliveryTypeEntity
     */
    private fun setupObjectHeader() {
        val secondToolbar = currentActivity.findViewById<Toolbar>(R.id.secondaryToolbar)
        if (secondToolbar != null) {
            secondToolbar.title = aHandlingUnitItemDeliveryTypeEntity.entityType.localName
        } else {
            currentActivity.title = aHandlingUnitItemDeliveryTypeEntity.entityType.localName
        }

        // Object Header is not available in tablet mode
        objectHeader = currentActivity.findViewById(R.id.objectHeader)
        val dataValue = aHandlingUnitItemDeliveryTypeEntity.getOptionalValue(AHandlingUnitItemDeliveryType.handlingUnitInternalID)

        objectHeader?.let {
            it.apply {
                headline = dataValue?.toString()
                subheadline = EntityKeyUtil.getOptionalEntityKey(aHandlingUnitItemDeliveryTypeEntity)
                body = "You can set the header body text here."
                footnote = "You can set the header footnote here."
                description = "You can add a detailed item description here."
            }
            it.setTag("#tag1", 0)
            it.setTag("#tag3", 2)
            it.setTag("#tag2", 1)

            setDetailImage(it, aHandlingUnitItemDeliveryTypeEntity)
            it.visibility = View.VISIBLE
        }
    }
}
