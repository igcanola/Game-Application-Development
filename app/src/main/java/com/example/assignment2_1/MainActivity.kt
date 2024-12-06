package com.example.assignment2_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment2_1.databinding.ActivityMainBinding

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    //FB
    private val callbackManager = CallbackManager.Factory.create()

    //Google
    private lateinit var googleSignInClient: GoogleSignInClient

    //Google
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        val data = result.data
        if(result.resultCode == RESULT_OK && data != null){
            handleSignInResult(data)
        }else{
            binding.tvWelcome.text = "Google Sign-In canceled or failed"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enabling edge-to-edge display for a more immersive UI experience
        enableEdgeToEdge()

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the root view to the inflated binding layout
        setContentView(binding.root)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is logged in
        checkUserStatus()

        //username & password
        // Set click listener for the signup button to navigate to the SignUp activity
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            // Start the SignUp activity
            startActivity(intent)
        }

        // Set click listener for the login button to navigate to the Login activity
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            // Start the Login activity
            startActivity(intent)
        }

        //FB
        binding.btnSignInFacebook.setOnClickListener {
            binding.fbLoginButton.performClick() // Programmatically trigger Facebook login
        }

        // Configure the hidden Facebook Login button
        binding.fbLoginButton.setPermissions("email", "public_profile")
        binding.fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("FacebookLogin", "Facebook token: ${result.accessToken.token}")
                handleFacebookAccessToken(result.accessToken.token)
            }

            override fun onCancel() {
                binding.tvWelcome.text = "Sign-in canceled."
                Toast.makeText(this@MainActivity, "Facebook sign-in canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                binding.tvWelcome.text = "Sign-in failed."
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        //Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnSignInGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

        //Log out
        binding.btnLogout.setOnClickListener {
            // Log out the user from Firebase
            firebaseAuth.signOut()
            checkUserStatus()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //FB
    private fun handleFacebookAccessToken(token: String) {
        val credential = FacebookAuthProvider.getCredential(token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    binding.tvWelcome.text = "Welcome, ${user?.displayName ?: "User"}!"
                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                } else {
                    binding.tvWelcome.text = "Sign-in failed."
                    Toast.makeText(this, "Sign-in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("FacebookLogin", "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    //check user status
    override fun onStart() {
        super.onStart()
        // Re-check user status when the app is brought to the foreground
        checkUserStatus()
    }

    //Function to check if the user is logged in
    private fun checkUserStatus() {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser == null) {
            binding.tvWelcome.text = "Hi, visitors"
        } else {
            // Optionally, display user info if needed
            val userEmail = currentUser.email
            binding.tvWelcome.text = "Welcome, $userEmail!"
        }
    }

    //Google
    private fun handleSignInResult(data: Intent){

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

            val idToken = account.idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Welcome, ${user?.displayName}!", Toast.LENGTH_SHORT).show()
                    // Navigate to GameMainPage activity
                    val intent = Intent(this, GameMainPage::class.java)
                    startActivity(intent)
                    finish() // Optional: close MainActivity
                } else {
                    binding.tvWelcome.text = "Sign-In Failed: ${task.exception?.message}"
                }
            }
        } catch (e: ApiException) {
            Log.e("SignInError", "Google sign-in failed", e)
            binding.tvWelcome.text = "Sign-In Failed: ${e.message}"
        }

    }
}