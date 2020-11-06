package com.cmps312.bankingapp.data.api

import com.cmps312.bankingapp.model.Account
import retrofit2.http.*

interface AccountService {

    @GET(".")
    suspend fun getAccounts(@Query("acctType")  acctType: String ): List<Account>

    @GET("{accountNo}")
    suspend fun getAccount(@Path("accountNo") accountNo: String): Account

    @POST(".")
    suspend fun addAccount(@Body account: Account): Account

    @PUT("{accountNo}")
    suspend fun updateAccount(@Path("accountNo") accountNo: String, @Body account: Account): Account

    @DELETE("{accountNo}")
    suspend fun deleteAccount(@Path("accountNo") accountNo: String): String

}