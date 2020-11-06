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
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.model.Transaction
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.accounts.observe(viewLifecycleOwner) {
            accountNoSp.adapter = ArrayAdapter<Account>(
                view.context,
                android.R.layout.simple_dropdown_item_1line,
                it
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

        val transaction = Transaction()

        submitBtn.setOnClickListener {
            val amount = amountEdt.text
            when{
                amount.isEmpty()-> Toast.makeText(activity, " You should provide a value for the amount to withdraw or deposit", Toast.LENGTH_LONG).show()
                (amount.toString().toDouble() > accountViewModel.selectedAccountForTransaction.balance )
                        && transactionTypeSp.selectedItem.equals("Withdraw")-> Toast.makeText(activity, "You do not have enough balance", Toast.LENGTH_LONG).show()
                else->{
                    accountViewModel.addTransaction(transaction)
                    findNavController().navigate(R.id.accountsListFragment)
                }
            }
        }

        cancelBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}