package com.rndtechnosoft.bconn.Model

data class UpdateMemberStatusResponseData(
    val id: String,
    val message: String,
    val status: String,
    val updatedFields: UpdatedFields
)
data class UpdatedFields(
    val approvedByMember: String
)