package com.cmps312.bankingapp.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.data.local.entity.AccTransaction
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.accounts.observe(viewLifecycleOwner) {
            accountNoSp.adapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_dropdown_item_1line,
                it.map { it.accountNo }
            )

        }
        accountNoSp.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                accountViewModel.selectedAccountForTransaction =
                    accountViewModel.accounts.value?.get(position)!!
                nameTv.text = accountViewModel.selectedAccountForTransaction.name
                balanceTv.text = accountViewModel.selectedAccountForTransaction.balance.toString()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        })


        val accountViewModel by activityViewModels<AccountViewModel>()

        val transaction = AccTransaction()

        submitBtn.setOnClickListener {

            val amount = amountEdt.text
            when {
                amount.isEmpty() -> Toast.makeText(
                    activity,
                    " You should provide a value for the amount to withdraw or deposit",
                    Toast.LENGTH_LONG
                ).show()
                (amount.toString().toInt() > accountViewModel.selectedAccountForTransaction.balance)
                        && transactionTypeSp.selectedItem.equals("Withdraw") -> Toast.makeText(
                    activity,
                    "You do not have enough balance",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    transaction.amount = amount.toString().toInt()
                    transaction.apply {
                        accountNo = accountViewModel.selectedAccountForTransaction.accountNo
                        type = transactionTypeSp.selectedItem.toString()
                    }
                    if (transactionTypeSp.selectedItem.toString() == "Withdraw") {
                        accountViewModel.selectedAccountForTransaction.balance -= amount.toString()
                            .toInt()
                    } else {
                        accountViewModel.selectedAccountForTransaction.balance += amount.toString()
                            .toInt()
                    }
                    accountViewModel.addAccTransaction(transaction)
                    accountViewModel.updateAccount(accountViewModel.selectedAccountForTransaction)
                    findNavController().navigate(R.id.accountsListFragment)
                }
            }
        }

        cancelBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}