package com.golive.godrive.btppublic.mdui.ahandlingunitheaderdelivery

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.golive.godrive.btppublic.databinding.FragmentAhandlingunitheaderdeliveryDetailBinding
import com.golive.godrive.btppublic.mdui.EntityKeyUtil
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.ahandlingunitheaderdeliverytype.AHandlingUnitHeaderDeliveryTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AHandlingUnitHeaderDeliveryType
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader

import com.golive.godrive.btppublic.mdui.ahandlingunititemdelivery.AHandlingUnitItemDeliveryActivity

/**
 * A fragment representing a single AHandlingUnitHeaderDeliveryType detail screen.
 * This fragment is contained in an AHandlingUnitHeaderDeliveryActivity.
 */
class AHandlingUnitHeaderDeliveryDetailFragment : InterfacedFragment<AHandlingUnitHeaderDeliveryType, FragmentAhandlingunitheaderdeliveryDetailBinding>() {

    /** AHandlingUnitHeaderDeliveryType entity to be displayed */
    private lateinit var aHandlingUnitHeaderDeliveryTypeEntity: AHandlingUnitHeaderDeliveryType

    /** Fiori ObjectHeader component used when entity is to be displayed on phone */
    private var objectHeader: ObjectHeader? = null

    /** View model of the entity type that the displayed entity belongs to */
    private lateinit var viewModel: AHandlingUnitHeaderDeliveryTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_view_options
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentBinding.handler = this
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAhandlingunitheaderdeliveryDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            currentActivity = it
            viewModel = ViewModelProvider(it)[AHandlingUnitHeaderDeliveryTypeViewModel::class.java]
            viewModel.deleteResult.observe(viewLifecycleOwner) { result ->
                onDeleteComplete(result)
            }

            viewModel.selectedEntity.observe(viewLifecycleOwner) { entity ->
                aHandlingUnitHeaderDeliveryTypeEntity = entity
                fragmentBinding.aHandlingUnitHeaderDeliveryType = entity
                setupObjectHeader()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.update_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aHandlingUnitHeaderDeliveryTypeEntity)
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
    private fun onDeleteComplete(result: OperationResult<AHandlingUnitHeaderDeliveryType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        result.error?.let {
            showError(getString(R.string.delete_failed_detail))
            return
        }
        listener?.onFragmentStateChange(UIConstants.EVENT_DELETION_COMPLETED, aHandlingUnitHeaderDeliveryTypeEntity)
    }


    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAHandlingUnitItemDelivery_to_HandlingUnitItemDelivery(view: View) {
        val intent = Intent(currentActivity, AHandlingUnitItemDeliveryActivity::class.java)
        intent.putExtra("parent", aHandlingUnitHeaderDeliveryTypeEntity)
        intent.putExtra("navigation", "to_HandlingUnitItemDelivery")
        startActivity(intent)
    }

    /**
     * Set detail image of ObjectHeader.
     * When the entity does not provides picture, set the first character of the masterProperty.
     */
    private fun setDetailImage(objectHeader: ObjectHeader, aHandlingUnitHeaderDeliveryTypeEntity: AHandlingUnitHeaderDeliveryType) {
        if (aHandlingUnitHeaderDeliveryTypeEntity.getOptionalValue(AHandlingUnitHeaderDeliveryType.handlingUnitInternalID) != null && !aHandlingUnitHeaderDeliveryTypeEntity.getOptionalValue(AHandlingUnitHeaderDeliveryType.handlingUnitInternalID).toString().isEmpty()) {
            objectHeader.detailImageCharacter = aHandlingUnitHeaderDeliveryTypeEntity.getOptionalValue(AHandlingUnitHeaderDeliveryType.handlingUnitInternalID).toString().substring(0, 1)
        } else {
            objectHeader.detailImageCharacter = "?"
        }
    }

    /**
     * Setup ObjectHeader with an instance of aHandlingUnitHeaderDeliveryTypeEntity
     */
    private fun setupObjectHeader() {
        val secondToolbar = currentActivity.findViewById<Toolbar>(R.id.secondaryToolbar)
        if (secondToolbar != null) {
            secondToolbar.title = aHandlingUnitHeaderDeliveryTypeEntity.entityType.localName
        } else {
            currentActivity.title = aHandlingUnitHeaderDeliveryTypeEntity.entityType.localName
        }

        // Object Header is not available in tablet mode
        objectHeader = currentActivity.findViewById(R.id.objectHeader)
        val dataValue = aHandlingUnitHeaderDeliveryTypeEntity.getOptionalValue(AHandlingUnitHeaderDeliveryType.handlingUnitInternalID)

        objectHeader?.let {
            it.apply {
                headline = dataValue?.toString()
                subheadline = EntityKeyUtil.getOptionalEntityKey(aHandlingUnitHeaderDeliveryTypeEntity)
                body = "You can set the header body text here."
                footnote = "You can set the header footnote here."
                description = "You can add a detailed item description here."
            }
            it.setTag("#tag1", 0)
            it.setTag("#tag3", 2)
            it.setTag("#tag2", 1)

            setDetailImage(it, aHandlingUnitHeaderDeliveryTypeEntity)
            it.visibility = View.VISIBLE
        }
    }
}
