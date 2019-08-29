package com.example.thequeen.features.home.secondStepActivity

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.thequeen.R
import com.example.thequeen.databinding.ItemHorizontalCategoriesBinding
import com.example.thequeen.databinding.ItemViewPagerBinding
import com.example.thequeen.features.home.Categories
import com.example.thequeen.features.home.Image

class HorizontalsScrollAdapter(val listener: ItemClickListenerWithPosition): RecyclerView.Adapter<HorizontalsScrollAdapter.ViewHolder>() {

    lateinit var binding: ItemHorizontalCategoriesBinding
    private var categories: List<Categories>? = null
    var selectedIndex = -1

    fun setImagesProduct(categories: List<Categories>) {
        this.categories = categories
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_horizontal_categories, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (categories != null)
            categories!!.size
        else
            0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val category = categories?.get(position)
        binding.category = category

        binding.categoryTv.setOnClickListener {
            if (category != null) {
                selectedIndex = position
                listener.onItemSelected(category ,position)
                notifyDataSetChanged()
            }
        }

        if (selectedIndex == position) {
            binding.isSelected = true
            return
        } else {
            binding.isSelected = false
            return
        }

    }

    inner class ViewHolder(internal var binding: ItemHorizontalCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListenerWithPosition {
        fun onItemSelected(item: Categories, position: Int)
    }

}