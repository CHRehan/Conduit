package io.realworld.android.conduit.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.realworld.android.conduit.AuthViewModel
import kotlinx.coroutines.runBlocking


class LogoutFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            authViewModel.logout()
        }
    }
}