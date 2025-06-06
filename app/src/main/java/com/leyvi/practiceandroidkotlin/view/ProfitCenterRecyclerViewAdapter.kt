package com.leyvi.practiceandroidkotlin.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leyvi.practiceandroidkotlin.databinding.RowProfitCenterBinding
import com.leyvi.practiceandroidkotlin.model.entity.ProfitCenter

class ProfitCenterRecyclerViewAdapter(
    private val profitCenters: List<ProfitCenter>,
) : RecyclerView.Adapter<ProfitCenterRecyclerViewAdapter.ProfitCenterRecyclerViewHolder>() {

    companion object {
        private val TAG = ProfitCenterRecyclerViewAdapter::class.simpleName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProfitCenterRecyclerViewHolder {
        val binding = RowProfitCenterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ProfitCenterRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProfitCenterRecyclerViewHolder,
        position: Int,
    ) {
        val profitCenter = profitCenters[position]
        holder.textCode.text = profitCenter.code
        holder.textDisplayName.text = profitCenter.displayName
        holder.itemView.setOnLongClickListener {
            Log.d(TAG, "position $position got long clicked")
            true
        }
    }

    override fun getItemCount(): Int = profitCenters.size

    class ProfitCenterRecyclerViewHolder(
        binding: RowProfitCenterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val textCode = binding.textProfitCenterCode
        val textDisplayName = binding.textProfitCenterDisplayName
    }
}