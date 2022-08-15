package com.example.tbctask15.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbctask15.Handler
import com.example.tbctask15.model.Login
import com.example.tbctask15.network.RetrofitObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow<Handler<Login>>(Handler.Loading(true))
    val loginState = _loginState.asStateFlow()


    fun logIn(email: String,password: String) {
        viewModelScope.launch {
            response(email = email, password = password).collect{
                _loginState.value = it
            }
        }
    }

    private fun response(email: String, password: String) = flow<Handler<Login>> {
        emit(Handler.Loading(true))
        val response = RetrofitObj.getInfo().loginResp(
            Login.LoginMain(
                email = email,
                password = password
            )
        )
        if (response.isSuccessful) {
            val body = response.body()
            emit(Handler.Success(body!!))
        } else {
            val error = response.errorBody()?.toString()
            emit(Handler.Error(error!!))

        }
    }
}