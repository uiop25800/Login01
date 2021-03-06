package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.api.Retro
import com.example.myapplication.api.UserApi
import com.example.myapplication.model.UserPost
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.inputNameAccount
import kotlinx.android.synthetic.main.activity_register.inputPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //getMyData()
        //Set event click back to Login Layout
        btnBack.setOnClickListener{
            val i:Intent= Intent(this,Login::class.java)
            startActivity(i)
            finish()
        }

        // Check empty values
        btnRegister.setOnClickListener(){
            val nameAccount = inputNameAccount.text.toString().trim()
            val Pass = inputPassword.text.toString().trim()
            val confirmPass = inputConfirmPassword.text.toString().trim()
            val fullNameFrm=inputFullName.text.toString().trim()
            val i:Intent= Intent(this,Login::class.java)

            if (nameAccount.isEmpty()){
                inputNameAccount.error = "Account Name is Required"
                inputNameAccount.requestFocus()
                return@setOnClickListener
            }
            if (Pass.isEmpty()){
                inputPassword.error = "Password is Required"
                inputPassword.requestFocus()
                return@setOnClickListener
            }
            if (confirmPass.isEmpty()){
                inputConfirmPassword.error = "Confirm Password is Required"
                inputConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            if(Pass.equals(confirmPass)==false){
                inputConfirmPassword.error = "M???t kh???u nh???p l???i kh??ng tr??ng kh???p"
                inputConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            if(fullNameFrm.isEmpty()){
                inputFullName.error="T??n b??? b??? tr???ng"
                inputFullName.requestFocus()
                return@setOnClickListener
            }
            if (nameAccount.isEmpty()){
                inputNameAccount.error = "Account Name is Required"
                inputNameAccount.requestFocus()
                return@setOnClickListener
            }
            //Ph???n ????ng k?? t??i kho???n
            val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
            val userPost= UserPost(""+nameAccount,""+Pass,
                ""+confirmPass,""+fullNameFrm)

            val call=retro.Resgister(userPost)
            call.enqueue(object: Callback<UserPost> {
                override fun onResponse(call: Call<UserPost>?, response: Response<UserPost>?) {
                    if (response != null) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext,"????ng K?? Th??nh C??ng"
                                , Toast.LENGTH_LONG).show()
                            startActivity(i)
                        }else{
                            Toast.makeText(applicationContext,"????ng K?? Th???t B???i"
                                , Toast.LENGTH_LONG).show()
                            Log.e("L???i 1",":"+response.message())
                        }
                    }
                }
                override fun onFailure(call: Call<UserPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(applicationContext,"????ng K?? Th???t B???i"+t.message, Toast.LENGTH_LONG).show()
                        Log.e("L???i 1",":"+t.message)
                    }
                }

            })
        }
    }
}