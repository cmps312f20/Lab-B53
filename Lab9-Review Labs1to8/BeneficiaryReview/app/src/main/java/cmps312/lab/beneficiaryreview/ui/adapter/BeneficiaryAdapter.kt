package cmps312.lab.beneficiaryreview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.beneficiaryreview.R
import cmps312.lab.beneficiaryreview.databinding.BeneficiaryListItemBinding
import cmps312.lab.beneficiaryreview.model.Beneficiary

class BeneficiaryAdapter(val deleteBeneficiaryListener : (Beneficiary) -> Unit) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryVH>() {

    var beneficiaries = listOf<Beneficiary>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class BeneficiaryVH (val binding : BeneficiaryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(beneficiary: Beneficiary){
            binding.beneficiary = beneficiary
            binding.deleteBenBtn.setOnClickListener {deleteBeneficiaryListener(beneficiary)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryVH {
        val binding : BeneficiaryListItemBinding = DataBindingUtil.inflate(
            //4 params
            LayoutInflater.from(parent.context),
            R.layout.beneficiary_list_item,
            parent,
            false
        )
        return BeneficiaryVH(binding)
    }

    override fun onBindViewHolder(holder: BeneficiaryVH, position: Int) = holder.bind(beneficiaries[position])
    override fun getItemCount() = beneficiaries.size
}