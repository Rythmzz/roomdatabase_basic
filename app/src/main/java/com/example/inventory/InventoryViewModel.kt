package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao:ItemDao): ViewModel() {

    private fun insertItem(item:Item){
       viewModelScope.launch {
           itemDao.insert(item)
       }
    }

    fun addNewItem(itemName: String,itemPrice: String,itemQuantity: String){
        val newItem = getItemDatabase(itemName,itemPrice,itemQuantity)
        insertItem(newItem)
    }

    private fun getItemDatabase(itemName:String, itemPrice:String, itemQuantity:String) : Item{
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemQuantity.toInt()
        )

    }

    fun isEntryValid(itemName:String, itemPrice:String, itemQuantity:String):Boolean{
        if (itemName.isBlank() || itemPrice.isBlank() || itemQuantity.isBlank()) return false
        return true
    }

    fun getItems() : Flow<List<Item>> {
        return itemDao.getItems()
    }

    fun getItem(id:Int) :Flow<Item>{
        return itemDao.getItem(id)
    }

    fun sellItem(item:Item){
       viewModelScope.launch {
           itemDao.update(Item(item.Id,item.itemName,item.itemPrice,item.quantityInStock-1))
       }
    }

    fun deleteItem(item:Item){
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknow Viewmodel Class")

    }
}