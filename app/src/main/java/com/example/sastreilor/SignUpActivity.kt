package com.example.sastreilor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.sastreilor.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SignUpActivity/"
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hide action bar
        supportActionBar?.hide()



        //this init Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        // the below textview sends the user to sign in activity
        binding.signinTexview.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        //this btn create an user account and adds it to firebase DataBase
        binding.registerBtn.setOnClickListener {
            //check for credentials
            val emailEditText = binding.EmailEditText.text.toString()
            val pwEditText = binding.passwordEditText.text.toString()
            val confirmPwEditText = binding.repasswordEditText.text.toString()

            if (emailEditText.isNotEmpty() && pwEditText.isNotEmpty() && confirmPwEditText.isNotEmpty()) {
                if (pwEditText == confirmPwEditText){

                    firebaseAuth.createUserWithEmailAndPassword(emailEditText,pwEditText).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this,SignInActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Account registered succesfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                }else {
                    Log.e(TAG,"Failed to create account")
                    Toast.makeText(this,"Empty fields are not allow!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun hideSoftKeyboard(view: View) {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}