package com.example.thequeen.features.add_client

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.thequeen.data.base_response.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCLientViewModel: ViewModel() {

    lateinit var repository: IAddClientRepository
    val hasError: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val response: MutableLiveData<AddClientResponse> = MutableLiveData()

    fun initRepo(repository: IAddClientRepository) {
        this.repository = repository
    }

    fun fetchClients(body: AddClientBody) {
        repository.postCLient(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                if(data.body()?.status == 200){
                    response.value = data.body()
                }

            }, {
                hasError.value = true
            })
    }
}