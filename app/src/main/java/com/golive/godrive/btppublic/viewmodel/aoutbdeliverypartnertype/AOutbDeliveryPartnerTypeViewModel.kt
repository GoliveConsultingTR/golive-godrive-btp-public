package com.golive.godrive.btppublic.viewmodel.aoutbdeliverypartnertype

import android.app.Application
import android.os.Parcelable

import com.golive.godrive.btppublic.viewmodel.EntityViewModel
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryPartnerType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets

/*
 * Represents View model for AOutbDeliveryPartnerType
 *
 * Having an entity view model for each <T> allows the ViewModelProvider to cache and return the view model of that
 * type. This is because the ViewModelStore of ViewModelProvider cannot not be able to tell the difference between
 * EntityViewModel<type1> and EntityViewModel<type2>.
 */
class AOutbDeliveryPartnerTypeViewModel(application: Application): EntityViewModel<AOutbDeliveryPartnerType>(application, EntitySets.aOutbDeliveryPartner, AOutbDeliveryPartnerType.partnerFunction) {
    /**
     * Constructor for a specific view model with navigation data.
     * @param [navigationPropertyName] - name of the navigation property
     * @param [entityData] - parent entity (starting point of the navigation)
     */
    constructor(application: Application, navigationPropertyName: String, entityData: Parcelable): this(application) {
        EntityViewModel<AOutbDeliveryPartnerType>(application, EntitySets.aOutbDeliveryPartner, AOutbDeliveryPartnerType.partnerFunction, navigationPropertyName, entityData)
    }
}
