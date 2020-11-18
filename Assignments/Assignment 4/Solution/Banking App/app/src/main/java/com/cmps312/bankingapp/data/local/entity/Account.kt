package com.cmps312.bankingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    var accountNo: Int = 0,
    var name: String = "",
    var accountType: String = "",
    var balance: Int = 0
)