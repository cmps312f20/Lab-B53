package com.cmps312.bankingapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cmps312.bankingapp.data.local.entity.AccTransaction
import com.cmps312.bankingapp.data.local.entity.Account


@Dao
interface AccountDao {

    @Query("SELECT * FROM Account")
    fun getAccounts(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(account: Account): Long

    @Delete
    suspend fun deleteAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    //@Query("SELECT * FROM Account WHERE accountType = :type OR IFNULL(:type, accountType !=\"\")")
    @Query("SELECT * FROM Account WHERE accountType = :type")
    fun getAccountByType(type: String): LiveData<List<Account>>

    @Query("SELECT * FROM AccTransaction")
    fun getTransactions(): LiveData<List<AccTransaction>>


    @Query("SELECT * FROM AccTransaction WHERE id=:id")
    fun getAccountTransactions(id: Int): AccTransaction

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(accTransaction: AccTransaction): Long


}