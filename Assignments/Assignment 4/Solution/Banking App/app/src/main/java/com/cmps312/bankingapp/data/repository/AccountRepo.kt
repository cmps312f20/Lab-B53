package com.cmps312.bankingapp.data.repository

import android.content.Context
import com.cmps312.bankingapp.data.local.AccountDatabase
import com.cmps312.bankingapp.data.local.entity.AccTransaction
import com.cmps312.bankingapp.data.local.entity.Account

class AccountRepo(private val context: Context) {
    private val accountDao by lazy {
        AccountDatabase.getDatabase(context).accountDao()
    }

    fun getAccounts() = accountDao.getAccounts()
    fun getAccountByType(type: String) = accountDao.getAccountByType(type)

    suspend fun addAccount(project: Account) = accountDao.addAccount(project)
    suspend fun deleteAccount(project: Account) = accountDao.deleteAccount(project)
    suspend fun updateAccount(project: Account) = accountDao.updateAccount(project)

    fun getTransactions() = accountDao.getTransactions()
    fun getAccountTransactions(id: Int) = accountDao.getAccountTransactions(id)
    suspend fun addTransaction(accTransaction: AccTransaction) =
        accountDao.addTransaction(accTransaction)

}