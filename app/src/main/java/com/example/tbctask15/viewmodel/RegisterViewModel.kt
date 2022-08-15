package com.example.tbctask15.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbctask15.Handler
import com.example.tbctask15.model.Register
import com.example.tbctask15.network.RetrofitObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val _registerState = MutableStateFlow<Handler<Register>>(Handler.Loading(true))
    val registerState = _registerState.asStateFlow()


    fun register(email: String,password: String) {
        viewModelScope.launch {
            response(email = email, password = password).collect{
                _registerState.value = it
            }
        }
    }

    private fun response(email: String, password: String) = flow<Handler<Register>> {
        emit(Handler.Loading(true))
            val response = RetrofitObj.getInfo().registerResp(
                Register.RegisterMain(
                    email = email,
                    password = password
                )
            )
            if(response.isSuccessful){
                val body = response.body()
                emit(Handler.Success(body!!))
            }
            else{
                val error = response.errorBody()?.string()
                emit(Handler.Error(error!!))

            }

    }
}