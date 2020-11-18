package com.cmps312.bankingapp.ui.sharedViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cmps312.bankingapp.data.local.entity.AccTransaction
import com.cmps312.bankingapp.data.local.entity.Account
import com.cmps312.bankingapp.data.repository.AccountRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionListRepo by lazy { AccountRepo(application) }

    var accounts: LiveData<List<Account>> = transactionListRepo.getAccounts()

    private lateinit var transactions: LiveData<List<AccTransaction>>

    var selectedAccountForTransaction = Account()
    var selectedAccount = Account()
    var isEdit = false

    fun addAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionListRepo.addAccount(account)
        }
    }

    fun getAccountsByType(type: String) {
        accounts = when (type) {
            "All" -> transactionListRepo.getAccounts()
            else -> transactionListRepo.getAccountByType(type)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionListRepo.deleteAccount(account)
        }
    }

    fun updateAccount(account: Account = selectedAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionListRepo.updateAccount(account)
        }
    }

    fun getTransactions() {
        transactions = transactionListRepo.getTransactions()
    }

    fun addAccTransaction(accTransaction: AccTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionListRepo.addTransaction(accTransaction)
        }
    }
}