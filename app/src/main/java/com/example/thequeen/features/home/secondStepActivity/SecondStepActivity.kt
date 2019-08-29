package com.example.thequeen.features.home.secondStepActivity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.thequeen.BR
import com.example.thequeen.R
import com.example.thequeen.data.api_configration.APIClient
import com.example.thequeen.databinding.ActivitySecondStepBinding
import com.example.thequeen.databinding.ItemViewPagerBinding
import com.example.thequeen.features.choose_partner.ChoosePartnerActivity
import com.example.thequeen.features.choose_partner.Client
import com.example.thequeen.features.choose_partner.Orders
import com.example.thequeen.features.home.*
import com.example.thequeen.features.home.products.ProductsBody
import com.example.thequeen.features.home.secondStepActivity.signature.SignatureActivityy
import com.example.thequeen.font.FontProvider
import com.example.thequeen.utils.CSnackbar
import com.example.thequeen.utils.SimpleBindingRecyclerAdapter
import com.example.thequeen.utils.UtilsFunctions


class SecondStepActivity : AppCompatActivity(), ColordAdapter.ItemClickListenerWithPosition ,ClothAdapter.ItemClickListenerWithPosition {

    lateinit var binding: ActivitySecondStepBinding
    lateinit var clothAdapter: ClothAdapter
    lateinit var colorAdapter: ColordAdapter
    lateinit var viewPagerAdapter: SimpleBindingRecyclerAdapter<Image, ItemViewPagerBinding>
    var signature: String = ""
    var screenShot1: String = ""
    var screenShot2: String = ""
    lateinit var orders: Orders
    lateinit var client: Client
    var productId = 0
    var product : Product? = null
    var colorId : Int? = null
    var cloth : Int? = null

    val viewModel by lazy {
        ViewModelProviders.of(this).get(SecondStepViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step)
        FontProvider.getInstance().init(this)
        initRepository()
        getExtras()
        initView()
        observeData()
        initClicking()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getStringExtra("signature")
                signature = result
                screenShot2 = UtilsFunctions.convertBitMapToBase64(UtilsFunctions.screenShot(binding.root))
                binding.signatureIv.visibility = View.VISIBLE
//                binding.image = signature
                binding.signatureIv.setImageBitmap(UtilsFunctions.decodeImage(signature))
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private fun initRepository() {
        viewModel.initRepo(
            HomeRepository(
                HomeApi(
                    APIClient.getAPIClientInstant()
                        .create(HomeEndPoint::class.java)
                )
            )
        )
    }

    private fun initClicking() {

        binding.signature.setOnClickListener {
            val intent = Intent(this, SignatureActivityy::class.java)
            startActivityForResult(intent, 1)
        }


        binding.confirmBtn.setOnClickListener {
            if (product != null && colorId != null && cloth != null && signature.isNotEmpty()
                && binding.mobileEd.text.toString().isNotEmpty() && binding.addressEd.text.toString().isNotEmpty() && binding.nameEd.text.toString().isNotEmpty()){
                    viewModel.postOrder(AddProductBody(productId , client.id ,colorId!! ,cloth!! ,signature
                    ,binding.mobileEd.text.toString() ,binding.addressEd.text.toString() ,binding.nameEd.text.toString() ,screenShot1 ,screenShot2 ,
                        orders.total_length,orders.skirt_lenth ,orders.size_of_Chest ,orders.chest_rotation ,orders.waist_rotation ,
                        orders.size_of_waist ,orders.display_above_the_front_chest ,orders.display_over_the_bac_chest ,orders.between_nahdeen ,
                        orders.length_of_the_penis ,orders.shoulder_width ,orders.display_back ,orders.length_of_the_back ,orders.size_of_back ,
                        orders.back_rotation ,orders.rotation_of_the_pit ,orders.front_pit ,orders.back_pit ,orders.wrist_rotation ,
                        orders.display_the_Zend ,orders.sleeve_length))
            }
            else{
                Toast.makeText(this ,"please ,Complete all data" ,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initView() {

        if (client != null){
            binding.nameEd.setText(client.name)
            binding.addressEd.setText(client.address)
            binding.mobileEd.setText(client.phone)
        }

        clothAdapter = ClothAdapter(this)//SimpleBindingRecyclerAdapter(R.layout.item_radio_button, BR.cloth)
        binding.clothRcl.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.clothRcl.adapter = clothAdapter

        colorAdapter = ColordAdapter(this)
        binding.colorRcl.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.colorRcl.adapter = colorAdapter

        viewPagerAdapter = SimpleBindingRecyclerAdapter(R.layout.item_view_pager, BR.img)

        binding.discreteScrollView.setOffscreenItems(3)
        binding.discreteScrollView.adapter = viewPagerAdapter

    }

    private fun getExtras() {
        if (intent.extras != null) {
            viewModel.fetchHome(ProductsBody(intent.getIntExtra("productId", 0)))
            orders = (intent.run { getSerializableExtra("details") } as Orders?)!!
            client = intent.run { getSerializableExtra("client") } as Client
            productId = intent.getIntExtra("productId", 1)
            screenShot1 = intent.run { extras.getString("screenShot1") }

        }
    }

    private fun observeData() {
        viewModel.isLoading.observe(this, Observer {
            it?.let {
                if (it)
                    binding.loadingPb.visibility = View.VISIBLE
                else
                    binding.loadingPb.visibility = View.GONE

            }
        })

        viewModel.response.observe(this, Observer {
            it?.let {
                product = it
                binding.product = it
                viewPagerAdapter.setItems(it.image)
                clothAdapter.setCloths(it.cloth)
                if (it.color.isNotEmpty())
                    colorAdapter.setColors(it.color)
            }
        })

        viewModel.hasError.observe(this, Observer {
            it?.let {
                if (it)
                    showSnackBar("No Network", android.graphics.Color.RED)

            }
        })

        viewModel.success.observe(this, Observer {
            it?.let {
                if (it){
                    showSnackBar("order added", android.graphics.Color.GREEN)
                    startActivity(Intent(this ,ChoosePartnerActivity::class.java))
                }

            }
        })

    }

    private fun showSnackBar(msg: String, color: Int) {
        val timeErrorSnackBar = CSnackbar.SnackBuilder(binding.root, msg)
            .setMessageColor(android.graphics.Color.WHITE)
            .setDuration(Snackbar.LENGTH_LONG)
            .setBackroundColor(color)
            .buildSnackBar()
        timeErrorSnackBar.view.setPadding(8, 0, 8, 0)
        timeErrorSnackBar.show()
    }

    /*override fun onItemSelected(productId: Int) {
    }*/

    override fun onColorSelected(colorId: Int) {
        this.colorId = colorId
    }

    override fun onClothSelected(clothId: Int) {
        cloth = clothId
    }
}
