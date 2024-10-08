package com.golive.godrive.btppublic.service

import com.sap.cloud.mobile.foundation.model.AppConfig
import com.sap.cloud.android.odata.espmcontainer.ESPMContainer
import com.sap.cloud.android.odata.api_outbound_delivery_srv_entities.API_OUTBOUND_DELIVERY_SRV_Entities
import com.sap.cloud.mobile.foundation.common.ClientProvider
import com.sap.cloud.mobile.odata.OnlineODataProvider
import com.sap.cloud.mobile.odata.http.OKHttpHandler

class SAPServiceManager(private val appConfig: AppConfig) {

    var serviceRoot: String = ""
        private set
        get() {
            return (aPI_OUTBOUND_DELIVERY_SRV_Entities?.provider as OnlineODataProvider).serviceRoot
        }

    var eSPMContainer: ESPMContainer? = null
        private set
        get() {
            return field ?: throw IllegalStateException("SAPServiceManager was not initialized")
        }
    var aPI_OUTBOUND_DELIVERY_SRV_Entities: API_OUTBOUND_DELIVERY_SRV_Entities? = null
        private set
        get() {
            return field ?: throw IllegalStateException("SAPServiceManager was not initialized")
        }

    fun openODataStore(callback: () -> Unit) {
        if( appConfig != null ) {
            appConfig.serviceUrl?.let { _serviceURL ->
                eSPMContainer = ESPMContainer (
                    OnlineODataProvider("SAPService", _serviceURL + CONNECTION_ID_ESPMCONTAINER).apply {
                        networkOptions.httpHandler = OKHttpHandler(ClientProvider.get())
                        serviceOptions.checkVersion = false
                        serviceOptions.requiresType = true
                        serviceOptions.cacheMetadata = false
                    }
                )
                aPI_OUTBOUND_DELIVERY_SRV_Entities = API_OUTBOUND_DELIVERY_SRV_Entities (
                    OnlineODataProvider("SAPService", _serviceURL + CONNECTION_ID_API_OUTBOUND_DELIVERY_SRV_ENTITIES).apply {
                        networkOptions.httpHandler = OKHttpHandler(ClientProvider.get())
                        serviceOptions.checkVersion = false
                        serviceOptions.requiresType = true
                        serviceOptions.cacheMetadata = false
                    }
                )
            } ?: run {
                throw IllegalStateException("ServiceURL of Configuration Data is not initialized")
            }
        }
        callback.invoke()
    }

    companion object {
        const val CONNECTION_ID_ESPMCONTAINER: String = "com.sap.edm.sampleservice.v4"
        const val CONNECTION_ID_API_OUTBOUND_DELIVERY_SRV_ENTITIES: String = "com.golive.godrive.publicbtp"
    }
}
