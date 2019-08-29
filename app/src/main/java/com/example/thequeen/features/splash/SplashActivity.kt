package com.example.thequeen.features.splash

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.thequeen.R
import com.example.thequeen.databinding.ActivityMainBinding
import com.example.thequeen.features.choose_partner.ChoosePartnerActivity
import com.example.thequeen.features.login.LoginActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val disposibles = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_main)

        disposibles.add(Completable.complete()
            .delay(3000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { navigateToLogInScreen() })
    }

    private fun navigateToLogInScreen(){
        startActivity(Intent(this ,LoginActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposibles.clear()
    }
}
