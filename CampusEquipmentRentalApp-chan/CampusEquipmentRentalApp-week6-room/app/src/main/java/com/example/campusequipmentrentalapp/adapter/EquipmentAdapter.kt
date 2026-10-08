package com.example.campusequipmentrentalapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.databinding.ItemEquipmentBinding
import com.example.campusequipmentrentalapp.model.Equipment
import com.example.campusequipmentrentalapp.model.RentalStatus

/**
 * Equipment 데이터를 item_equipment.xml에 연결하는 Adapter입니다.
 */
class EquipmentAdapter(
    private val items: List<Equipment>,
    private val onItemClick: (Equipment) -> Unit
) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentViewHolder {
        val binding = ItemEquipmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EquipmentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EquipmentViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class EquipmentViewHolder(
        private val binding: ItemEquipmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(equipment: Equipment) {
            binding.tvEquipmentIcon.text = equipment.icon
            binding.tvEquipmentName.text = equipment.name
            binding.tvEquipmentCategory.text = equipment.category
            binding.tvEquipmentLocation.text = equipment.location
            binding.tvMaxDays.text = binding.root.context.getString(
                R.string.max_days_short_format,
                equipment.maxRentalDays
            )
            binding.tvEquipmentStatus.text = equipment.status.label
            binding.tvEquipmentStatus.setBackgroundResource(
                statusBackground(equipment.status)
            )

            binding.root.alpha = if (equipment.status.isAvailable) 1.0f else 0.78f
            binding.root.setOnClickListener {
                onItemClick(equipment)
            }
        }

        private fun statusBackground(status: RentalStatus): Int =
            when (status) {
                RentalStatus.AVAILABLE -> R.drawable.bg_status_available
                RentalStatus.RENTED -> R.drawable.bg_status_unavailable
                RentalStatus.MAINTENANCE -> R.drawable.bg_status_maintenance
            }
    }
}