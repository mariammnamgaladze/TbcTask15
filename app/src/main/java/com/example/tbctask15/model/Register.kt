package com.example.tbctask15.model

data class Register(
    val id: Int,
    val s: String?
)
{
    data class RegisterMain(
        val email:String?,
        val password:String?,
    )
}