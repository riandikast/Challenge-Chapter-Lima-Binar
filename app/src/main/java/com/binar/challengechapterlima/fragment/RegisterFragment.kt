package com.binar.challengechapterlima.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.binar.challengechapterlima.R
import com.binar.challengechapterlima.model.GetAllUserItem
import com.binar.challengechapterlima.model.ResponseLogin
import com.binar.challengechapterlima.model.ResponseRegister
import com.binar.challengechapterlima.network.ApiClient
import com.binar.challengechapterlima.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {
    lateinit var email : String
    lateinit var get : SharedPreferences
    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : ViewModelUser
    lateinit var password: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val btnregis = view.findViewById<Button>(R.id.btnregis)
        get = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)


        btnregis.setOnClickListener {
            val username = regisusername.text.toString()
            email = regisemail.text.toString()
            password = regispassword.text.toString()

            val confirmpass = confirmpassword.text.toString()
            if (password == confirmpass){
                val edit = get.edit()
                edit.putString("username", username)
                edit.apply()
                regisUser(username, email, password)
                view?.findNavController()
                    ?.navigate(R.id.action_registerFragment_to_loginFragment)

            }else{
                val text = "Konfirmasi Password Tidak Sesuai"
                val toast = Toast.makeText(requireActivity()?.getApplicationContext(), text, Toast.LENGTH_LONG)
                val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                val toastView: View? = toast.getView()
                toastView?.setBackgroundColor(Color.TRANSPARENT)
                text1.setTextColor(Color.RED);
                text1.setTextSize(15F)
                toast.show()
                toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)
            }

        }
        return view

    }
//    fun getDataUserItem(){
//        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
//        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
//            dataUser = it
//            check(dataUser)
//
//        })
//        viewModel.userApi()
//    }

    fun regisUser(username : String, email : String, password : String) {
        ApiClient.instance.register(username, email, password)
            .enqueue(object :Callback<ResponseRegister> {
                override fun onResponse(
                    call: Call<ResponseRegister>,
                    response: Response<ResponseRegister>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(requireContext(), "register sukses", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {

                }
            })
    }

//    fun check(dataUser : List<GetAllUserItem>) {
//
//        val username = regisusername.text.toString()
//        email = regisemail.text.toString()
//        password = regispassword.text.toString()
//        getDataUserItem()
//        val confirmpass = confirmpassword.text.toString()
//        login(email, password)
//        for (i in dataUser.indices) {
//
//            if (email != dataUser[i].email ) {
//                if (regisusername.text.isNotEmpty() && regisemail.text.isNotEmpty()
//                    && regispassword.text.isNotEmpty()
//                    && confirmpassword.text.isNotEmpty() ){
//
//
//
//                }else {
//                    val text = "Harap isi semua data"
//                    val toast = Toast.makeText(
//                        requireActivity()?.getApplicationContext(),
//                        text,
//                        Toast.LENGTH_LONG
//                    )
//                    val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
//                    val toastView: View? = toast.getView()
//                    toastView?.setBackgroundColor(Color.TRANSPARENT)
//                    text1.setTextColor(Color.RED);
//                    text1.setTextSize(15F)
//                    toast.show()
//                    toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)
//                }
//
//            }else{
//                val text = "Email Sudah Terdaftar"
//                val toast = Toast.makeText(
//                    requireActivity()?.getApplicationContext(),
//                    text,
//                    Toast.LENGTH_LONG
//                )
//                val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
//                val toastView: View? = toast.getView()
//                toastView?.setBackgroundColor(Color.TRANSPARENT)
//                text1.setTextColor(Color.RED);
//                text1.setTextSize(15F)
//                toast.show()
//                toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)
//
//            }
//
//        }
//    }

//    fun login(email :String, password : String){
//        ApiClient.instance.login(email, password).enqueue(object : Callback<ResponseLogin>{
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                if (response.isSuccessful){
//
//                    Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_SHORT).show()
//                }else{
//                    val text = "Email atau password salah!"
//                    val toast = Toast.makeText(
//                        requireActivity()?.getApplicationContext(),
//                        text,
//                        Toast.LENGTH_LONG
//                    )
//                    val text1 =
//                        toast.getView()?.findViewById(android.R.id.message) as TextView
//                    val toastView: View? = toast.getView()
//                    toastView?.setBackgroundColor(Color.TRANSPARENT)
//                    text1.setTextColor(Color.RED);
//                    text1.setTextSize(15F)
//                    toast.show()
//                    toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
//                }
//            }
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//
//            }
//        })
//    }

}