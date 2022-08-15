package com.example.tbctask15.model

data class Login(
    val s: String?
)
{
    data class LoginMain(
        val email:String?,
        val password:String?
    )
}