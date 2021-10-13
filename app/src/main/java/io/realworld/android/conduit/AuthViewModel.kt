package io.realworld.android.conduit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realworld.android.conduit.models.entities.User
import io.realworld.android.conduit.data.UserPreferences
import io.realworld.android.conduit.data.UserRepo
import io.realworld.android.conduit.models.responses.UserResponse
import io.realworld.android.conduit.network.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val pref: UserPreferences
) : ViewModel() {

    private val _user = MutableLiveData<Resource<UserResponse>?>()
    val user: LiveData<Resource<UserResponse>?> = _user

    fun getCurrentUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = userRepo.getCurrentUser()
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = userRepo.login(email, password)


    }

    fun signup(username: String, email: String, password: String) =
        viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = userRepo.signup(username, email, password)

        }

    fun update(
        image: String?,
        bio: String?,
        username: String?,
        email: String?,
        password: String?
    ) {
        viewModelScope.launch {

            _user.value = Resource.Loading
            _user.value = userRepo.update(
                image = image,
                bio = bio,
                username = username,
                email = email,
                password = password
            )
        }
    }

    suspend fun logout() {
        pref.clear()

        _user.value= Resource.Logout
    }


}