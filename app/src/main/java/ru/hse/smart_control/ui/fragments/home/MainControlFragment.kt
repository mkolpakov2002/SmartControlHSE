package ru.hse.smart_control.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.FragmentMainControlBinding


class MainControlFragment : Fragment(){
    private var _binding: FragmentMainControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainControlBinding.inflate(inflater, container, false)
        initTabLayout()
        return binding.root
    }

    private fun initTabLayout(){
        val context = activity?.applicationContext
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.yandex_tab))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.arduino_tab))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.ros_tab))

        val adapter = SubscribersDataCollectionAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = context?.let { adapter.getPageTitle(position, it) }
            binding.viewpager.setCurrentItem(tab.position, true)
        }.attach()
    }

    private class SubscribersDataCollectionAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        val listTabName = listOf(
            R.string.yandex_tab,
            R.string.arduino_tab,
            R.string.ros_tab
        )

        fun getPageTitle(position: Int, context: Context): String {
            return context.getString(listTabName[position])
        }

        override fun getItemCount(): Int = listTabName.size
        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> MainDeviceListFragment("Yandex")
                1 -> MainDeviceListFragment("Arduino")
                2 -> MainDeviceListFragment("ROS")
                else -> Fragment()
            }
            return fragment
        }
    }



}