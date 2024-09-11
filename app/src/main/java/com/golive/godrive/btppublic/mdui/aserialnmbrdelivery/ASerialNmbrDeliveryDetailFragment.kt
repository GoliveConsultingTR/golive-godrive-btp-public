package com.golive.godrive.btppublic.mdui.aserialnmbrdelivery

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.golive.godrive.btppublic.databinding.FragmentAserialnmbrdeliveryDetailBinding
import com.golive.godrive.btppublic.mdui.EntityKeyUtil
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.aserialnmbrdeliverytype.ASerialNmbrDeliveryTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.ASerialNmbrDeliveryType
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader

import com.golive.godrive.btppublic.mdui.amaintenanceitemobject.AMaintenanceItemObjectActivity

/**
 * A fragment representing a single ASerialNmbrDeliveryType detail screen.
 * This fragment is contained in an ASerialNmbrDeliveryActivity.
 */
class ASerialNmbrDeliveryDetailFragment : InterfacedFragment<ASerialNmbrDeliveryType, FragmentAserialnmbrdeliveryDetailBinding>() {

    /** ASerialNmbrDeliveryType entity to be displayed */
    private lateinit var aSerialNmbrDeliveryTypeEntity: ASerialNmbrDeliveryType

    /** Fiori ObjectHeader component used when entity is to be displayed on phone */
    private var objectHeader: ObjectHeader? = null

    /** View model of the entity type that the displayed entity belongs to */
    private lateinit var viewModel: ASerialNmbrDeliveryTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_view_options
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentBinding.handler = this
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAserialnmbrdeliveryDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            currentActivity = it
            viewModel = ViewModelProvider(it)[ASerialNmbrDeliveryTypeViewModel::class.java]
            viewModel.deleteResult.observe(viewLifecycleOwner) { result ->
                onDeleteComplete(result)
            }

            viewModel.selectedEntity.observe(viewLifecycleOwner) { entity ->
                aSerialNmbrDeliveryTypeEntity = entity
                fragmentBinding.aSerialNmbrDeliveryType = entity
                setupObjectHeader()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.update_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aSerialNmbrDeliveryTypeEntity)
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
    private fun onDeleteComplete(result: OperationResult<ASerialNmbrDeliveryType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        result.error?.let {
            showError(getString(R.string.delete_failed_detail))
            return
        }
        listener?.onFragmentStateChange(UIConstants.EVENT_DELETION_COMPLETED, aSerialNmbrDeliveryTypeEntity)
    }


    @Suppress("UNUSED", "UNUSED_PARAMETER") // parameter is needed because of the xml binding
    fun onNavigationClickedToAMaintenanceItemObject_to_MaintenanceItemObject(view: View) {
        val intent = Intent(currentActivity, AMaintenanceItemObjectActivity::class.java)
        intent.putExtra("parent", aSerialNmbrDeliveryTypeEntity)
        intent.putExtra("navigation", "to_MaintenanceItemObject")
        startActivity(intent)
    }

    /**
     * Set detail image of ObjectHeader.
     * When the entity does not provides picture, set the first character of the masterProperty.
     */
    private fun setDetailImage(objectHeader: ObjectHeader, aSerialNmbrDeliveryTypeEntity: ASerialNmbrDeliveryType) {
        if (aSerialNmbrDeliveryTypeEntity.getOptionalValue(ASerialNmbrDeliveryType.maintenanceItemObjectList) != null && !aSerialNmbrDeliveryTypeEntity.getOptionalValue(ASerialNmbrDeliveryType.maintenanceItemObjectList).toString().isEmpty()) {
            objectHeader.detailImageCharacter = aSerialNmbrDeliveryTypeEntity.getOptionalValue(ASerialNmbrDeliveryType.maintenanceItemObjectList).toString().substring(0, 1)
        } else {
            objectHeader.detailImageCharacter = "?"
        }
    }

    /**
     * Setup ObjectHeader with an instance of aSerialNmbrDeliveryTypeEntity
     */
    private fun setupObjectHeader() {
        val secondToolbar = currentActivity.findViewById<Toolbar>(R.id.secondaryToolbar)
        if (secondToolbar != null) {
            secondToolbar.title = aSerialNmbrDeliveryTypeEntity.entityType.localName
        } else {
            currentActivity.title = aSerialNmbrDeliveryTypeEntity.entityType.localName
        }

        // Object Header is not available in tablet mode
        objectHeader = currentActivity.findViewById(R.id.objectHeader)
        val dataValue = aSerialNmbrDeliveryTypeEntity.getOptionalValue(ASerialNmbrDeliveryType.maintenanceItemObjectList)

        objectHeader?.let {
            it.apply {
                headline = dataValue?.toString()
                subheadline = EntityKeyUtil.getOptionalEntityKey(aSerialNmbrDeliveryTypeEntity)
                body = "You can set the header body text here."
                footnote = "You can set the header footnote here."
                description = "You can add a detailed item description here."
            }
            it.setTag("#tag1", 0)
            it.setTag("#tag3", 2)
            it.setTag("#tag2", 1)

            setDetailImage(it, aSerialNmbrDeliveryTypeEntity)
            it.visibility = View.VISIBLE
        }
    }
}
