package io.realworld.android.conduit.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.realworld.android.conduit.AuthViewModel
import io.realworld.android.conduit.databinding.FragmentSettingsBinding
import io.realworld.android.conduit.extensions.visible
import io.realworld.android.conduit.network.Resource


class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding? = null
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.user.observe({ lifecycle }) { it ->
            binding?.settingProgressbar?.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding?.apply {
                        it.value.user.apply {
                            imageEdittext.setText(image ?: "")
                            usernameEdittext.setText(username)
                            bioEdittext.setText(bio ?: "")
                            emailEdittext.setText(email)
                        }
                    }
                }

            }
        }
        binding?.apply {

            updateButton.setOnClickListener {
                authViewModel.update(
                    image = imageEdittext.text.toString(),
                    username = usernameEdittext.text.toString().takeIf { it.isNotBlank() },
                    bio = bioEdittext.text.toString(),
                    email = emailEdittext.text.toString().takeIf { it.isNotBlank() },
                    password = passwordEdittext.text.toString().takeIf { it.isNotBlank() }
                )
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}