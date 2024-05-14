package ru.hse.smart_control.ui.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.musfickjamil.snackify.Snackify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.smart_control.databinding.FragmentRegisterBinding
import ru.hse.smart_control.domain.ApiResponse
import ru.hse.smart_control.model.user.RegisterModel
import android.content.Context
import android.view.inputmethod.InputMethodManager

class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    interface OnButtonClickedListener {
        fun onButtonClicked()
    }

    private lateinit var buttonClickListener: OnButtonClickedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClickListener = parentFragment as OnButtonClickedListener

        binding.btnRegister.setOnClickListener {
            with(binding) {
                name = tvName.text.toString()
                email = tvEmail.text.toString()
                password = tvPassword.text.toString()
            }
            hideKeyboard(it)

//            viewModel.register2(RegisterModel(name, email, password))
//            viewLifecycleOwner.lifecycleScope.launch {
//                viewModel.events.collect { event ->
//                    when (event) {
//                        is UserEvent.Error -> {
//                            // Handle error here
//                            Log.d("TAG", "event.error: ${event.error}")
//                            Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
//                        }
//                        // If you have other events
//                    }
//                }
//            }

             viewModel.register(RegisterModel(name, email, password))
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.registerResult.collectLatest {
                    when (it) {
                        is ApiResponse.Success -> {
                            Snackify.success(
                                binding.linearLayout,
                                "Аккаунт создан!",
                                Snackify.LENGTH_LONG
                            ).show()
                            withContext(Dispatchers.Main) {
                                buttonClickListener.onButtonClicked()
                            }

                        }

                        is ApiResponse.Failure -> {
                            Snackify.error(
                                binding.linearLayout,
                                "${it.message}",
                                Snackify.LENGTH_LONG
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }

        binding.tvEmail.doAfterTextChanged {
            email = it.toString()
        }

        binding.tvPassword.doAfterTextChanged {
            password = it.toString()
        }

    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}