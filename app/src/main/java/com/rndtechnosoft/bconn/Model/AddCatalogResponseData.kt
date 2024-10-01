package com.rndtechnosoft.bconn.Model

data class AddCatalogResponseData(
    val id: String,
    val updatedFields: UpdatedFieldsCatalog
)
 data class UpdatedFieldsCatalog(
     val catalog:String
 )