package com.example.thequeen.features.home.secondStepActivity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.thequeen.features.home.IHomeRepository
import com.example.thequeen.features.home.Product
import com.example.thequeen.features.home.products.ProductsBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SecondStepViewModel : ViewModel() {

    lateinit var repository: IHomeRepository
    val hasError: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val response: MutableLiveData<Product> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()
    val disposables = CompositeDisposable()

    fun initRepo(repository: IHomeRepository) {
        this.repository = repository
    }

    fun fetchHome(body: ProductsBody) {
        disposables.add(repository.fetchProduct(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                if (data.isSuccessful) {
                    response.value = data.body()?.product
                } else {
                    hasError.value = false
                }
            }, {
                hasError.value = true
            })
        )
    }

    fun postOrder(body: AddProductBody) {
        disposables.add(repository.postOrder(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe({ data ->
                if (data.body()?.status == 200) {
                    success.value = true
                } else {
                    hasError.value = false
                }
            }, {
                hasError.value = true
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}