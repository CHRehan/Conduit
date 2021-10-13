package io.realworld.android.conduit.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.realworld.android.conduit.AuthViewModel
import io.realworld.android.conduit.R
import io.realworld.android.conduit.databinding.FragmentLoginSignupBinding
import io.realworld.android.conduit.extensions.handleApiError
import io.realworld.android.conduit.extensions.visible
import io.realworld.android.conduit.network.Resource


class SignupFragment : Fragment() {

    private var binding: FragmentLoginSignupBinding? = null
    private val authViewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginSignupBinding.inflate(inflater, container, false)

        binding?.apply {
            usernameEditText.visibility = View.VISIBLE
            submitButton.text = getString(R.string.signup)
            submitButton.setOnClickListener {
                authViewModel.signup(
                    usernameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        authViewModel.user.observe({ lifecycle }) {
            binding?.loginProgressbar?.visible(it is Resource.Loading)
            when (it) {
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }

        }




        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}