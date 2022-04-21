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
import com.binar.challengechapterlima.network.ApiClient
import com.binar.challengechapterlima.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class LoginFragment : Fragment() {
    lateinit var send : SharedPreferences
    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : ViewModelUser
    lateinit var email: String
    lateinit var password: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        send = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
        getDataUserItem()

        val daftar = view.findViewById<TextView>(R.id.daftar2)
        val login = view.findViewById<Button>(R.id.btnlogin)

        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        login.setOnClickListener {
            if (loginemail.text.isNotEmpty() && loginpassword.text.isNotEmpty()){
                email = loginemail.text.toString()
                password = loginpassword.text.toString()

                check(dataUser)

            }
            else{
                val text = "Harap isi semua data"
                val toast = Toast.makeText(requireActivity()?.getApplicationContext(), text, Toast.LENGTH_LONG)
                val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                val toastView: View? = toast.getView()
                toastView?.setBackgroundColor(Color.TRANSPARENT)
                text1.setTextColor(Color.RED);
                text1.setTextSize(15F)
                toast.show()
                toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
            }
        }
        return view
    }
    fun getDataUserItem(){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it


        })
        viewModel.userApi()
    }

    fun check(dataUser : List<GetAllUserItem>) {
        login(email, password)
        for (i in dataUser.indices) {

            if (email == dataUser[i].email && password == dataUser[i].password) {
                val loginstate = "true"

                val sf = send.edit()
                sf.putString("id", dataUser[i].id)
                sf.putString("email", dataUser[i].email)
                sf.putString("username", dataUser[i].username)
                sf.putString("namalengkap", dataUser[i].completeName)
                sf.putString("birth", dataUser[i].dateofbirth)
                sf.putString("address", dataUser[i].address)
                sf.putString("login_state", loginstate)
                sf.apply()
                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_homeFragment)

            }

        }
    }

    fun login(email :String, password : String){
        ApiClient.instance.login(email, password).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful){

                    Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_SHORT).show()
                }else{
                    val text = "Email atau password salah!"
                    val toast = Toast.makeText(
                        requireActivity()?.getApplicationContext(),
                        text,
                        Toast.LENGTH_LONG
                    )
                    val text1 =
                        toast.getView()?.findViewById(android.R.id.message) as TextView
                    val toastView: View? = toast.getView()
                    toastView?.setBackgroundColor(Color.TRANSPARENT)
                    text1.setTextColor(Color.RED);
                    text1.setTextSize(15F)
                    toast.show()
                    toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
                }
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

            }
        })
    }




}