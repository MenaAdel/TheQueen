package com.example.thequeen.features.choose_partner

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.thequeen.BR
import com.example.thequeen.R
import com.example.thequeen.data.api_configration.APIClient
import com.example.thequeen.databinding.ActivityChoosePartnerBinding
import com.example.thequeen.databinding.CountryViewBinding
import com.example.thequeen.features.add_client.AddClientActivity
import com.example.thequeen.features.details.DetailsActivity
import com.example.thequeen.font.FontProvider
import com.example.thequeen.utils.CSnackbar
import com.example.thequeen.utils.CustomBottomSheet


class ChoosePartnerActivity : AppCompatActivity() {

    lateinit var binding: ActivityChoosePartnerBinding
    val viewModel by lazy {
        ViewModelProviders.of(this).get(ChoosePartnerViewModel::class.java)
    }
    val optionSheet = CustomBottomSheet<Client, CountryViewBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_partner)
        FontProvider.getInstance().init(this)
        initRepository()
        observeViewModelData()
        initClicking()
    }

    private fun initRepository() {
        viewModel.initRepo(
            ChoosePartnerRepository(
                ChoosePartnerApi(
                    APIClient.getAPIClientInstant()
                        .create(ChoosePartnerEndPoint::class.java)
                )
            )
        )
    }

    private fun initClicking(){
        binding.spinner.setOnClickListener {
            viewModel.fetchClients()
        }

        binding.addClientBtn.setOnClickListener {
            startActivity(Intent(this ,AddClientActivity::class.java))
        }
    }

    private fun observeViewModelData() {
        viewModel.isLoading.observe(this, Observer {
            it?.let { isLoading ->
                if (isLoading)
                    binding.loadingPb.visibility = View.VISIBLE
                else
                    binding.loadingPb.visibility = View.GONE
            }
        })

        viewModel.hasError.observe(this, Observer {
            it?.let { hasError ->
                if (hasError)
                    showSnackBar("No Network", Color.RED)
            }
        })

        viewModel.clients.observe(this, Observer {
            it?.let { clients ->
                if (clients.isNotEmpty())
                    showSchoolSystemSheet(clients)
            }
        })
    }

    private fun showSnackBar(msg: String, color: Int) {
        val timeErrorSnackBar = CSnackbar.SnackBuilder(binding.root, msg)
            .setMessageColor(Color.WHITE)
            .setDuration(Snackbar.LENGTH_LONG)
            .setBackroundColor(color)
            .buildSnackBar()
        timeErrorSnackBar.view.setPadding(8, 0, 8, 0)
        timeErrorSnackBar.show()
    }

    private fun showSchoolSystemSheet(clients: List<Client>) {
        val schoolSheetBinding = DataBindingUtil.inflate<CountryViewBinding>(layoutInflater, R.layout.country_view, null, false)


        optionSheet.setView(schoolSheetBinding.root)
        optionSheet.setItemId(R.layout.item_spinner)
        optionSheet.provideBindingVariableId(BR.customer)
        optionSheet.items = clients
        optionSheet.isCancelable = true
        optionSheet.show(supportFragmentManager, "")

        optionSheet.itemClickListener {
            binding.spinner.text = it.name
            optionSheet.dismiss()
            navigateToDetails(it)
        }
    }

    private fun navigateToDetails(orders: Client){
        val intent = Intent(this ,DetailsActivity::class.java)
        intent.putExtra("details" ,orders)
        startActivity(intent)
    }
}
