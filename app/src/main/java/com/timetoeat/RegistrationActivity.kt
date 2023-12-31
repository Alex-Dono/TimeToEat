package com.timetoeat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.timetoeat.databinding.ActivityRegistrationBinding
import com.timetoeat.utils.Utils

private lateinit var binding: ActivityRegistrationBinding

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
    }

    private fun signIn(email: String, password: String) {
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

    private fun updateUI(user: FirebaseUser?) {
        // Update your UI here based on user authentication status
    }

}




