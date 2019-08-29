package com.example.thequeen.features.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel: ViewModel() {

    lateinit var repository: ILoginRepository
    val hasError: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    fun initRepo(repository: ILoginRepository) {
        this.repository = repository
    }

    fun postLogin(body: LoginBody) {

        repository.getDiscoveries(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                if (data.isSuccessful) {
                    success.value = true
                    error.value = data.body()?.status == 203
                }else {
                    success.value = false
                }
            }, {
                hasError.value = true
            })
    }

    fun checkNumberAvailability(number: String): Boolean{
        return !(number.length <= 5 && number.contains(" "))
    }
}