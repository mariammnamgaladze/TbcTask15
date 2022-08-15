package com.example.tbctask15.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbctask15.R
import com.example.tbctask15.Handler
import com.example.tbctask15.databinding.FragmentLogInBinding
import com.example.tbctask15.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {


    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        getRes()

    }

    private fun onClick() {
        binding.loginButton.setOnClickListener {
            if (binding.logInEmail.text!!.isNotEmpty() || binding.logInPassword.text!!.isNotEmpty()) {
                viewModel.logIn(
                    binding.logInEmail.text.toString(),
                    binding.logInPassword.text.toString()
                )
            }
        }
    }

    private fun getRes() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.loginState.collect {
                    when (it) {
                        is Handler.Error -> {
                            view?.let { it1 ->
                                Snackbar.make(
                                    it1,
                                    it.errorData,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is Handler.Loading -> {
                            Log.d("loading", "${it.loader}")
                        }
                        is Handler.Success -> {
                            view?.let { it1 ->
                                Snackbar.make(
                                    it1,
                                    "welcome user",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}