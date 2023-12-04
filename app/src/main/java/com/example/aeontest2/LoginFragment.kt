package com.example.aeontest2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginEditText = view.findViewById(R.id.editTextLogin)
        passwordEditText = view.findViewById(R.id.editTextPassword)

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                loginUser(login, password)
            } else {
                showToast("Please enter both login and password.")
            }
        }

        return view
    }

    private fun loginUser(login: String, password: String) {
        val call = ApiClient.apiService.loginUser(LoginRequest(login, password))

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = call.also {  }

                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    // Сохраните токен в SharedPreferences
                    val sharedPreferencesManager = SharedPreferencesManager(requireContext())
                    sharedPreferencesManager.saveToken(token)


                    // Аутентификация прошла успешно, переходите на следующий экран
                    // Например, на экран с платежами (PaymentsFragment)
                    (activity as MainActivity).replaceFragment(PaymentsFragment())
                } else {
                    handleErrorResponse(response)
                }
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
            }
        }
    }

    private fun saveToken(token: String) {
        // Реализуйте логику сохранения токена, например, в SharedPreferences
    }
    private fun handleErrorResponse(response: Response<TokenResponse>) {
        val errorBody = response.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            showToast("Error: $errorBody")
        } else {
            showToast("Error: ${response.message()}")
        }
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
