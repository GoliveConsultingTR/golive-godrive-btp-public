package com.golive.godrive.btppublic.mdui.aoutbdeliverypartner

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.CheckBox
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.golive.godrive.btppublic.service.SAPServiceManager
import com.golive.godrive.btppublic.app.SAPWizardApplication
import com.golive.godrive.btppublic.databinding.ElementEntityitemListBinding
import com.golive.godrive.btppublic.databinding.FragmentEntityitemListBinding
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.viewmodel.EntityViewModelFactory
import com.golive.godrive.btppublic.viewmodel.aoutbdeliverypartnertype.AOutbDeliveryPartnerTypeViewModel
import com.golive.godrive.btppublic.repository.OperationResult
import com.golive.godrive.btppublic.mdui.UIConstants
import com.golive.godrive.btppublic.mdui.EntitySetListActivity.EntitySetName
import com.golive.godrive.btppublic.mdui.InterfacedFragment
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryPartnerType
import com.sap.cloud.mobile.fiori.`object`.ObjectCell
import com.sap.cloud.mobile.fiori.`object`.ObjectHeader
import com.sap.cloud.mobile.odata.EntityValue
import org.slf4j.LoggerFactory

/**
 * An activity representing a list of AOutbDeliveryPartnerType. This activity has different presentations for handset and tablet-size
 * devices. On handsets, the activity presents a list of items, which when touched, lead to a view representing
 * AOutbDeliveryPartnerType details. On tablets, the activity presents the list of AOutbDeliveryPartnerType and AOutbDeliveryPartnerType details side-by-side using two
 * vertical panes.
 */

class AOutbDeliveryPartnerListFragment : InterfacedFragment<AOutbDeliveryPartnerType, FragmentEntityitemListBinding>() {

    /**
     * Service manager to provide root URL of OData Service for Glide to load images if there are media resources
     * associated with the entity type
     */
    private var sapServiceManager: SAPServiceManager? = null

    /**
     * List adapter to be used with RecyclerView containing all instances of aOutbDeliveryPartner
     */
    private var adapter: AOutbDeliveryPartnerTypeListAdapter? = null

    private lateinit var refreshLayout: SwipeRefreshLayout
    private var actionMode: ActionMode? = null
    private var isInActionMode: Boolean = false
    private val selectedItems = ArrayList<Int>()

    /**
     * View model of the entity type
     */
    private lateinit var viewModel: AOutbDeliveryPartnerTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTitle = getString(EntitySetName.AOutbDeliveryPartner.titleId)
        menu = R.menu.itemlist_menu
        savedInstanceState?.let {
            isInActionMode = it.getBoolean("ActionMode")
        }

        sapServiceManager = (currentActivity.application as SAPWizardApplication).sapServiceManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        currentActivity.findViewById<ObjectHeader>(R.id.objectHeader)?.let {
            it.visibility = View.GONE
        }
        return fragmentBinding.root
    }

    override  fun  initBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentEntityitemListBinding.inflate(inflater, container, false)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_refresh -> {
                refreshLayout.isRefreshing = true
                refreshListData()
                true
            }
            else -> return super.onMenuItemSelected(menuItem)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("ActionMode", isInActionMode)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        currentActivity.title = activityTitle

        fragmentBinding.itemList?.let {
            this.adapter = AOutbDeliveryPartnerTypeListAdapter(currentActivity, it)
            it.adapter = this.adapter
        } ?: throw AssertionError()

        setupRefreshLayout()
        refreshLayout.isRefreshing = true

        navigationPropertyName = currentActivity.intent.getStringExtra("navigation")
        parentEntityData = when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) -> {
                currentActivity.intent.getParcelableExtra("parent", Parcelable::class.java)
            }
            else -> @Suppress("DEPRECATION") currentActivity.intent.getParcelableExtra("parent")
        }

        fragmentBinding.fab?.let {
            it.contentDescription = getString(R.string.add_new) + " AOutbDeliveryPartnerType"
            if (navigationPropertyName != null && parentEntityData != null) {
                it.hide()
            } else {
                it.setOnClickListener {
                    listener?.onFragmentStateChange(UIConstants.EVENT_CREATE_NEW_ITEM, null)
                }
            }
        }

        sapServiceManager?.openODataStore {
            prepareViewModel()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshListData()
    }

    /** Initializes the view model and add observers on it */
    private fun prepareViewModel() {
        viewModel = if( navigationPropertyName != null && parentEntityData != null ) {
            ViewModelProvider(currentActivity, EntityViewModelFactory(currentActivity.application, navigationPropertyName!!, parentEntityData!!))
                .get(AOutbDeliveryPartnerTypeViewModel::class.java)
        } else {
            ViewModelProvider(currentActivity).get(AOutbDeliveryPartnerTypeViewModel::class.java)
        }
        viewModel.observableItems.observe(viewLifecycleOwner, Observer<List<AOutbDeliveryPartnerType>> { items ->
            items?.let { entityList ->
                adapter?.let { listAdapter ->
                    listAdapter.setItems(entityList)

                    var item = viewModel.selectedEntity.value?.let { containsItem(entityList, it) }
                    if (item == null) {
                        item = if (entityList.isEmpty()) null else entityList[0]
                    }

                    item?.let {
                        viewModel.inFocusId = listAdapter.getItemIdForAOutbDeliveryPartnerType(it)
                        if (currentActivity.resources.getBoolean(R.bool.two_pane)) {
                            viewModel.setSelectedEntity(it)
                            if(!isInActionMode && !(currentActivity as AOutbDeliveryPartnerActivity).isNavigationDisabled) {
                                listener?.onFragmentStateChange(UIConstants.EVENT_ITEM_CLICKED, it)
                            }
                        }
                        listAdapter.notifyDataSetChanged()
                    }

                    if( item == null ) hideDetailFragment()
                }

                refreshLayout.isRefreshing = false
            }
        })

        viewModel.readResult.observe(viewLifecycleOwner, Observer {
            if (refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = false
            }
        })

        viewModel.deleteResult.observe(viewLifecycleOwner, Observer {
            this.onDeleteComplete(it!!)
        })
    }

    /**
     * Checks if [item] exists in the list [items] based on the item id, which in offline is the read readLink,
     * while for online the primary key.
     */
    private fun containsItem(items: List<AOutbDeliveryPartnerType>, item: AOutbDeliveryPartnerType) : AOutbDeliveryPartnerType? {
        return items.find { entry ->
            adapter?.getItemIdForAOutbDeliveryPartnerType(entry) == adapter?.getItemIdForAOutbDeliveryPartnerType(item)
        }
    }

    /** when no items return from server, hide the detail fragment on tablet */
    private fun hideDetailFragment() {
        currentActivity.supportFragmentManager.findFragmentByTag(UIConstants.DETAIL_FRAGMENT_TAG)?.let {
            currentActivity.supportFragmentManager.beginTransaction()
                .remove(it).commit()
        }
        secondaryToolbar?.let {
            it.menu.clear()
            it.title = ""
        }
        currentActivity.findViewById<ObjectHeader>(R.id.objectHeader)?.let {
            it.visibility = View.GONE
        }
    }

    /** Completion callback for delete operation  */
    private fun onDeleteComplete(result: OperationResult<AOutbDeliveryPartnerType>) {
        progressBar?.let {
            it.visibility = View.INVISIBLE
        }
        viewModel.removeAllSelected()
        actionMode?.let {
            it.finish()
            isInActionMode = false
        }

        result.error?.let {
            handleDeleteError()
            return
        }
        refreshListData()
    }

    /** Handles the deletion error */
    private fun handleDeleteError() {
        showError(resources.getString(R.string.delete_failed_detail))
        refreshLayout.isRefreshing = false
    }

    /** sets up the refresh layout */
    private fun setupRefreshLayout() {
        refreshLayout = fragmentBinding.swiperefresh
        refreshLayout.setColorSchemeColors(UIConstants.FIORI_STANDARD_THEME_GLOBAL_DARK_BASE)
        refreshLayout.setProgressBackgroundColorSchemeColor(UIConstants.FIORI_STANDARD_THEME_BACKGROUND)
        refreshLayout.setOnRefreshListener(this::refreshListData)
    }

    /** Refreshes the list data */
    internal fun refreshListData() {
        navigationPropertyName?.let { _navigationPropertyName ->
            parentEntityData?.let { _parentEntityData ->
                viewModel.refresh(_parentEntityData as EntityValue, _navigationPropertyName)
            }
        } ?: run {
            viewModel.refresh()
        }
        adapter?.notifyDataSetChanged()
    }

    /** Sets the id for the selected item into view model */
    private fun setItemIdSelected(itemId: Int): AOutbDeliveryPartnerType? {
        viewModel.observableItems.value?.let { aOutbDeliveryPartner ->
            if (aOutbDeliveryPartner.isNotEmpty()) {
                adapter?.let {
                    viewModel.inFocusId = it.getItemIdForAOutbDeliveryPartnerType(aOutbDeliveryPartner[itemId])
                    return aOutbDeliveryPartner[itemId]
                }
            }
        }
        return null
    }

    /** Sets the detail image for the given [viewHolder] */
    private fun setDetailImage(viewHolder: AOutbDeliveryPartnerTypeListAdapter.ViewHolder<ElementEntityitemListBinding>, aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType?) {
        if (isInActionMode) {
            val drawable: Int = if (viewHolder.isSelected) {
                R.drawable.ic_sap_icon_done
            } else {
                R.drawable.ic_sap_icon_shape_circle
            }
            viewHolder.objectCell.prepareDetailImageView().scaleType = ImageView.ScaleType.FIT_CENTER
            Glide.with(currentActivity)
                .load(resources.getDrawable(drawable, null))
                .apply(RequestOptions().fitCenter())
                .into(viewHolder.objectCell.prepareDetailImageView())
        } else if (!viewHolder.masterPropertyValue.isNullOrEmpty()) {
            viewHolder.objectCell.detailImageCharacter = viewHolder.masterPropertyValue?.substring(0, 1)
        } else {
            viewHolder.objectCell.detailImageCharacter = "?"
        }
    }

    /**
     * Represents the listener to start the action mode. 
     */
    inner class OnActionModeStartClickListener(internal var holder: AOutbDeliveryPartnerTypeListAdapter.ViewHolder<ElementEntityitemListBinding>) : View.OnClickListener, View.OnLongClickListener {

        override fun onClick(view: View) {
            onAnyKindOfClick()
        }

        override fun onLongClick(view: View): Boolean {
            return onAnyKindOfClick()
        }

        /** callback function for both normal and long click of an entity */
        private fun onAnyKindOfClick(): Boolean {
            val isNavigationDisabled = (activity as AOutbDeliveryPartnerActivity).isNavigationDisabled
            if (isNavigationDisabled) {
                Toast.makeText(activity, "Please save your changes first...", Toast.LENGTH_LONG).show()
            } else {
                if (!isInActionMode) {
                    actionMode = (currentActivity as AppCompatActivity).startSupportActionMode(AOutbDeliveryPartnerListActionMode())
                    adapter?.notifyDataSetChanged()
                }
                holder.isSelected = !holder.isSelected
            }
            return true
        }
    }

    /**
     * Represents list action mode.
     */
    inner class AOutbDeliveryPartnerListActionMode : ActionMode.Callback {
        override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
            isInActionMode = true
            fragmentBinding.fab?.let {
                it.hide()
            }
            //(currentActivity as AOutbDeliveryPartnerActivity).onSetActionModeFlag(isInActionMode)
            val inflater = actionMode.menuInflater
            inflater.inflate(R.menu.itemlist_view_options, menu)

            hideDetailFragment()
            return true
        }

        override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(actionMode: ActionMode, menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.update_item -> {
                    val aOutbDeliveryPartnerTypeEntity = viewModel.getSelected(0)
                    if (viewModel.numberOfSelected() == 1 && aOutbDeliveryPartnerTypeEntity != null) {
                        isInActionMode = false
                        actionMode.finish()
                        viewModel.setSelectedEntity(aOutbDeliveryPartnerTypeEntity)
                        if(currentActivity.resources.getBoolean(R.bool.two_pane)) {
                            //make sure 'view' is under 'crt/update',
                            //so after done or back, the right panel has things to view
                            listener?.onFragmentStateChange(UIConstants.EVENT_ITEM_CLICKED, aOutbDeliveryPartnerTypeEntity)
                        }
                        listener?.onFragmentStateChange(UIConstants.EVENT_EDIT_ITEM, aOutbDeliveryPartnerTypeEntity)
                    }
                    true
                }
                R.id.delete_item -> {
                    listener?.onFragmentStateChange(UIConstants.EVENT_ASK_DELETE_CONFIRMATION,null)
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(actionMode: ActionMode) {
            isInActionMode = false
            if (!(navigationPropertyName != null && parentEntityData != null)) {
                fragmentBinding.fab?.let {
                    it.show()
                }
            }
            selectedItems.clear()
            viewModel.removeAllSelected()

            //if in big screen, make sure one item is selected.
            refreshListData()
        }
    }

    /**
    * List adapter to be used with RecyclerView. It contains the set of aOutbDeliveryPartner.
    */
    inner class AOutbDeliveryPartnerTypeListAdapter(private val context: Context, private val recyclerView: RecyclerView) : RecyclerView.Adapter<AOutbDeliveryPartnerTypeListAdapter.ViewHolder<ElementEntityitemListBinding>>() {

        /** Entire list of AOutbDeliveryPartnerType collection */
        private var aOutbDeliveryPartner: MutableList<AOutbDeliveryPartnerType> = ArrayList()

        /** Flag to indicate whether we have checked retained selected aOutbDeliveryPartner */
        private var checkForSelectedOnCreate = false

        private lateinit var binding: ElementEntityitemListBinding

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AOutbDeliveryPartnerTypeListAdapter.ViewHolder<ElementEntityitemListBinding> {
            binding = ElementEntityitemListBinding.inflate( LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return aOutbDeliveryPartner.size
        }

        override fun getItemId(position: Int): Long {
            return getItemIdForAOutbDeliveryPartnerType(aOutbDeliveryPartner[position])
        }

        override fun onBindViewHolder(holder: ViewHolder<ElementEntityitemListBinding>, position: Int) {
            checkForRetainedSelection()

            val aOutbDeliveryPartnerTypeEntity = aOutbDeliveryPartner[holder.bindingAdapterPosition]
            (aOutbDeliveryPartnerTypeEntity.getOptionalValue(AOutbDeliveryPartnerType.partnerFunction))?.let {
                holder.masterPropertyValue = it.toString()
            }
            populateObjectCell(holder, aOutbDeliveryPartnerTypeEntity)

            val isActive = getItemIdForAOutbDeliveryPartnerType(aOutbDeliveryPartnerTypeEntity) == viewModel.inFocusId
            if (isActive) {
                setItemIdSelected(holder.bindingAdapterPosition)
            }
            val isAOutbDeliveryPartnerTypeSelected = viewModel.selectedContains(aOutbDeliveryPartnerTypeEntity)
            setViewBackground(holder.objectCell, isAOutbDeliveryPartnerTypeSelected, isActive)

            holder.itemView.setOnLongClickListener(OnActionModeStartClickListener(holder))
            setOnClickListener(holder, aOutbDeliveryPartnerTypeEntity)

            setOnCheckedChangeListener(holder, aOutbDeliveryPartnerTypeEntity)
            holder.isSelected = isAOutbDeliveryPartnerTypeSelected
            setDetailImage(holder, aOutbDeliveryPartnerTypeEntity)
        }

        /**
        * Check to see if there are an retained selected aOutbDeliveryPartnerTypeEntity on start.
        * This situation occurs when a rotation with selected aOutbDeliveryPartner is triggered by user.
        */
        private fun checkForRetainedSelection() {
            if (!checkForSelectedOnCreate) {
                checkForSelectedOnCreate = true
                if (viewModel.numberOfSelected() > 0) {
                    manageActionModeOnCheckedTransition()
                }
            }
        }

        /**
        * Computes a stable ID for each AOutbDeliveryPartnerType object for use to locate the ViewHolder
        *
        * @param [aOutbDeliveryPartnerTypeEntity] to get the items for
        * @return an ID based on the primary key of AOutbDeliveryPartnerType
        */
        internal fun getItemIdForAOutbDeliveryPartnerType(aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType): Long {
            return aOutbDeliveryPartnerTypeEntity.entityKey.toString().hashCode().toLong()
        }

        /**
        * Start Action Mode if it has not been started
        *
        * This is only called when long press action results in a selection. Hence action mode may not have been
        * started. Along with starting action mode, title will be set. If this is an additional selection, adjust title
        * appropriately.
        */
        private fun manageActionModeOnCheckedTransition() {
            if (actionMode == null) {
                actionMode = (activity as AppCompatActivity).startSupportActionMode(AOutbDeliveryPartnerListActionMode())
            }
            if (viewModel.numberOfSelected() > 1) {
                actionMode?.menu?.findItem(R.id.update_item)?.isVisible = false
            }
            actionMode?.title = viewModel.numberOfSelected().toString()
        }

        /**
        * This is called when one of the selected aOutbDeliveryPartner has been de-selected
        *
        * On this event, we will determine if update action needs to be made visible or action mode should be
        * terminated (no more selected)
        */
        private fun manageActionModeOnUncheckedTransition() {
            when (viewModel.numberOfSelected()) {
                1 -> actionMode?.menu?.findItem(R.id.update_item)?.isVisible = true
                0 -> {
                    actionMode?.finish()
                    actionMode = null
                    return
                }
            }
            actionMode?.title = viewModel.numberOfSelected().toString()
        }

        private fun populateObjectCell(viewHolder: ViewHolder<ElementEntityitemListBinding>, aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType) {

            val dataValue = aOutbDeliveryPartnerTypeEntity.getOptionalValue(AOutbDeliveryPartnerType.partnerFunction)
            var masterPropertyValue: String? = null
            if (dataValue != null) {
                masterPropertyValue = dataValue.toString()
            }
            viewHolder.objectCell.apply {
                headline = masterPropertyValue
                setUseCutOut(false)
                setDetailImage(viewHolder, aOutbDeliveryPartnerTypeEntity)
                subheadline = "Subheadline goes here"
                footnote = "Footnote goes here"
                if (masterPropertyValue == null || masterPropertyValue.isEmpty()) {
                setIcon("?", 0)
                } else {
                setIcon(masterPropertyValue.substring(0, 1), 0)
                }
                setIcon(R.drawable.default_dot, 1, R.string.attachment_item_content_desc)
            }
        }

        private fun processClickAction(viewHolder: ViewHolder<ElementEntityitemListBinding>, aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType) {
            resetPreviouslyClicked()
            setViewBackground(viewHolder.objectCell, false, true)
            viewModel.inFocusId = getItemIdForAOutbDeliveryPartnerType(aOutbDeliveryPartnerTypeEntity)
        }

        /**
        * Attempt to locate previously clicked view and reset its background
        * Reset view model's inFocusId
        */
        private fun resetPreviouslyClicked() {
            (recyclerView.findViewHolderForItemId(viewModel.inFocusId) as ViewHolder<ElementEntityitemListBinding>?)?.let {
                setViewBackground(it.objectCell, it.isSelected, false)
            } ?: run {
                viewModel.refresh()
            }
        }

        /**
        * If there are selected aOutbDeliveryPartner via long press, clear them as click and long press are mutually exclusive
        * In addition, since we are clearing all selected aOutbDeliveryPartner via long press, finish the action mode.
        */
        private fun resetSelected() {
            if (viewModel.numberOfSelected() > 0) {
                viewModel.removeAllSelected()
                if (actionMode != null) {
                    actionMode?.finish()
                    actionMode = null
                }
            }
        }

        /**
        * Set up checkbox value and visibility based on aOutbDeliveryPartnerTypeEntity selection status
        *
        * @param [checkBox] to set
        * @param [isAOutbDeliveryPartnerTypeSelected] true if aOutbDeliveryPartnerTypeEntity is selected via long press action
        */
        private fun setCheckBox(checkBox: CheckBox, isAOutbDeliveryPartnerTypeSelected: Boolean) {
            checkBox.isChecked = isAOutbDeliveryPartnerTypeSelected
        }

        /**
        * Use DiffUtil to calculate the difference and dispatch them to the adapter
        * Note: Please use background thread for calculation if the list is large to avoid blocking main thread
        */
        @WorkerThread
        fun setItems(currentAOutbDeliveryPartner: List<AOutbDeliveryPartnerType>) {
            if (aOutbDeliveryPartner.isEmpty()) {
                aOutbDeliveryPartner = java.util.ArrayList(currentAOutbDeliveryPartner)
                notifyItemRangeInserted(0, currentAOutbDeliveryPartner.size)
            } else {
                val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize(): Int {
                        return aOutbDeliveryPartner.size
                    }

                    override fun getNewListSize(): Int {
                        return currentAOutbDeliveryPartner.size
                    }

                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return aOutbDeliveryPartner[oldItemPosition].entityKey.toString() == currentAOutbDeliveryPartner[newItemPosition].entityKey.toString()
                    }

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        val aOutbDeliveryPartnerTypeEntity = aOutbDeliveryPartner[oldItemPosition]
                        return !aOutbDeliveryPartnerTypeEntity.isUpdated && currentAOutbDeliveryPartner[newItemPosition] == aOutbDeliveryPartnerTypeEntity
                    }
                })
                aOutbDeliveryPartner.clear()
                aOutbDeliveryPartner.addAll(currentAOutbDeliveryPartner)
                result.dispatchUpdatesTo(this)
            }
        }

        /**
        * Set ViewHolder's CheckBox onCheckedChangeListener
        *
        * @param [holder] to set
        * @param [aOutbDeliveryPartnerTypeEntity] associated with this ViewHolder
        */
        private fun setOnCheckedChangeListener(holder: ViewHolder<ElementEntityitemListBinding>, aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType) {
            holder.checkBox.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    //(currentActivity as AOutbDeliveryPartnerActivity).onUnderDeletion(aOutbDeliveryPartnerTypeEntity, true)
                    viewModel.addSelected(aOutbDeliveryPartnerTypeEntity)
                    manageActionModeOnCheckedTransition()
                    resetPreviouslyClicked()
                } else {
                    //(currentActivity as AOutbDeliveryPartnerActivity).onUnderDeletion(aOutbDeliveryPartnerTypeEntity, false)
                    viewModel.removeSelected(aOutbDeliveryPartnerTypeEntity)
                    manageActionModeOnUncheckedTransition()
                }
                setViewBackground(holder.objectCell, viewModel.selectedContains(aOutbDeliveryPartnerTypeEntity), false)
                setDetailImage(holder, aOutbDeliveryPartnerTypeEntity)
            }
        }

        /**
        * Set ViewHolder's view onClickListener
        *
        * @param [holder] to set
        * @param [aOutbDeliveryPartnerTypeEntity] associated with this ViewHolder
        */
        private fun setOnClickListener(holder: ViewHolder<ElementEntityitemListBinding>, aOutbDeliveryPartnerTypeEntity: AOutbDeliveryPartnerType) {
            holder.itemView.setOnClickListener { view ->
                val isNavigationDisabled = (currentActivity as AOutbDeliveryPartnerActivity).isNavigationDisabled
                if( !isNavigationDisabled ) {
                    resetSelected()
                    resetPreviouslyClicked()
                    processClickAction(holder, aOutbDeliveryPartnerTypeEntity)
                    viewModel.setSelectedEntity(aOutbDeliveryPartnerTypeEntity)
                    listener?.onFragmentStateChange(UIConstants.EVENT_ITEM_CLICKED, aOutbDeliveryPartnerTypeEntity)
                } else {
                    Toast.makeText(currentActivity, "Please save your changes first...", Toast.LENGTH_LONG).show()
                }
            }
        }

        /**
        * Set background of view to indicate aOutbDeliveryPartnerTypeEntity selection status
        * Selected and Active are mutually exclusive. Only one can be true
        *
        * @param [view]
        * @param [isAOutbDeliveryPartnerTypeSelected] - true if aOutbDeliveryPartnerTypeEntity is selected via long press action
        * @param [isActive]           - true if aOutbDeliveryPartnerTypeEntity is selected via click action
        */
        private fun setViewBackground(view: View, isAOutbDeliveryPartnerTypeSelected: Boolean, isActive: Boolean) {
            val isMasterDetailView = currentActivity.resources.getBoolean(R.bool.two_pane)
            if (isAOutbDeliveryPartnerTypeSelected) {
                view.background = ContextCompat.getDrawable(context, R.drawable.list_item_selected)
            } else if (isActive && isMasterDetailView && !isInActionMode) {
                view.background = ContextCompat.getDrawable(context, R.drawable.list_item_active)
            } else {
                view.background = ContextCompat.getDrawable(context, R.drawable.list_item_default)
            }
        }

        /**
        * ViewHolder for RecyclerView.
        * Each view has a Fiori ObjectCell and a checkbox (used by long press)
        */
        inner class ViewHolder<VB: ElementEntityitemListBinding>(private val viewBinding: VB) : RecyclerView.ViewHolder(viewBinding.root) {

            var isSelected = false
                set(selected) {
                    field = selected
                    checkBox.isChecked = selected
                }

            var masterPropertyValue: String? = null

            /** Fiori ObjectCell to display aOutbDeliveryPartnerTypeEntity in list */
            val objectCell: ObjectCell = viewBinding.content

            /** Checkbox for long press selection */
            val checkBox: CheckBox = viewBinding.cbx

            override fun toString(): String {
                return super.toString() + " '" + objectCell.description + "'"
            }
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AOutbDeliveryPartnerActivity::class.java)
    }
}
