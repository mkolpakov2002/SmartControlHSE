package ru.hse.smart_control.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.FragmentAuthControlBinding
import ru.hse.smart_control.ui.MainActivity

class AuthControlFragment : Fragment(), RegisterFragment.OnButtonClickedListener {

    private var _binding: FragmentAuthControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthControlBinding.inflate(inflater, container, false)
        initTabLayout()
        return binding.root
    }


    private fun initTabLayout(){
        val context = activity?.applicationContext
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.login))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.register))

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
            R.string.login,
            R.string.register
        )

        fun getPageTitle(position: Int, context: Context): String {
            return context.getString(listTabName[position])
        }

        override fun getItemCount(): Int = listTabName.size
        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> LoginFragment()
                1 -> RegisterFragment()
                else -> Fragment()
            }
            return fragment
        }
    }

    override fun onButtonClicked() {
        binding.viewpager.currentItem = 0
    }

}