package ru.hse.smart_control.ui.fragments.connection_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.hse.smart_control.model.entities.universal.YandexSmartHomeAPITest
import ru.hse.smart_control.databinding.FragmentConnectionTypeBinding

class ConnectionTypeFragment : Fragment() {

    private val viewModel: ConnectionTypeViewModel by lazy {
        ViewModelProvider(this)[ConnectionTypeViewModel::class.java]
    }
    private lateinit var binding: FragmentConnectionTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectionTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabConnect.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val apiTest = YandexSmartHomeAPITest()
                apiTest.testAll()
            }
        }
    }


}