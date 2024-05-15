package com.tatry.yandextest.presentation.screens.control

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.FragmentControlBinding
import ru.hse.smart_control.ui.fragments.control.UiFragment
import ru.hse.smart_control.ui.fragments.control.UiSettingsFragment

class ControlFragment : Fragment() {

    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        initTabLayout()
        return binding.root
    }

    private fun initTabLayout(){
        val context = activity?.applicationContext
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.ui_control))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.settings))

        val adapter = SubscribersDataCollectionAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = context?.let { adapter.getPageTitle(position, it) }
            binding.viewpager.setCurrentItem(tab.position, true)
            binding.viewpager.isUserInputEnabled = false
        }.attach()
    }

    private class SubscribersDataCollectionAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        val listTabName = listOf(
            R.string.ui_control,
            R.string.settings,
        )

        fun getPageTitle(position: Int, context: Context): String {
            return context.getString(listTabName[position])
        }

        override fun getItemCount(): Int = listTabName.size
        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> UiFragment()
                1 -> UiSettingsFragment()
                else -> Fragment()
            }
            return fragment
        }
    }

}