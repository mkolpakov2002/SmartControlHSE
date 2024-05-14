package ru.hse.smart_control.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.musfickjamil.snackify.Snackify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.FragmentLoginBinding
import ru.hse.smart_control.domain.ApiResponse
import ru.hse.smart_control.model.prefs.SharedPreferences
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.ui.MainActivity


class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    private val sharedPreferences: SharedPreferences by lazy {
        SharedPreferences(requireContext())
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var email: String
    private lateinit var password: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEmail.doAfterTextChanged {
            email = it.toString()
            Log.d("MY_TAG", "email: $it")
        }

        binding.tvPassword.doAfterTextChanged {
            password = it.toString()
        }

        binding.btnLogin.setOnClickListener {

            hideKeyboard(it)

            with(binding) {
                email = tvEmail.text.toString()
                password = tvPassword.text.toString()
            }

            binding.progressBar.visibility = View.VISIBLE
            viewModel.loginUser(LoginModel(email, password))

//            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
//                viewModel.userToken.collect {
//                    async {
//                        sharedPreferences.save("token", it.accessToken)
//                        sharedPreferences.save("token_expire_in", it.expiresIn.toString())
//                        (activity as MainActivity).saveAuthTime()
//                    }.await()
//                    withContext(Dispatchers.Main) {
////                        (activity as MainActivity).binding.bottomNav.visibility = View.VISIBLE
//                        binding.progressBar.visibility = View.GONE
//                        findNavController().navigate(R.id.action_authControlFragment_to_mainMenuFragment)
//                    }
//                }
//            }
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.loginResult.collectLatest {
                    when (it) {
                        is ApiResponse.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            async {
                                sharedPreferences.save("token", it.value.accessToken)
                                (activity as MainActivity).saveAuthTime()
                            }.await()
                            withContext(Dispatchers.Main) {
                                (activity as MainActivity).binding.bottomnav.visibility =
                                    View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                findNavController().navigate(R.id.action_authControlFragment_to_mainMenuFragment)
                            }
                        }

                        is ApiResponse.Failure -> {
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                            }
                            Log.d("TAG", "message: ${it.message},")
                            Snackify.error(
                                binding.linearLayout,
                                "${it.message}",
                                Snackify.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }


        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}