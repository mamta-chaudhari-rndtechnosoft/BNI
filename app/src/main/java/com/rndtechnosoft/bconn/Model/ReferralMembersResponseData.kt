package com.rndtechnosoft.bconn.Model

data class ReferralMembersResponseData(
    val `data`: List<ReferralMemberData>
)
data class ReferralMemberData(
    val _id: String,
    val approvedByadmin: String,
    val approvedBymember: String,
    val bannerImg: Any,
    val city: String,
    val country: String,
    val deviceTokens: List<String>,
    val email: String,
    val mobile: String,
    val name: String,
    val password: String,
    val profileImg: Any,
    val ref_member: String,
    val refral_code: String
)