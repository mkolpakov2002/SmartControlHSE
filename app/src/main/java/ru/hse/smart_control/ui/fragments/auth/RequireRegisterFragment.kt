package ru.hse.smart_control.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.FragmentRequireRegisterBinding
import ru.hse.smart_control.ui.MainActivity

class RequireRegisterFragment : Fragment() {
    private var _binding: FragmentRequireRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequireRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.authControlFragment)
            (activity as MainActivity).binding.bottomnav.visibility = View.GONE
        }
    }
}