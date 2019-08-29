package com.example.thequeen.features.home.secondStepActivity

import android.databinding.DataBindingUtil
import android.graphics.Color.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.thequeen.R
import com.example.thequeen.databinding.ItemColorBinding
import com.example.thequeen.features.home.Color

class ColordAdapter(val listener: ItemClickListenerWithPosition) :
    RecyclerView.Adapter<ColordAdapter.ViewHolder>() {

    lateinit var binding: ItemColorBinding
    private var colors: List<Color>? = null
    var selectedIndex = -1

    fun setColors(colors: List<Color>) {
        this.colors = colors
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_color, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (colors != null)
            colors!!.size
        else
            0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val color = colors?.get(position)

        if (color?.code?.length!! > 5)
            binding.colorString = color.code
        else
            binding.colorString = "#000000"

        binding.colorIv.setOnClickListener {
            selectedIndex = position
            listener.onColorSelected(color.id)

            notifyDataSetChanged()
        }

        if (selectedIndex == position) {
            binding.root.setBackgroundColor(GRAY)
            return
        } else {
            binding.root.setBackgroundColor(WHITE)
            return
        }

    }

    inner class ViewHolder(internal var binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListenerWithPosition {
        fun onColorSelected(colorId: Int)
    }

}