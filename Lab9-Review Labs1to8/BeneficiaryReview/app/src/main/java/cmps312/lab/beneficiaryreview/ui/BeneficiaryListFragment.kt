package cmps312.lab.beneficiaryreview.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.beneficiaryreview.R
import cmps312.lab.beneficiaryreview.model.Beneficiary
import cmps312.lab.beneficiaryreview.ui.adapter.BeneficiaryAdapter
import cmps312.lab.beneficiaryreview.ui.viewmodel.BeneficiaryViewModel
import kotlinx.android.synthetic.main.fragment_beneficiary_list.*


class BeneficiaryListFragment : Fragment(R.layout.fragment_beneficiary_list) {
    //shared view model
    val beneficiariesViewModel : BeneficiaryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val beneficiaryAdapter = BeneficiaryAdapter(::deleteBeneficiaryListener)

        beneficiaryRV.apply {
              adapter = beneficiaryAdapter
              layoutManager = LinearLayoutManager(context)
        }
        beneficiariesViewModel.beneficiaries.observe(viewLifecycleOwner){
            beneficiaryAdapter.beneficiaries = it
        }

        addBtn.setOnClickListener {
            findNavController().navigate(R.id.toAddBeneficiaryFragment)
        }
    }

    fun deleteBeneficiaryListener(beneficiary: Beneficiary){
        beneficiariesViewModel.deleteBeneficiary(beneficiary)
    }
}