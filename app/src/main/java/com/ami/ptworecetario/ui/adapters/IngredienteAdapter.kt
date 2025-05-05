package com.example.recetaspiola.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.graphics.toColorInt
import com.ami.ptworecetario.databinding.ListAddIngredientesBinding
import dp.models.Ingrediente

class IngredienteAdapter(
    var ingredients: ArrayList<Ingrediente>,
    private val onIngredientClick: (Ingrediente) -> Unit,
    private val isIngredientSelected: (Ingrediente) -> Boolean
) : RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListAddIngredientesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ingredients[position]
        holder.bind(item, onIngredientClick, isIngredientSelected)
    }

    fun setData(newIngredients: List<Ingrediente>) {
        ingredients.clear()
        ingredients.addAll(newIngredients)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListAddIngredientesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Ingrediente,
            onClick: (Ingrediente) -> Unit,
            isSelected: (Ingrediente) -> Boolean
        ) {
            binding.btnIngredient.text = item.name

            val color = if (isSelected(item)) "#92d2f7" else "#ffffff"
            binding.btnIngredient.setBackgroundColor(color.toColorInt())

            binding.btnIngredient.setOnClickListener {
                onClick(item)
            }
        }
    }
}
