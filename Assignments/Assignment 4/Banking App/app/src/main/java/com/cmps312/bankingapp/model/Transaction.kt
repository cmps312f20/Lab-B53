package com.cmps312.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Transaction(
    var id: Int=0,
    var type: String = "",
    var amount: String = "",
    var accountNo: String = ""
)
