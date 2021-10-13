package io.realworld.android.conduit.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import io.realworld.android.conduit.R
import io.realworld.android.conduit.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    private var binding: FragmentAuthBinding? = null
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        navController =
            binding?.let { Navigation.findNavController(it.root.findViewById(R.id.authNavFragment)) }

        binding?.authTabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        navController?.navigate(R.id.goToLoginFragment)
                    }
                    1 -> {
                        navController?.navigate(R.id.goToSignupFragment)

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}