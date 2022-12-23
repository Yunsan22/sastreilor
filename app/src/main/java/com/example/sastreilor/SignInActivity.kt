package com.example.sastreilor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.sastreilor.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var  googleSigninClient: GoogleSignInClient
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hide action bar
        supportActionBar?.hide()
        builder = AlertDialog.Builder(this)

        //this init Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //configure google sign in
//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSigninClient = GoogleSignIn.getClient(this,gso)
        val signInIntent = googleSigninClient.signInIntent

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

        val signInIntent = googleSigninClient.signInIntent
        launcher.launch(signInIntent)


    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task =GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    }
    private fun handleResults(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else {
            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
        }

    }
    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent = Intent(this,MainActivity::class.java)
                //trying to pass info
                intent.putExtra("email", account.email)
                intent.putExtra("name",account.displayName)
                startActivity((intent))

            }else {
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun facebookSignin(){

    }

    fun handleSignin(){
        val emailEditText = binding.EmailEditText.text.toString()
        val pwEditText = binding.passwordEditText.text.toString()


        if (emailEditText.isNotEmpty() && pwEditText.isNotEmpty()){

            firebaseAuth.signInWithEmailAndPassword(emailEditText,pwEditText).addOnCompleteListener {
                if (it.isSuccessful){
                    val emailIsVerified = firebaseAuth.currentUser?.isEmailVerified

                    if (emailIsVerified == true){
                        builder.setTitle("Welcome")
                            .setMessage("Hello ${emailEditText}")
                            .setCancelable(true)
                            .setNegativeButton("OK"){dialogInterface, it ->
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                            }
                        builder.show()

//                        Toast.makeText(this, "Welcome" + emailEditText, Toast.LENGTH_SHORT).show()
                    } else {
                        builder.setTitle("Error")
                            .setMessage("Please verify your email")
                            .setCancelable(true)
                            .setNegativeButton("OK"){dialogInterface, it ->
                                dialogInterface.cancel()
                            }
                        builder.show()
                    }

                }else {

                    print(it.exception.toString())
                    builder.setTitle("Alert")
                        .setMessage(it.exception.toString())
                        .setCancelable(true)
                        .setNegativeButton("OK"){dialogInterface, it ->
                            dialogInterface.cancel()
                        }
                    builder.show()
                }
            }
        } else {
            builder.setTitle("Alert")
                .setMessage("Empty fields are not allow!!")
                .setCancelable(true)
                .setNegativeButton("OK"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
            builder.show()

            Toast.makeText(this, "Empty Fields are not allow", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val emailIsVerified = firebaseAuth.currentUser?.isEmailVerified
        if(emailIsVerified == true ) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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