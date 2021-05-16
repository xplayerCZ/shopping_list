package cz.davidkurzica.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.davidkurzica.shoppinglist.R
import cz.davidkurzica.shoppinglist.data.Item
import cz.davidkurzica.shoppinglist.databinding.ItemShoppingBinding
import java.security.InvalidParameterException

/**
 * [RecyclerView.Adapter] that can display a [Item].
 */

class ShoppingAdapter(
    private val shoppingItems: MutableList<Item>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            ItemShoppingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]

        holder.nameTextView.text = shoppingItem.name
        holder.quantityTextView.text = shoppingItem.quantity.toString()
        holder.categoryImageView.setImageResource(
            when (shoppingItem.category) {
                "ovoce" -> R.drawable.ovoce
                "zelenina" -> R.drawable.zelenina
                "oblečení" -> R.drawable.obleceni
                "spotřební zboží" -> R.drawable.spotrebni_zbozi
                else -> R.drawable.jine
            }
        )
    }

    override fun getItemCount(): Int = shoppingItems.size

    inner class ShoppingViewHolder(binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTextView: TextView = binding.textViewName
        val quantityTextView: TextView = binding.textViewQuantity
        val categoryImageView: ImageView = binding.imageViewCategory

        init {
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}