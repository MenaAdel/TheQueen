package com.example.thequeen.features.details

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.thequeen.R
import com.example.thequeen.cash_data.DataSharedPreferences
import com.example.thequeen.databinding.ActivityDetailsBinding
import com.example.thequeen.features.choose_partner.Client
import com.example.thequeen.features.choose_partner.Orders
import com.example.thequeen.features.home.HomeActivity
import com.example.thequeen.font.FontProvider
import com.example.thequeen.utils.UtilsFunctions

class DetailsActivity : AppCompatActivity() , OnAddDetails{

    lateinit var binding: ActivityDetailsBinding
    lateinit var client: Client
    val preferences by lazy { DataSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        FontProvider.getInstance().init(this)
        getExtras()
        initClicking()
    }

    private fun getExtras() {
        if (intent.extras != null) {
            client = intent.run { getSerializableExtra("details") } as Client
            binding.order = client.orders
        }
    }

    private fun initClicking() {

        binding.ketfEd.setOnClickListener {
            showDialogFragment("length")
        }

        binding.ebetEd.setOnClickListener {
            showDialogFragment("back")
        }

        binding.sedrEd.setOnClickListener {
            showDialogFragment("sedr")
        }

        binding.khesrEd.setOnClickListener {
            showDialogFragment("kom")
        }

        binding.werkEd.setOnClickListener {
            showDialogFragment("werk")
        }

        binding.confirmBtn.setOnClickListener {
                navigateToHome()
        }
    }

    fun showDialogFragment(type: String){
        val bundle = Bundle()
        bundle.putString("type" ,type)
        bundle.putSerializable("order" ,client.orders)
        val lengthFragment = LengthFragment()
        lengthFragment.arguments = bundle
        lengthFragment.show(supportFragmentManager, "")
    }

    private fun navigateToHome() {
        val order = Orders(preferences.retrieveFloatFromSharedPreference("total_length") ,preferences.retrieveFloatFromSharedPreference("skirt_lenth")
            ,preferences.retrieveFloatFromSharedPreference("size_of_Chest") ,preferences.retrieveFloatFromSharedPreference("chest_rotation")
            ,preferences.retrieveFloatFromSharedPreference("waist_rotation") ,preferences.retrieveFloatFromSharedPreference("size_of_waist")
            ,preferences.retrieveFloatFromSharedPreference("display_above_the_front_chest") ,preferences.retrieveFloatFromSharedPreference("display_over_the_bac_chest")
            ,preferences.retrieveFloatFromSharedPreference("between_nahdeen") ,preferences.retrieveFloatFromSharedPreference("length_of_the_penis")
            ,preferences.retrieveFloatFromSharedPreference("shoulder_width") ,preferences.retrieveFloatFromSharedPreference("display_back")
            ,preferences.retrieveFloatFromSharedPreference("length_of_the_back") ,preferences.retrieveFloatFromSharedPreference("size_of_back")
            ,preferences.retrieveFloatFromSharedPreference("back_rotation") ,preferences.retrieveFloatFromSharedPreference("rotation_of_the_pit")
            ,preferences.retrieveFloatFromSharedPreference("front_pit") ,preferences.retrieveFloatFromSharedPreference("back_pit")
            ,preferences.retrieveFloatFromSharedPreference("wrist_rotation") ,preferences.retrieveFloatFromSharedPreference("display_the_Zend")
            ,preferences.retrieveFloatFromSharedPreference("sleeve_length"))

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("details", order)
            intent.putExtra("client", client)
            intent.putExtra(
                "screenShot1",
                UtilsFunctions.convertBitMapToBase64(UtilsFunctions.screenShot(binding.root))
            )
            startActivity(intent)
    }

    override fun onKomClicked(
        rotation_of_the_pit: String,
        front_pit: String,
        back_pit: String,
        display_the_Zend: String,
        sleeve_length: String,
        wrist_rotation: String
    ) {
        /*binding.order?.rotation_of_the_pit = rotation_of_the_pit.toFloat()
        binding.order?.front_pit = front_pit.toFloat()
        binding.order?.back_pit = back_pit.toFloat()
        binding.order?.display_the_Zend = display_the_Zend.toFloat()
        binding.order?.sleeve_length = sleeve_length.toFloat()
        binding.order?.wrist_rotation = wrist_rotation.toFloat()*/

            preferences.saveFloatToSharedPreference("rotation_of_the_pit", if (rotation_of_the_pit.isNotEmpty()) rotation_of_the_pit.toFloat() else 0f)
            preferences.saveFloatToSharedPreference("front_pit", if (front_pit.isNotEmpty()) front_pit.toFloat() else 0f)
            preferences.saveFloatToSharedPreference("back_pit", if (back_pit.isNotEmpty()) back_pit.toFloat() else 0f)
            preferences.saveFloatToSharedPreference("display_the_Zend", if (display_the_Zend.isNotEmpty())display_the_Zend.toFloat() else 0f)
            preferences.saveFloatToSharedPreference("sleeve_length", if (sleeve_length.isNotEmpty())sleeve_length.toFloat() else 0f)
            preferences.saveFloatToSharedPreference("wrist_rotation", if (wrist_rotation.isNotEmpty())wrist_rotation.toFloat() else 0f)
    }

    override fun onBackClicked(display_back: String, length_of_the_back: String) {
        /*binding.order?.display_back = display_back.toFloat()
        binding.order?.length_of_the_back = length_of_the_back.toFloat()*/

        preferences.saveFloatToSharedPreference("display_back", if (display_back.isNotEmpty()) display_back.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("length_of_the_back", if (length_of_the_back.isNotEmpty()) length_of_the_back.toFloat() else 0f)
    }

    override fun onWerkClicked(back_rotation: String, size_of_back: String) {
        /*binding.order?.back_rotation = back_rotation.toFloat()
        binding.order?.size_of_back = size_of_back.toFloat()*/

        preferences.saveFloatToSharedPreference("back_rotation" ,if (back_rotation.isNotEmpty()) back_rotation.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("size_of_back" ,if (size_of_back.isNotEmpty()) size_of_back.toFloat() else 0f)
    }

    override fun onLengthClicked(total_length: String, skirt_lenth: String) {
        /*binding.order?.total_length = total_length.toFloat()
        binding.order?.skirt_lenth = skirt_lenth.toFloat()*/

        preferences.saveFloatToSharedPreference("total_length" ,if (total_length.isNotEmpty()) total_length.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("skirt_lenth" ,if (skirt_lenth.isNotEmpty()) skirt_lenth.toFloat() else 0f)
    }

    override fun onSedrClicked(
        shoulder_width: String,
        size_of_Chest: String,
        chest_rotation: String,
        size_of_waist: String,
        wrist_rotation: String,
        display_above_the_front_chest: String,
        display_over_the_bac_chest: String,
        between_nahdeen: String,
        length_of_the_penis: String
    ) {
        /*binding.order?.shoulder_width = shoulder_width.toFloat()
        binding.order?.size_of_Chest = size_of_Chest.toFloat()
        binding.order?.chest_rotation = chest_rotation.toFloat()
        binding.order?.size_of_waist = size_of_waist.toFloat()
        binding.order?.wrist_rotation = wrist_rotation.toFloat()
        binding.order?.display_above_the_front_chest = display_above_the_front_chest.toFloat()
        binding.order?.display_over_the_bac_chest = display_over_the_bac_chest.toFloat()
        binding.order?.between_nahdeen = between_nahdeen.toFloat()
        binding.order?.length_of_the_penis = length_of_the_penis.toFloat()*/

        preferences.saveFloatToSharedPreference("shoulder_width" ,if (shoulder_width.isNotEmpty())shoulder_width.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("size_of_Chest" ,if (size_of_Chest.isNotEmpty())size_of_Chest.toFloat()else 0f)
        preferences.saveFloatToSharedPreference("chest_rotation" ,if (chest_rotation.isNotEmpty())chest_rotation.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("size_of_waist" ,if (size_of_waist.isNotEmpty())size_of_waist.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("wrist_rotation" ,if (wrist_rotation.isNotEmpty())wrist_rotation.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("display_above_the_front_chest" ,if (display_above_the_front_chest.isNotEmpty())display_above_the_front_chest.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("display_over_the_bac_chest" ,if (display_over_the_bac_chest.isNotEmpty())display_over_the_bac_chest.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("between_nahdeen" ,if (between_nahdeen.isNotEmpty())between_nahdeen.toFloat() else 0f)
        preferences.saveFloatToSharedPreference("length_of_the_penis" ,if (length_of_the_penis.isNotEmpty()) length_of_the_penis.toFloat() else 0f)
    }
}
