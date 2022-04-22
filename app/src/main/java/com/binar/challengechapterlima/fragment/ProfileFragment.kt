package com.binar.challengechapterlima.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.binar.challengechapterlima.R
import com.binar.challengechapterlima.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.logout_dialog.view.*


class ProfileFragment : Fragment() {
    lateinit var home: SharedPreferences
    lateinit var viewModel: ViewModelUser
    lateinit var username: String

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        home = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)

        view.update1.setText(home.getString("username", "").toString())
        view.update2.setText(home.getString("namalengkap", "").toString())
        view.update3.setText(home.getString("birth", "").toString())
        view.update4.setText(home.getString("address", "").toString())

        view.btnupdate.setOnClickListener {

            val id = home.getString("id", "").toString()
            username = view.update1.text.toString()
            val cn = view.update2.text.toString()
            val dateofbirth = view.update3.text.toString()
            val address = view.update4.text.toString()
            val sf = home.edit()
            sf.putString("username", username)
            sf.apply()
            updateDataUser(id!!.toInt(),username, cn, dateofbirth, address)

            view.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)

        }
        view.btnlogout.setOnClickListener {
            val custom = LayoutInflater.from(requireContext()).inflate(R.layout.logout_dialog, null)
            val a = AlertDialog.Builder(requireContext())
                .setView(custom)
                .create()

            custom.btnlogouttidak.setOnClickListener {
                a.dismiss()
            }

            custom.btnlogoutya.setOnClickListener {
                val logout = home.edit()

                for (key in home.all.keys) {
                    if (key.startsWith("login_state")) {
                        logout.remove(key)
                    }
                }
                logout.commit()

                a.dismiss()
                view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            a.show()

        }
        return view
    }


    fun updateDataUser(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(requireActivity(), Observer {
            if (it  != null){
                Toast.makeText(requireContext(), "Gagal Update Data", Toast.LENGTH_LONG ).show()
            }else{
                Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_LONG ).show()
                val sf = home.edit()

                sf.putString("birth", dateofbirth)
                sf.putString("address", address)
                sf.putString("namalengkap", completeName)
                sf.apply()
            }

        })
        viewModel.updateDataUser(id, username, completeName, dateofbirth, address)

}
}