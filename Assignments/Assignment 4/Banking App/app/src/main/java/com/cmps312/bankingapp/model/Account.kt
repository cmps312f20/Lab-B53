package com.cmps312.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Account(
    var accountNo: String = "-1",
    var name: String = "",
    var acctType: String = "",
    var balance: Int = 0
){
    override fun toString(): String {
        return accountNo
    }
}
