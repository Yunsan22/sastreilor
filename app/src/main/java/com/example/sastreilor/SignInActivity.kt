package com.example.sastreilor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sastreilor.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hide action bar
        supportActionBar?.hide()

        //this init Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        binding.createacctTexview.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        //this btn sign the user in
        binding.loginBtn.setOnClickListener { 
           handleSignin()
        }
        binding.Googlebtn.setOnClickListener {
            googleSignIn()
        }
        binding.Facebookbtn.setOnClickListener {
            facebookSignin()
        }

    }
    fun googleSignIn(){

    }
    fun facebookSignin(){

    }

    fun handleSignin(){
        val emailEditText = binding.EmailEditText.text.toString()
        val pwEditText = binding.passwordEditText.text.toString()


        if (emailEditText.isNotEmpty() && pwEditText.isNotEmpty()){

            firebaseAuth.signInWithEmailAndPassword(emailEditText,pwEditText).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Welcome" + emailEditText, Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Empty Fields are not allow", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null ) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}