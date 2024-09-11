package com.golive.godrive.btppublic.mdui.amaintenanceitemobject

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.golive.godrive.btppublic.databinding.FragmentAmaintenanceitemobjectDetailBinding
import com.golive.godrive.btppublic.mdui.EntityKeyUtil
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.amaintenanceitemobjecttype.AMaintenanceItemObjectTypeViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AMaintenanceItemObjectType
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader


/**
 * A fragment representing a single AMaintenanceItemObjectType detail screen.
 * This fragment is contained in an AMaintenanceItemObjectActivity.
 */
class AMaintenanceItemObjectDetailFragment : InterfacedFragment<AMaintenanceItemObjectType, FragmentAmaintenanceitemobjectDetailBinding>() {

    /** AMaintenanceItemObjectType entity to be displayed */
    private lateinit var aMaintenanceItemObjectTypeEntity: AMaintenanceItemObjectType

    /** Fiori ObjectHeader component used when entity is to be displayed on phone */
    private var objectHeader: ObjectHeader? = null

    /** View model of the entity type that the displayed entity belongs to */
    private lateinit var viewModel: AMaintenanceItemObjectTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = R.menu.itemlist_view_options
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentBinding.handler = this
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentAmaintenanceitemobjectDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            currentActivity = it
            viewModel = ViewModelProvider(it)[AMaintenanceItemObjectTypeViewModel::class.java]
            viewModel.deleteResult.observe(viewLifecycleOwner) { result ->
                onDeleteComplete(result)
            }

            viewModel.selectedEntity.observe(viewLifecycleOwner) { entity ->
                aMaintenanceItemObjectTypeEntity = entity
                fragmentBinding.aMaintenanceItemObjectType = entity
                setupObjectHeader()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.update_item -> {
                listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aMaintenanceItemObjectTypeEntity)
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
    private fun onDeleteComplete(result: OperationResult<AMaintenanceItemObjectType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        result.error?.let {
            showError(getString(R.string.delete_failed_detail))
            return
        }
        listener?.onFragmentStateChange(UIConstants.EVENT_DELETION_COMPLETED, aMaintenanceItemObjectTypeEntity)
    }


    /**
     * Set detail image of ObjectHeader.
     * When the entity does not provides picture, set the first character of the masterProperty.
     */
    private fun setDetailImage(objectHeader: ObjectHeader, aMaintenanceItemObjectTypeEntity: AMaintenanceItemObjectType) {
        if (aMaintenanceItemObjectTypeEntity.getOptionalValue(AMaintenanceItemObjectType.maintenanceItemObject) != null && !aMaintenanceItemObjectTypeEntity.getOptionalValue(AMaintenanceItemObjectType.maintenanceItemObject).toString().isEmpty()) {
            objectHeader.detailImageCharacter = aMaintenanceItemObjectTypeEntity.getOptionalValue(AMaintenanceItemObjectType.maintenanceItemObject).toString().substring(0, 1)
        } else {
            objectHeader.detailImageCharacter = "?"
        }
    }

    /**
     * Setup ObjectHeader with an instance of aMaintenanceItemObjectTypeEntity
     */
    private fun setupObjectHeader() {
        val secondToolbar = currentActivity.findViewById<Toolbar>(R.id.secondaryToolbar)
        if (secondToolbar != null) {
            secondToolbar.title = aMaintenanceItemObjectTypeEntity.entityType.localName
        } else {
            currentActivity.title = aMaintenanceItemObjectTypeEntity.entityType.localName
        }

        // Object Header is not available in tablet mode
        objectHeader = currentActivity.findViewById(R.id.objectHeader)
        val dataValue = aMaintenanceItemObjectTypeEntity.getOptionalValue(AMaintenanceItemObjectType.maintenanceItemObject)

        objectHeader?.let {
            it.apply {
                headline = dataValue?.toString()
                subheadline = EntityKeyUtil.getOptionalEntityKey(aMaintenanceItemObjectTypeEntity)
                body = "You can set the header body text here."
                footnote = "You can set the header footnote here."
                description = "You can add a detailed item description here."
            }
            it.setTag("#tag1", 0)
            it.setTag("#tag3", 2)
            it.setTag("#tag2", 1)

            setDetailImage(it, aMaintenanceItemObjectTypeEntity)
            it.visibility = View.VISIBLE
        }
    }
}
