package com.example.thequeen.features.choose_partner

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChoosePartnerViewModel: ViewModel() {

    lateinit var repository: IChoosePartnerRepository
    val hasError: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val clients: MutableLiveData<List<Client>> = MutableLiveData()

    fun initRepo(repository: IChoosePartnerRepository) {
        this.repository = repository
    }

    fun fetchClients() {
        repository.fetchDiscoveries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                clients.value = data.body()?.client
            }, {
                hasError.value = true
            })
    }
}