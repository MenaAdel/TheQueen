package com.example.thequeen.features.home.secondStepActivity

import android.databinding.DataBindingUtil
import android.graphics.Color.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.thequeen.R
import com.example.thequeen.databinding.ItemColorBinding
import com.example.thequeen.databinding.ItemRadioButtonBinding
import com.example.thequeen.features.home.Cloth
import com.example.thequeen.features.home.Color

class ClothAdapter(val listener: ItemClickListenerWithPosition) :
    RecyclerView.Adapter<ClothAdapter.ViewHolder>() {

    lateinit var binding: ItemRadioButtonBinding
    private var cloths: List<Cloth>? = null
    var selectedIndex = -1

    fun setCloths(cloths: List<Cloth>) {
        this.cloths = cloths
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_radio_button, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (cloths != null)
            cloths!!.size
        else
            0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val cloth = cloths?.get(position)
        binding.cloth = cloth
        binding.radioButton.setOnClickListener {
            selectedIndex = position
            listener.onClothSelected(cloth!!.id)
            notifyDataSetChanged()
        }

        if (selectedIndex == position) {
            binding.radioButton.isChecked = true
        } else {
            binding.radioButton.isChecked = false
        }


    }

    inner class ViewHolder(internal var binding: ItemRadioButtonBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListenerWithPosition {
        fun onClothSelected(clothId: Int)
    }

}