package com.example.campusequipmentrental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.databinding.ItemEquipmentBinding
import com.example.campusequipmentrental.model.Equipment
import com.example.campusequipmentrental.util.badgeBackground

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
                equipment.status.badgeBackground()
            )

            binding.root.alpha = if (equipment.status.isAvailable) 1.0f else 0.78f
            binding.root.setOnClickListener {
                onItemClick(equipment)
            }
        }
    }
}