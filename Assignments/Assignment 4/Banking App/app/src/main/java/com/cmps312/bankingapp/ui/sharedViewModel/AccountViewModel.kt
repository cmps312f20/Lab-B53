package com.cmps312.bankingapp.ui.sharedViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmps312.bankingapp.data.repository.AccountRepository
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel : ViewModel() {

    private var _accounts = MutableLiveData<List<Account>>()

    init {
        getAccounts("All")
    }

    //transaction
    var selectedAccountForTransaction = Account()


    //will be used for both edit and update
    var account = Account()
    var isEdit = false

    val accounts = _accounts as LiveData<List<Account>>


    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.deleteAccount(account.accountNo)
            }
            _accounts.value?.let {
                _accounts.value = it - account
            }
        }
    }

    fun addAccount() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.addAccount(account)
            }
            _accounts.value?.let {
                _accounts.value = it + account
            }
        }
    }

    fun updateAccount() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.updateAccount(
                    account.accountNo,
                    account
                )
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        //Todo implement this method and related methods that you need inside the Repository
    }

    fun getAccounts(acctType: String) {
        viewModelScope.launch {
            _accounts.value = withContext(Dispatchers.Default) {
                    AccountRepository.getAccounts(acctType)
                }
        }
    }
}