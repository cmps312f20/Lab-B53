package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.ui.transfer.adapter.TransferListAdapter
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import kotlinx.android.synthetic.main.fragment_transfer_list.*

//Todo 2: Rename it to transfer
class TransferListFragment : Fragment(R.layout.fragment_transfer_list) {
    val transferViewModel: TransferViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var transferListAdapter: TransferListAdapter
        transactionRV.apply {
            layoutManager = LinearLayoutManager(view.context)
            transferListAdapter = TransferListAdapter(view.context,::showDetails)
            adapter = transferListAdapter
        }
        transferViewModel.transfers().observe(viewLifecycleOwner){
            transferListAdapter.updateList(it)
        }
    }

    private fun showDetails(transfer: Transfer) {
        var action = TransferListFragmentDirections.toTransactionDetails()
        findNavController().navigate(action)
    }
}