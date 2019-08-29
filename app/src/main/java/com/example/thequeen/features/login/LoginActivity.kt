package com.example.thequeen.features.login

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
import com.example.thequeen.databinding.ActivityLoginBinding
import com.example.thequeen.features.choose_partner.ChoosePartnerActivity
import com.example.thequeen.font.FontProvider
import com.example.thequeen.utils.CSnackbar

class LoginActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        FontProvider.getInstance().init(this)
        initRepository()
        initClicking()
        observeViewModelData()
    }

    private fun initRepository() {
        viewModel.initRepo(
            LoginRepository(
                LoginApi(
                    APIClient.getAPIClientInstant()
                        .create(LoginEndPoint::class.java)
                )
            )
        )
    }

    private fun initClicking() {
        binding.loginBtn.setOnClickListener {
            if (viewModel.checkNumberAvailability(binding.mobileNumber.text.toString().trim()) && binding.password.text.toString().trim().isNotEmpty())
                viewModel.postLogin(
                    LoginBody(
                        binding.mobileNumber.text.toString().trim(),
                        binding.password.text.toString().trim()
                    )
                )
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
                    showSnackBar("No Network" ,Color.RED)
            }
        })

        viewModel.success.observe(this, Observer {
            it?.let { success ->
                if (success)
                    startActivity(Intent(this ,ChoosePartnerActivity::class.java))
                else
                    Toast.makeText(this ,"user name error" ,Toast.LENGTH_LONG).show()
            }
        })

        viewModel.error.observe(this, Observer {
            it?.let { error ->
                if (error)
                    Toast.makeText(this ,"wrong password" ,Toast.LENGTH_LONG).show()
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
