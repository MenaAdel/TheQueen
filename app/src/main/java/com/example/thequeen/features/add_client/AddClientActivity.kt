package com.example.thequeen.features.add_client

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.thequeen.R
import com.example.thequeen.data.api_configration.APIClient
import com.example.thequeen.databinding.ActivityAddClientBinding
import com.example.thequeen.features.choose_partner.ChoosePartnerActivity
import com.example.thequeen.features.choose_partner.Client
import com.example.thequeen.features.choose_partner.Orders
import com.example.thequeen.features.details.DetailsActivity
import com.example.thequeen.font.FontProvider
import com.example.thequeen.utils.CSnackbar

class AddClientActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProviders.of(this).get(AddCLientViewModel::class.java)
    }

    lateinit var binding: ActivityAddClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_client)
        FontProvider.getInstance().init(this)
        initRepository()
        initClicking()
        observeData()
    }

    private fun initRepository() {
        viewModel.initRepo(
            AddCLientRepository(
                AddClientApi(
                    APIClient.getAPIClientInstant()
                        .create(AddClientEndPoint::class.java)
                )
            )
        )
    }

    private fun initClicking() {
        binding.addClientBtn.setOnClickListener {
            if (binding.nameTv.text.toString().trim().isNotEmpty() &&
                binding.numberTv.text.toString().trim().isNotEmpty() && binding.addressTv.text.toString().trim().isNotEmpty()
            ) {
                viewModel.fetchClients(
                    AddClientBody(
                        binding.nameTv.text.toString().trim(),
                        binding.numberTv.text.toString().trim(),
                        binding.addressTv.text.toString().trim()
                    )
                )
            } else {
                if (binding.nameTv.text.toString().trim().isEmpty())
                    Toast.makeText(this, "name is required", Toast.LENGTH_LONG).show()
                else if (binding.numberTv.text.toString().trim().isEmpty())
                    Toast.makeText(this, "mobile number is required", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "address is required", Toast.LENGTH_LONG).show()


            }
        }
    }

    private fun observeData() {
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

        viewModel.response.observe(this, Observer {
            it?.let { response ->
                val order = Orders()
                val client = Client(it.client_id, it.client_name, it.client_address, it.client_phone, order)
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("details", client)
                startActivity(intent)
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
}
