package com.example.thequeen.features.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.thequeen.R
import com.example.thequeen.databinding.ItemDressesBinding

class NewAdapter(val listener: ItemClickListenerWithPosition) :
    RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    lateinit var binding: ItemDressesBinding
    private var products: List<Product>? = null
    var selectedIndex = -1

    fun setImages(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_dresses, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (products != null)
            products!!.size
        else
            0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val product = products?.get(position)
        binding.image = product?.image?.get(0)?.image

        binding.productIv.setOnClickListener {
            if (product?.image != null) {
                listener.onDressSelected(products!!.get(position))
            }
        }

//        if (color?.code?.length!! > 5)
//            binding.colorString = color.code
//        else
//            binding.colorString = "#000000"

//        binding.colorIv.setOnClickListener {
//            selectedIndex = position
//            listener.onColorSelected(color.id)
//
//            notifyDataSetChanged()
//        }

        if (selectedIndex == position) {
            binding.root.setBackgroundColor(android.graphics.Color.GRAY)
            return
        } else {
            binding.root.setBackgroundColor(android.graphics.Color.WHITE)
            return
        }

    }

    inner class ViewHolder(internal var binding: ItemDressesBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListenerWithPosition {
        fun onDressSelected(image: Product)
    }

}