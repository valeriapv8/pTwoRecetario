package com.example.recetaspiola.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ami.ptworecetario.databinding.RecetaItemLayoutBinding
import com.ami.ptworecetario.db.models.RecetaConIngredientes

class RecetaAdapter(
    private var recipes: List<RecetaConIngredientes>,
    private val onRecipeClick: (RecetaConIngredientes) -> Unit
) : RecyclerView.Adapter<RecetaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecetaItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipeWithIngredients: RecetaConIngredientes) {
            binding.textRecipeName.text = recipeWithIngredients.recipe.name
            binding.root.setOnClickListener {
                onRecipeClick(recipeWithIngredients)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecetaItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    fun setData(newRecipes: List<RecetaConIngredientes>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
