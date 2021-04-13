package cz.davidkurzica.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.davidkurzica.shoppinglist.databinding.ItemShoppingBinding

class ShoppingAdapter(private val shoppingItems : List<Item>) : RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private var _binding: ItemShoppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = ItemShoppingBinding.inflate(inflater, parent, false)

        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingAdapter.ShoppingViewHolder, position: Int) {
        holder.nameTextView.text = shoppingItems[position].name
        holder.quantityTextView.text = shoppingItems[position].quantity.toString()
    }

    override fun getItemCount(): Int = shoppingItems.size

    class ShoppingViewHolder(binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameTextView: TextView = binding.textViewName
        val quantityTextView: TextView = binding.textViewQuantity
    }
}