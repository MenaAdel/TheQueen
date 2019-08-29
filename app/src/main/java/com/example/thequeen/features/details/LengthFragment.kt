package com.example.thequeen.features.details

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thequeen.R
import com.example.thequeen.databinding.FragmentLengthBinding
import com.example.thequeen.features.choose_partner.Orders
import android.content.Context


class LengthFragment: DialogFragment() {

    lateinit var binding: FragmentLengthBinding
    lateinit var listener: OnAddDetails

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddDetails) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_length, container, false)
        getExtraData()
        initClicking()
        return binding.root

    }

    private fun getExtraData(){
        if (arguments != null) {
            binding.type = arguments?.getString("type")
            binding.order = arguments?.getSerializable("order") as? Orders
        }
    }

    private fun initClicking(){
        binding.doneBtn.setOnClickListener {
            if (binding.type == "kom")
                listener.onKomClicked(binding.circleHoleEd.text.toString() ,binding.lengthFrontHoleEd.text.toString() ,binding.lengthBackHoleEd.text.toString() ,
                    binding.zendWidthEd.text.toString() ,binding.komLengthEd.text.toString() ,binding.mesamCircleEd.text.toString())
            else if (binding.type == "back")
                listener.onBackClicked(binding.backWidthEd.text.toString() ,binding.backHeightEd.text.toString())
            else if (binding.type == "werk")
                listener.onWerkClicked(binding.circleWerkEd.text.toString() ,binding.werkLengthEd.text.toString())
            else if (binding.type == "length")
                listener.onLengthClicked(binding.allLengthEd.text.toString() ,binding.tanoraLengthEd.text.toString())
            else if (binding.type == "sedr")
                listener.onSedrClicked(binding.shoulderWidthEd.text.toString() ,binding.sedrHeightEd.text.toString() ,binding.sedrCircleEd.text.toString() ,
                    binding.khesrLengthEd.text.toString() ,binding.khesrCircleEd.text.toString() ,binding.frontWidthSedrEd.text.toString() ,
                    binding.backWidthSedrEd.text.toString() ,binding.betweenNahdenEd.text.toString() ,binding.benasLengthEd.text.toString())

            this.dismiss()
        }
    }



}

interface OnAddDetails {
    fun onKomClicked(rotation_of_the_pit: String ,front_pit :String ,back_pit: String ,display_the_Zend: String ,sleeve_length: String ,wrist_rotation: String)
    fun onBackClicked(display_back: String ,length_of_the_back :String)
    fun onWerkClicked(back_rotation: String ,size_of_back :String)
    fun onLengthClicked(total_length: String ,skirt_lenth :String)
    fun onSedrClicked(shoulder_width: String ,size_of_Chest :String ,chest_rotation: String ,size_of_waist: String ,wrist_rotation: String ,
                      display_above_the_front_chest: String ,display_over_the_bac_chest: String ,between_nahdeen: String ,length_of_the_penis: String)
}