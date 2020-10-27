package cmps312.lab.beneficiaryreview.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cmps312.lab.beneficiaryreview.R
import cmps312.lab.beneficiaryreview.model.Beneficiary
import cmps312.lab.beneficiaryreview.ui.viewmodel.BeneficiaryViewModel
import kotlinx.android.synthetic.main.fragment_add_beneficiary.*


class AddBeneficiaryFragment : Fragment(R.layout.fragment_add_beneficiary) {
    val beneficiaryViewModel : BeneficiaryViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn.setOnClickListener {
            //read all the data from the inputs
            //create beneficiary object
            //send this object to my viewmodel

            val beneficiary = Beneficiary()

            beneficiary.apply {
                name = nameEdt.text.toString()
                accountNo = accountNoEdt.text.toString()
                cid = cidEdt.text.toString().toInt()
            }

            beneficiaryViewModel.addBeneficiary(beneficiary)
            activity?.onBackPressed()
        }
    }
}