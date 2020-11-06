package com.cmps312.bankingapp.data.repository

import com.cmps312.bankingapp.data.api.AccountService
import com.cmps312.bankingapp.model.Account
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Path

object AccountRepository {

    private const val BASE_URL = "https://employee-bank-app.herokuapp.com/api/accounts/"
    private val contentType = "application/json".toMediaType()
    private val jsonConverterFactory = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }.asConverterFactory(contentType)


    private val accountService: AccountService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(AccountService::class.java)
    }
    suspend fun getAccount(accountNo: String): Account = accountService.getAccount(accountNo)
    suspend fun getAccounts(acctType : String) = accountService.getAccounts(acctType)
    suspend fun addAccount(account: Account) = accountService.addAccount(account)
    suspend fun updateAccount(accountID: String, updatedAcc: Account) =
        accountService.updateAccount(accountID, updatedAcc)

    suspend fun deleteAccount(accountID: String) = accountService.deleteAccount(accountID)

}