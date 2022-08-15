package com.example.tbctask15.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbctask15.R
import com.example.tbctask15.Handler
import com.example.tbctask15.databinding.FragmentRegisterBinding
import com.example.tbctask15.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {


    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCLick()
        getInfo()
    }

    private fun onCLick(){

        binding.registerBtn2.setOnClickListener {
            if (binding.registerEmail.text!!.isNotEmpty() || binding.registerPassword.text!!.isNotEmpty()) {
                viewModel.register(
                    binding.registerEmail.text.toString(),
                    binding.registerPassword.text.toString()
                )
            }
        }
    }

    private fun getInfo (){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    when (it) {
                        is Handler.Error -> {
                            view?.let { it1 -> Snackbar.make(it1, it.errorData, Snackbar.LENGTH_SHORT).show() }
                        }
                        is Handler.Loading -> {
                            Log.d("loading", "${it.loader}")
                        }
                        is Handler.Success -> {
                            view?.let { it1 ->
                                Snackbar.make(
                                    it1,
                                    "welcome, you have registered Successfully ",
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