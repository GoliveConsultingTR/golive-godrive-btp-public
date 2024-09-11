package com.golive.godrive.btppublic.mdui

import com.golive.godrive.btppublic.app.SAPWizardApplication

import com.sap.cloud.mobile.flowv2.core.DialogHelper
import com.sap.cloud.mobile.flowv2.core.Flow
import com.sap.cloud.mobile.flowv2.core.FlowContextRegistry
import com.sap.cloud.mobile.flowv2.model.FlowType
import com.sap.cloud.mobile.flowv2.securestore.UserSecureStoreDelegate
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.widget.ArrayAdapter
import android.content.Context
import android.content.Intent
import java.util.ArrayList
import java.util.HashMap
import com.golive.godrive.btppublic.app.WelcomeActivity
import com.golive.godrive.btppublic.databinding.ActivityEntitySetListBinding
import com.golive.godrive.btppublic.databinding.ElementEntitySetListBinding
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer
import com.sap.cloud.mobile.foundation.usage.UsageService
import com.golive.godrive.btppublic.mdui.ahandlingunitheaderdelivery.AHandlingUnitHeaderDeliveryActivity
import com.golive.godrive.btppublic.mdui.ahandlingunititemdelivery.AHandlingUnitItemDeliveryActivity
import com.golive.godrive.btppublic.mdui.amaintenanceitemobject.AMaintenanceItemObjectActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryaddress.AOutbDeliveryAddressActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryaddress2.AOutbDeliveryAddress2Activity
import com.golive.godrive.btppublic.mdui.aoutbdeliverydocflow.AOutbDeliveryDocFlowActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryheader.AOutbDeliveryHeaderActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryheadertext.AOutbDeliveryHeaderTextActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryitem.AOutbDeliveryItemActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliveryitemtext.AOutbDeliveryItemTextActivity
import com.golive.godrive.btppublic.mdui.aoutbdeliverypartner.AOutbDeliveryPartnerActivity
import com.golive.godrive.btppublic.mdui.aserialnmbrdelivery.ASerialNmbrDeliveryActivity
import org.slf4j.LoggerFactory
import com.golive.godrive.btppublic.R

/*
 * An activity to display the list of all entity types from the OData service
 */
class EntitySetListActivity : AppCompatActivity() {
    private val entitySetNames = ArrayList<String>()
    private val entitySetNameMap = HashMap<String, EntitySetName>()
    private lateinit var binding: ActivityEntitySetListBinding


    enum class EntitySetName constructor(val entitySetName: String, val titleId: Int, val iconId: Int) {
        AHandlingUnitHeaderDelivery("AHandlingUnitHeaderDelivery", R.string.eset_ahandlingunitheaderdelivery,
            BLUE_ANDROID_ICON),
        AHandlingUnitItemDelivery("AHandlingUnitItemDelivery", R.string.eset_ahandlingunititemdelivery,
            WHITE_ANDROID_ICON),
        AMaintenanceItemObject("AMaintenanceItemObject", R.string.eset_amaintenanceitemobject,
            BLUE_ANDROID_ICON),
        AOutbDeliveryAddress("AOutbDeliveryAddress", R.string.eset_aoutbdeliveryaddress,
            WHITE_ANDROID_ICON),
        AOutbDeliveryAddress2("AOutbDeliveryAddress2", R.string.eset_aoutbdeliveryaddress2,
            BLUE_ANDROID_ICON),
        AOutbDeliveryDocFlow("AOutbDeliveryDocFlow", R.string.eset_aoutbdeliverydocflow,
            WHITE_ANDROID_ICON),
        AOutbDeliveryHeader("AOutbDeliveryHeader", R.string.eset_aoutbdeliveryheader,
            BLUE_ANDROID_ICON),
        AOutbDeliveryHeaderText("AOutbDeliveryHeaderText", R.string.eset_aoutbdeliveryheadertext,
            WHITE_ANDROID_ICON),
        AOutbDeliveryItem("AOutbDeliveryItem", R.string.eset_aoutbdeliveryitem,
            BLUE_ANDROID_ICON),
        AOutbDeliveryItemText("AOutbDeliveryItemText", R.string.eset_aoutbdeliveryitemtext,
            WHITE_ANDROID_ICON),
        AOutbDeliveryPartner("AOutbDeliveryPartner", R.string.eset_aoutbdeliverypartner,
            BLUE_ANDROID_ICON),
        ASerialNmbrDelivery("ASerialNmbrDelivery", R.string.eset_aserialnmbrdelivery,
            WHITE_ANDROID_ICON)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //navigate to launch screen if SAPServiceManager or OfflineOdataProvider is not initialized
        navForInitialize()
        binding = ActivityEntitySetListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar) // to avoid ambiguity
        setSupportActionBar(toolbar)

        SDKInitializer.getService(UsageService::class)?.eventBehaviorViewDisplayed(EntitySetListActivity::class.java.simpleName,
                "elementId", "onCreate", "called")
        entitySetNames.clear()
        entitySetNameMap.clear()
        for (entitySet in EntitySetName.values()) {
            val entitySetTitle = resources.getString(entitySet.titleId)
            entitySetNames.add(entitySetTitle)
            entitySetNameMap[entitySetTitle] = entitySet
        }

        val listView = binding.entityList
        val adapter = EntitySetListAdapter(this, R.layout.element_entity_set_list, entitySetNames)

        listView.adapter = adapter

        listView.setOnItemClickListener listView@{ _, _, position, _ ->
            val entitySetName = entitySetNameMap[adapter.getItem(position)!!]
            SDKInitializer.getService(UsageService::class)?.eventBehaviorUserInteraction(EntitySetListActivity::class.java.simpleName,
                    "position: $position", "onClicked", entitySetName?.entitySetName)
            val context = this@EntitySetListActivity
            val intent: Intent = when (entitySetName) {
                EntitySetName.AHandlingUnitHeaderDelivery -> Intent(context, AHandlingUnitHeaderDeliveryActivity::class.java)
                EntitySetName.AHandlingUnitItemDelivery -> Intent(context, AHandlingUnitItemDeliveryActivity::class.java)
                EntitySetName.AMaintenanceItemObject -> Intent(context, AMaintenanceItemObjectActivity::class.java)
                EntitySetName.AOutbDeliveryAddress -> Intent(context, AOutbDeliveryAddressActivity::class.java)
                EntitySetName.AOutbDeliveryAddress2 -> Intent(context, AOutbDeliveryAddress2Activity::class.java)
                EntitySetName.AOutbDeliveryDocFlow -> Intent(context, AOutbDeliveryDocFlowActivity::class.java)
                EntitySetName.AOutbDeliveryHeader -> Intent(context, AOutbDeliveryHeaderActivity::class.java)
                EntitySetName.AOutbDeliveryHeaderText -> Intent(context, AOutbDeliveryHeaderTextActivity::class.java)
                EntitySetName.AOutbDeliveryItem -> Intent(context, AOutbDeliveryItemActivity::class.java)
                EntitySetName.AOutbDeliveryItemText -> Intent(context, AOutbDeliveryItemTextActivity::class.java)
                EntitySetName.AOutbDeliveryPartner -> Intent(context, AOutbDeliveryPartnerActivity::class.java)
                EntitySetName.ASerialNmbrDelivery -> Intent(context, ASerialNmbrDeliveryActivity::class.java)
                else -> return@listView
            }
            context.startActivity(intent)
        }
    }

    inner class EntitySetListAdapter internal constructor(context: Context, resource: Int, entitySetNames: List<String>)
                    : ArrayAdapter<String>(context, resource, entitySetNames) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            var viewBind :ElementEntitySetListBinding
            val entitySetName = entitySetNameMap[getItem(position)!!]
            if (view == null) {
                viewBind = ElementEntitySetListBinding.inflate(LayoutInflater.from(context), parent, false)
                view = viewBind.root
            } else {
                viewBind = ElementEntitySetListBinding.bind(view)
            }
            val entitySetCell = viewBind.entitySetName
            entitySetCell.headline = entitySetName?.titleId?.let {
                context.resources.getString(it)
            }
            entitySetName?.iconId?.let { entitySetCell.setDetailImage(it) }
            return view
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.entity_set_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_delete_registration)?.isEnabled =
            UserSecureStoreDelegate.getInstance().getRuntimeMultipleUserModeAsync() == true
        menu?.findItem(R.id.menu_delete_registration)?.isVisible =
            UserSecureStoreDelegate.getInstance().getRuntimeMultipleUserModeAsync() == true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        LOGGER.debug("onOptionsItemSelected: " + item.title)
        return when (item.itemId) {
            R.id.menu_settings -> {
                LOGGER.debug("settings screen menu item selected.")
                Intent(this, SettingsActivity::class.java).also {
                    this.startActivity(it)
                }
                true
            }
            R.id.menu_logout -> {
                Flow.start(this, FlowContextRegistry.flowContext.copy(
                    flowType = FlowType.LOGOUT,
                )) { _, resultCode, _ ->
                    if (resultCode == RESULT_OK) {
                        Intent(this, WelcomeActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(this)
                        }
                    }
                }
                true
            }
            R.id.menu_delete_registration -> {
                DialogHelper.ErrorDialogFragment(
                    message = getString(R.string.delete_registration_warning),
                    title = getString(R.string.dialog_warn_title),
                    positiveButtonCaption = getString(R.string.confirm_yes),
                    negativeButtonCaption = getString(R.string.cancel),
                    positiveAction = {
                        Flow.start(this, FlowContextRegistry.flowContext.copy(
                            flowType = FlowType.DEL_REGISTRATION
                        )) { _, resultCode, _ ->
                            if (resultCode == RESULT_OK) {
                                Intent(this, WelcomeActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(this)
                                }
                            }
                        }
                    }
                ).apply {
                    isCancelable = false
                    show(supportFragmentManager, this@EntitySetListActivity.getString(R.string.delete_registration))
                }
                true
            }
            else -> false
        }
    }

    private fun navForInitialize() {
        if ((application as SAPWizardApplication).sapServiceManager == null) {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }


    companion object {
        private val LOGGER = LoggerFactory.getLogger(EntitySetListActivity::class.java)
        private val BLUE_ANDROID_ICON = R.drawable.ic_sap_icon_product_filled_round
        private val WHITE_ANDROID_ICON = R.drawable.ic_sap_icon_product_outlined
    }
}
