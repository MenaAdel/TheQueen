package com.example.thequeen.features.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    lateinit var repository: IHomeRepository
    val hasError: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val response: MutableLiveData<List<Categories>> = MutableLiveData()
    val color: MutableLiveData<List<Color>> = MutableLiveData()

    fun initRepo(repository: IHomeRepository) {
        this.repository = repository
    }

    fun fetchHome(){
        repository.fetchHome()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                if (data.isSuccessful) {
                    response.value = data.body()?.categories
                }else {
                    hasError.value = false
                }
            }, {
                hasError.value = true
            })
    }

}