package com.example.inventory.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.ItemListFragmentDirections
import com.example.inventory.data.Item
import com.example.inventory.databinding.ItemListFragmentBinding
import com.example.inventory.databinding.ItemListItemBinding
import kotlinx.coroutines.flow.Flow

class ItemAdapter(private var items: List<Item>, private val fragment:Fragment) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val binding:ItemListItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item:Item){
            binding.itemName.setText(item.itemName)
            binding.itemPrice.setText(item.itemPrice.toString())
            binding.itemQuantity.setText(item.quantityInStock.toString())
            binding.itemLayout.setOnClickListener {
                val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item.Id)
                fragment.findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(items:List<Item>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val binding = ItemListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}