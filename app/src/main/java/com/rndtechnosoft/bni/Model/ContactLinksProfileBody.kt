package com.rndtechnosoft.bni.Model

data class ContactLinksProfileBody(
    val contactLinks: ContactLinks
)

data class ContactLinks(
    val facebook: String,
    val linkedin: String,
    val twitter:  String,
    val whatsapp: String
)