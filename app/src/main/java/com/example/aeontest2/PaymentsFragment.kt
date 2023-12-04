package com.example.aeontest2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var paymentAdapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payments, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPayments)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadPayments()

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            // Выполните действия для logout
            // Например, переходите на экран входа (LoginFragment)
            (activity as MainActivity).replaceFragment(LoginFragment())
        }

        return view
    }

    private fun loadPayments() {
        try {
            val response = ApiClient.apiService.getPayments("")

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val paymentList = response.body()
                    updateUI(paymentList)
                } else {
                    handleErrorResponse(response)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateUI(paymentList: List<PaymentItem>?) {
        activity?.runOnUiThread {
            paymentAdapter = PaymentAdapter(paymentList ?: emptyList())
            recyclerView.adapter = paymentAdapter
        }
    }

    private fun handleErrorResponse(response: Response<List<PaymentItem>>) {
        // Обработка ошибок, если необходимо
    }
}