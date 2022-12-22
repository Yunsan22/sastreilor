package com.example.sastreilor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.sastreilor.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SignUpActivity/"
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hide action bar
        supportActionBar?.hide()
        builder = AlertDialog.Builder(this)

        binding.EmailEditText.addTextChangedListener(object : TextWatcher {
            val email = binding.EmailEditText.text.toString()
            val emailedit = binding.EmailEditText
            override fun afterTextChanged(s: Editable) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailedit.setError("email is good")

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailedit.error = "email is good"

                }else {
                    emailedit.error = "invalid Email"
                }
            }
        })
        //this init Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        // the below textview sends the user to sign in activity
        binding.signinTexview.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        binding.verifiedEmailBtn.setOnClickListener {
//            val emailEditText = binding.EmailEditText.text.toString()
//            validateEmailAddres(emailEditText)
        }

        //this btn create an user account and adds it to firebase DataBase
        binding.registerBtn.setOnClickListener {
            //check for credentials
            val emailEditText = binding.EmailEditText.text.toString()
            val pwEditText = binding.passwordEditText.text.toString()
            val confirmPwEditText = binding.repasswordEditText.text.toString()
            val nameEditText = binding.nameEditText.text.toString()
            val lastnameEditText = binding.lasnameEditText.text.toString()




//            if (validateEmailAddres(emailEditText)){
//
//            }

            if (emailEditText.isNotEmpty() && pwEditText.isNotEmpty()
                && confirmPwEditText.isNotEmpty() && nameEditText.isNotEmpty()
                && lastnameEditText.isNotEmpty()) {


                if (pwEditText == confirmPwEditText){

                    firebaseAuth.createUserWithEmailAndPassword(emailEditText,pwEditText).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this,SignInActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Account registered succesfully", Toast.LENGTH_SHORT).show()
                            builder.setTitle("Welcome")
                                .setMessage("Account registered succesfully")
                                .setCancelable(true)
                                .setNegativeButton("OK"){dialogInterface, it ->
                                    dialogInterface.cancel()
                                }
                            builder.show()
                        } else {
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                            builder.setTitle("Alert")
                                .setMessage(it.exception.toString())
                                .setCancelable(true)
                                .setNegativeButton("OK"){dialogInterface, it ->
                                    dialogInterface.cancel()
                                }
                            builder.show()
                        }
                    }

                }else {
                    Log.e(TAG,"Failed to create account paswword don't match")
                    builder.setTitle("Alert")
                        .setMessage("passwors do not match!!")
                        .setCancelable(true)
                        .setNegativeButton("OK"){dialogInterface, it ->
                            dialogInterface.cancel()
                        }
                    builder.show()

                    Toast.makeText(this,"Passwords must be the same!!",Toast.LENGTH_SHORT).show()
                }

            } else {
                Log.e(TAG,"Failed to create account")

                Toast.makeText(this,"Empty fields are not allow!!",Toast.LENGTH_SHORT).show()
                builder.setTitle("Alert")
                    .setMessage("Empty fields are not allow!!")
                    .setCancelable(true)
                    .setNegativeButton("OK"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                builder.show()

            }



        }
    }
    fun validateEmailAddres(email: String): Boolean {
//     val emailinput = email.text.toString()

        if (!email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            builder.setTitle("Welcome")
                .setMessage("Email is validated succesfully")
                .setCancelable(true)
                .setNegativeButton("OK"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
            builder.show()
            Toast.makeText(this,"Email is validated succesfully",Toast.LENGTH_SHORT).show()
            return true
        }else {
            builder.setTitle("Welcome")
                .setMessage("Email is is invalid")
                .setCancelable(true)
                .setNegativeButton("OK"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
            builder.show()
            Toast.makeText(this,"email is invalid",Toast.LENGTH_SHORT).show()
            return false
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