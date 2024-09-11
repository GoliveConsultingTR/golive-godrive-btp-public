package com.golive.godrive.btppublic.mdui.aoutbdeliveryheader

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.golive.godrive.btppublic.databinding.FragmentAoutbdeliveryheaderDetailBinding
import com.golive.godrive.btppublic.mdui.EntityKeyUtil
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryheadertype.AOutbDeliveryHeaderTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryHeaderType
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader

import com.golive.godrive.btppublic.mdui.aoutbdeliveryheadertext.AOutbDeliveryHeaderTextActivity
import com.golive.godrive.btppublic.mdui.ahandlingunitheaderdelivery.AHandlingUnitHeaderDeliveryActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliverypartner.AOutbDeliveryPartnerActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryitem.AOutbDeliveryItemActivity

/**
 * A fragment representing a single AOutbDeliveryHeaderType detail screen.
 * This fragment is contained in an AOutbDeliveryHeaderActivity.
 */
class AOutbDeliveryHeaderDetailFragment : InterfacedFragment<AOutbDeliveryHeaderType, FragmentAoutbdeliveryheaderDetailBinding>() {

    /** AOutbDeliveryHeaderType entity to be displayed */
    private lateinit var aOutbDeliveryHeaderTypeEntity: AOutbDeliveryHeaderType

    /** Fiori ObjectHeader component used when entity is to be displayed on phone */
    private var objectHeader: ObjectHeader? = null

    /** View model of the entity type that the displayed entity belongs to */
    private lateinit var viewModel: AOutbDeliveryHeaderTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_view_options
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentBinding.handler = this
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAoutbdeliveryheaderDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            currentActivity = it
            viewModel = ViewModelProvider(it)[AOutbDeliveryHeaderTypeViewModel::class.java]
            viewModel.deleteResult.observe(viewLifecycleOwner) { result ->
                onDeleteComplete(result)
            }

            viewModel.selectedEntity.observe(viewLifecycleOwner) { entity ->
                aOutbDeliveryHeaderTypeEntity = entity
                fragmentBinding.aOutbDeliveryHeaderType = entity
                setupObjectHeader()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.update_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aOutbDeliveryHeaderTypeEntity)
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
    private fun onDeleteComplete(result: OperationResult<AOutbDeliveryHeaderType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        result.error?.let {
            showError(getString(R.string.delete_failed_detail))
            return
        }
        listener?.onFragmentStateChange(UIConstants.EVENT_DELETION_COMPLETED, aOutbDeliveryHeaderTypeEntity)
    }


    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAOutbDeliveryHeaderText_to_DeliveryDocumentText(view: View) {
        val intent = Intent(currentActivity, AOutbDeliveryHeaderTextActivity::class.java)
        intent.putExtra("parent", aOutbDeliveryHeaderTypeEntity)
        intent.putExtra("navigation", "to_DeliveryDocumentText")
        startActivity(intent)
    }

    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAHandlingUnitHeaderDelivery_to_HandlingUnitHeaderDelivery(view: View) {
        val intent = Intent(currentActivity, AHandlingUnitHeaderDeliveryActivity::class.java)
        intent.putExtra("parent", aOutbDeliveryHeaderTypeEntity)
        intent.putExtra("navigation", "to_HandlingUnitHeaderDelivery")
        startActivity(intent)
    }

    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAOutbDeliveryPartner_to_DeliveryDocumentPartner(view: View) {
        val intent = Intent(currentActivity, AOutbDeliveryPartnerActivity::class.java)
        intent.putExtra("parent", aOutbDeliveryHeaderTypeEntity)
        intent.putExtra("navigation", "to_DeliveryDocumentPartner")
        startActivity(intent)
    }

    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAOutbDeliveryItem_to_DeliveryDocumentItem(view: View) {
        val intent = Intent(currentActivity, AOutbDeliveryItemActivity::class.java)
        intent.putExtra("parent", aOutbDeliveryHeaderTypeEntity)
        intent.putExtra("navigation", "to_DeliveryDocumentItem")
        startActivity(intent)
    }

    /**
     * Set detail image of ObjectHeader.
     * When the entity does not provides picture, set the first character of the masterProperty.
     */
    private fun setDetailImage(objectHeader: ObjectHeader, aOutbDeliveryHeaderTypeEntity: AOutbDeliveryHeaderType) {
        if (aOutbDeliveryHeaderTypeEntity.getOptionalValue(AOutbDeliveryHeaderType.actualDeliveryRoute) != null && !aOutbDeliveryHeaderTypeEntity.getOptionalValue(AOutbDeliveryHeaderType.actualDeliveryRoute).toString().isEmpty()) {
            objectHeader.detailImageCharacter = aOutbDeliveryHeaderTypeEntity.getOptionalValue(AOutbDeliveryHeaderType.actualDeliveryRoute).toString().substring(0, 1)
        } else {
            objectHeader.detailImageCharacter = "?"
        }
    }

    /**
     * Setup ObjectHeader with an instance of aOutbDeliveryHeaderTypeEntity
     */
    private fun setupObjectHeader() {
        val secondToolbar = currentActivity.findViewById<Toolbar>(R.id.secondaryToolbar)
        if (secondToolbar != null) {
            secondToolbar.title = aOutbDeliveryHeaderTypeEntity.entityType.localName
        } else {
            currentActivity.title = aOutbDeliveryHeaderTypeEntity.entityType.localName
        }

        // Object Header is not available in tablet mode
        objectHeader = currentActivity.findViewById(R.id.objectHeader)
        val dataValue = aOutbDeliveryHeaderTypeEntity.getOptionalValue(AOutbDeliveryHeaderType.actualDeliveryRoute)

        objectHeader?.let {
            it.apply {
                headline = dataValue?.toString()
                subheadline = EntityKeyUtil.getOptionalEntityKey(aOutbDeliveryHeaderTypeEntity)
                body = "You can set the header body text here."
                footnote = "You can set the header footnote here."
                description = "You can add a detailed item description here."
            }
            it.setTag("#tag1", 0)
            it.setTag("#tag3", 2)
            it.setTag("#tag2", 1)

            setDetailImage(it, aOutbDeliveryHeaderTypeEntity)
            it.visibility = View.VISIBLE
        }
    }
}
