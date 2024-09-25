package com.rndtechnosoft.bconn.Model

data class MemberVerifiedResponseData(
    val `data`: VerifiedData
)
data class VerifiedData(
    val approvedByAdmin: String,
    val approvedByMember: String
)