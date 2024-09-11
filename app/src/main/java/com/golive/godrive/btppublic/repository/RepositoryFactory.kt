package com.golive.godrive.btppublic.repository
import com.golive.godrive.btppublic.service.SAPServiceManager

import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_EntitiesMetadata.EntitySets
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AHandlingUnitHeaderDeliveryType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AHandlingUnitItemDeliveryType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AMaintenanceItemObjectType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryAddressType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryAddress2Type
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryDocFlowType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryHeaderType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryHeaderTextType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryItemType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryItemTextType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.AOutbDeliveryPartnerType
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.ASerialNmbrDeliveryType

import com.sap.cloud.mobile.odata.EntitySet
import com.sap.cloud.mobile.odata.EntityValue
import com.sap.cloud.mobile.odata.Property

import java.util.WeakHashMap

/*
 * Repository factory to construct repository for an entity set
 */
class RepositoryFactory
/**
 * Construct a RepositoryFactory instance. There should only be one repository factory and used
 * throughout the life of the application to avoid caching entities multiple times.
 * @param sapServiceManager - Service manager for interaction with OData service
 */
(private val sapServiceManager: SAPServiceManager?) {
    private val repositories: WeakHashMap<String, Repository<out EntityValue>> = WeakHashMap()

    /**
     * Construct or return an existing repository for the specified entity set
     * @param entitySet - entity set for which the repository is to be returned
     * @param orderByProperty - if specified, collection will be sorted ascending with this property
     * @return a repository for the entity set
     */
    fun getRepository(entitySet: EntitySet, orderByProperty: Property?): Repository<out EntityValue> {
        val aPI_OUTBOUND_DELIVERY_SRV_Entities = sapServiceManager?.aPI_OUTBOUND_DELIVERY_SRV_Entities
        val key = entitySet.localName
        var repository: Repository<out EntityValue>? = repositories[key]
        if (repository == null) {
            repository = when (key) {
                EntitySets.aHandlingUnitHeaderDelivery.localName -> Repository<AHandlingUnitHeaderDeliveryType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aHandlingUnitHeaderDelivery, orderByProperty)
                EntitySets.aHandlingUnitItemDelivery.localName -> Repository<AHandlingUnitItemDeliveryType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aHandlingUnitItemDelivery, orderByProperty)
                EntitySets.aMaintenanceItemObject.localName -> Repository<AMaintenanceItemObjectType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aMaintenanceItemObject, orderByProperty)
                EntitySets.aOutbDeliveryAddress.localName -> Repository<AOutbDeliveryAddressType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryAddress, orderByProperty)
                EntitySets.aOutbDeliveryAddress2.localName -> Repository<AOutbDeliveryAddress2Type>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryAddress2, orderByProperty)
                EntitySets.aOutbDeliveryDocFlow.localName -> Repository<AOutbDeliveryDocFlowType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryDocFlow, orderByProperty)
                EntitySets.aOutbDeliveryHeader.localName -> Repository<AOutbDeliveryHeaderType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryHeader, orderByProperty)
                EntitySets.aOutbDeliveryHeaderText.localName -> Repository<AOutbDeliveryHeaderTextType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryHeaderText, orderByProperty)
                EntitySets.aOutbDeliveryItem.localName -> Repository<AOutbDeliveryItemType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryItem, orderByProperty)
                EntitySets.aOutbDeliveryItemText.localName -> Repository<AOutbDeliveryItemTextType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryItemText, orderByProperty)
                EntitySets.aOutbDeliveryPartner.localName -> Repository<AOutbDeliveryPartnerType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aOutbDeliveryPartner, orderByProperty)
                EntitySets.aSerialNmbrDelivery.localName -> Repository<ASerialNmbrDeliveryType>(aPI_OUTBOUND_DELIVERY_SRV_Entities!!, EntitySets.aSerialNmbrDelivery, orderByProperty)
                else -> throw AssertionError("Fatal error, entity set[$key] missing in generated code")
            }
            repositories[key] = repository
        }
        return repository
    }

    /**
     * Get rid of all cached repositories
     */
    fun reset() {
        repositories.clear()
    }
}
