package com.cmps312.bankingapp.ui.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.databinding.FragmentAddUpdateAccountBinding
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_add_update_account.*


class AddUpdateAccountFragment : Fragment(R.layout.fragment_add_update_account) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (accountViewModel.isEdit) {
            title.text = "Edit Account"

            val binding = FragmentAddUpdateAccountBinding.bind(view)
            binding.account = accountViewModel.selectedAccount

            if (accountViewModel.selectedAccount.accountType == "Current")
                typeSpinner.setSelection(0)
            else
                typeSpinner.setSelection(1)
        }

        doneBtn.setOnClickListener {
            if (isValidForm()) {
                accountViewModel.apply {
                    selectedAccount.name = nameEdt.text.toString()
                    selectedAccount.accountNo = accountNoSp.text.toString().toInt()
                    selectedAccount.accountType = typeSpinner.selectedItem.toString()
                    selectedAccount.balance = balanceEdt.text.toString().toInt()
                }

                if (accountViewModel.isEdit)
                    accountViewModel.updateAccount(accountViewModel.selectedAccount)
                else
                    accountViewModel.addAccount(accountViewModel.selectedAccount)

                activity?.onBackPressed()
            } else {
                Toast.makeText(context, "Please Fill all the requirements", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        cancelBtn.setOnClickListener {
            activity?.onNavigateUp()
        }
    }

    private fun isValidForm(): Boolean {
        if (nameEdt.text.isEmpty() || accountNoSp.text.isEmpty() || balanceEdt.text.toString()
                .toInt() < 0
        ) return false
        return true
    }
}