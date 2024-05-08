package ru.hse.smart_control.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.musfickjamil.snackify.Snackify
import ru.hse.smart_control.databinding.FragmentRegisterBinding
import ru.hse.smart_control.model.user.RegisterModel


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
            viewModel.registerUser(RegisterModel(name, email, password))

            // if success
            buttonClickListener.onButtonClicked()
            Snackify.success(binding.linearLayout, "Success message !", Snackify.LENGTH_LONG).show()

        }

        binding.tvEmail.doAfterTextChanged {
            email = it.toString()
        }

        binding.tvPassword.doAfterTextChanged {
            password = it.toString()
        }

    }
}