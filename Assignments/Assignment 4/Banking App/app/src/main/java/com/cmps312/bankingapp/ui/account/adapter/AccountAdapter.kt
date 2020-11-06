package com.cmps312.bankingapp.ui.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.databinding.AccountItemListBinding
import com.cmps312.bankingapp.model.Account

class AccountAdapter(
    val deleteAccountListener: (Account) -> Unit,
    val editAccountListener: (Account) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    var accounts = listOf<Account>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class AccountViewHolder(private val binding: AccountItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.apply {
                binding.account = account
                binding.deleteBtn.setOnClickListener {
                    deleteAccountListener(account)
                }
                binding.editBtn.setOnClickListener {
                    editAccountListener(account)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding: AccountItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.account_item_list,
            parent,
            false
        )
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(accounts[position])

    override fun getItemCount() = accounts.size
}