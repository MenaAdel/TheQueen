package com.example.thequeen.features.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.thequeen.R
import com.example.thequeen.data.api_configration.APIClient
import com.example.thequeen.databinding.ActivityHomeBinding
import com.example.thequeen.features.choose_partner.Client
import com.example.thequeen.features.choose_partner.Orders
import com.example.thequeen.features.home.secondStepActivity.HorizontalsScrollAdapter
import com.example.thequeen.features.home.secondStepActivity.SecondStepActivity
import com.example.thequeen.font.FontProvider

class HomeActivity : AppCompatActivity() , HorizontalsScrollAdapter.ItemClickListenerWithPosition ,NewAdapter.ItemClickListenerWithPosition {

    lateinit var binding: ActivityHomeBinding
    lateinit var adapter: HorizontalsScrollAdapter//SimpleBindingRecyclerAdapter<Categories, ItemHorizontalCategoriesBinding>
    lateinit var adapterProducts: NewAdapter//SimpleBindingRecyclerAdapter<Image, ItemDressesBinding>
    lateinit var orders: Orders
    lateinit var client: Client
    lateinit var screenShot: String
    val viewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        FontProvider.getInstance().init(this)
        initRepository()
        initView()
        viewModel.fetchHome()
        observeData()
        getExtras()
    }

    private fun getExtras() {
        if (intent.extras != null) {
            orders = (intent.run { getSerializableExtra("details") } as Orders?)!!
            client = intent.run { getSerializableExtra("client") } as Client
            screenShot = intent.run { extras.getString("screenShot1") }
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
                adapter.setImagesProduct(it)
                if (it[0].products != null) {
                    adapterProducts.setImages(it[0].products)
                    binding.subProductRv.visibility = View.VISIBLE
                    binding.emptyTv.visibility = View.GONE
                } else {
                    binding.subProductRv.visibility = View.GONE
                    binding.emptyTv.visibility = View.VISIBLE
                }


            }
        })

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

    private fun initView() {
        adapter = HorizontalsScrollAdapter(this)//SimpleBindingRecyclerAdapter(R.layout.item_horizontal_categories, BR.category)
        binding.productRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.productRv.adapter = adapter

        adapterProducts = NewAdapter(this)//SimpleBindingRecyclerAdapter(R.layout.item_dresses, BR.product)
        binding.subProductRv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        binding.subProductRv.adapter = adapterProducts

//        adapter.setPositionListener { item, position ->
//            if (item.products.isNotEmpty()) {
//                adapterProducts.setItems(item.products[0].image)
//                binding.subProductRv.visibility = View.VISIBLE
//                binding.emptyTv.visibility = View.GONE
//            } else {
//                binding.subProductRv.visibility = View.GONE
//                binding.emptyTv.visibility = View.VISIBLE
//            }
//        }

//        adapterProducts.setPositionListener { item, position ->
//            val intent = Intent(this, SecondStepActivity::class.java)
//            intent.putExtra("productId", item.product_id)
//            intent.putExtra("details", orders)
//            intent.putExtra("client", client)
//            intent.putExtra("screenShot1", screenShot)
//            startActivity(intent)
//        }

    }

    override fun onItemSelected(item: Categories, position: Int) {
        if (item.products.isNotEmpty()) {
            adapterProducts.setImages(item.products)
            binding.subProductRv.visibility = View.VISIBLE
            binding.emptyTv.visibility = View.GONE
        } else {
            binding.subProductRv.visibility = View.GONE
            binding.emptyTv.visibility = View.VISIBLE
        }
    }

    override fun onDressSelected(image: Product) {
        val intent = Intent(this, SecondStepActivity::class.java)
        intent.putExtra("productId", image.id)
        intent.putExtra("details", orders)
        intent.putExtra("client", client)
        intent.putExtra("screenShot1", screenShot)
        startActivity(intent)
    }

}
