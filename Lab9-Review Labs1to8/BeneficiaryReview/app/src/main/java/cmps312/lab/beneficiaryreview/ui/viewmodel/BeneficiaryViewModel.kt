package cmps312.lab.beneficiaryreview.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cmps312.lab.beneficiaryreview.data.repository.BeneficiaryRepo
import cmps312.lab.beneficiaryreview.model.Beneficiary
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BeneficiaryViewModel : ViewModel() {
    private val _beneficiaries = getBeneficiaries() as MutableLiveData

    fun getBeneficiaries() = liveData {
        emit(BeneficiaryRepo.getBeneficiaries())
    }

    val beneficiaries = _beneficiaries

    fun addBeneficiary(beneficiary: Beneficiary) {
        viewModelScope.launch {
            val addedBeneficiary = async { BeneficiaryRepo.addBeneficiaries(beneficiary) }.await()
            Log.d("TAG", "addBeneficiary: $addedBeneficiary")
            _beneficiaries.value?.let {
                _beneficiaries.value = it + addedBeneficiary
            }
        }
    }

    fun deleteBeneficiary(beneficiary: Beneficiary) {
        viewModelScope.launch {

            async { BeneficiaryRepo.deleteBeneficiaries(beneficiary) }.await()
            _beneficiaries.value?.let {
                _beneficiaries.value = it - beneficiary
            }
        }
    }
}