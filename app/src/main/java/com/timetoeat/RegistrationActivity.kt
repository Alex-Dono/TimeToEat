package com.timetoeat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.timetoeat.databinding.ActivityRegistrationBinding

private lateinit var binding: ActivityRegistrationBinding
private const val MY_PERMISSIONS_REQUEST_LOCATION = 1

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            register(binding.email.text.toString(), binding.password.text.toString())
        }

        binding.signIn.setOnClickListener {
            signIn(binding.email.text.toString(), binding.password.text.toString())
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
        }
    }

    private fun signIn(email: String, password: String) {
        if (nullField(email, password)) {
            Toast.makeText(baseContext, "Field not filled.",
                Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    val errorMessage = task.exception?.message
                    if (errorMessage != null) {
                        Toast.makeText(baseContext, errorMessage.toString(),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                    updateUI(null)
                }
            }
    }

    private fun register(email: String, password: String) {
        Log.d("RegistrationActivity", "Register function called with email: $email")
        if (nullField(email, password)) {
            Toast.makeText(baseContext, "Field not filled.",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(baseContext, "Invalid email format.",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPassword(password)) {
            Toast.makeText(baseContext, "Password has to be longer than 6 characters.",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    val errorMessage = task.exception?.message
                    if (errorMessage != null) {
                        Toast.makeText(baseContext, errorMessage.toString(),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, "Registration failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                    updateUI(null)
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$".toRegex()
        return emailRegex.matches(email)
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 6) {
            return false
        }
        return true
    }

    private fun nullField(email: String, password: String): Boolean {
        if (email == "" || password == "") {
            return true
        }
        return false
    }

    private fun updateUI(user: FirebaseUser?) {
        // Update your UI here based on user authentication status
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    Log.i("Message:", "Allowed")
                } else {
                    // Permission was denied
                    Log.i("Message:", "Denied")
                }
                return
            }
            // Handle other permission requests here if needed
        }
    }

}




