package com.golive.godrive.btppublic.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Parcelable

import com.golive.godrive.btppublic.viewmodel.ahandlingunitheaderdeliverytype.AHandlingUnitHeaderDeliveryTypeViewModel
import com.golive.godrive.btppublic.viewmodel.ahandlingunititemdeliverytype.AHandlingUnitItemDeliveryTypeViewModel
import com.golive.godrive.btppublic.viewmodel.amaintenanceitemobjecttype.AMaintenanceItemObjectTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryaddresstype.AOutbDeliveryAddressTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryaddress2type.AOutbDeliveryAddress2TypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliverydocflowtype.AOutbDeliveryDocFlowTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryheadertype.AOutbDeliveryHeaderTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryheadertexttype.AOutbDeliveryHeaderTextTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryitemtype.AOutbDeliveryItemTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliveryitemtexttype.AOutbDeliveryItemTextTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aoutbdeliverypartnertype.AOutbDeliveryPartnerTypeViewModel
import com.golive.godrive.btppublic.viewmodel.aserialnmbrdeliverytype.ASerialNmbrDeliveryTypeViewModel

/**
 * Custom factory class, which can create view models for entity subsets, which are
 * reached from a parent entity through a navigation property.
 *
 * @param application parent application
 * @param navigationPropertyName name of the navigation link
 * @param entityData parent entity
 */
class EntityViewModelFactory (
        val application: Application, // name of the navigation property
        val navigationPropertyName: String, // parent entity
        val entityData: Parcelable) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass.simpleName) {
			"AHandlingUnitHeaderDeliveryTypeViewModel" -> AHandlingUnitHeaderDeliveryTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AHandlingUnitItemDeliveryTypeViewModel" -> AHandlingUnitItemDeliveryTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AMaintenanceItemObjectTypeViewModel" -> AMaintenanceItemObjectTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryAddressTypeViewModel" -> AOutbDeliveryAddressTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryAddress2TypeViewModel" -> AOutbDeliveryAddress2TypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryDocFlowTypeViewModel" -> AOutbDeliveryDocFlowTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryHeaderTypeViewModel" -> AOutbDeliveryHeaderTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryHeaderTextTypeViewModel" -> AOutbDeliveryHeaderTextTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryItemTypeViewModel" -> AOutbDeliveryItemTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryItemTextTypeViewModel" -> AOutbDeliveryItemTextTypeViewModel(application, navigationPropertyName, entityData) as T
            			"AOutbDeliveryPartnerTypeViewModel" -> AOutbDeliveryPartnerTypeViewModel(application, navigationPropertyName, entityData) as T
             else -> ASerialNmbrDeliveryTypeViewModel(application, navigationPropertyName, entityData) as T
        }
    }
}
