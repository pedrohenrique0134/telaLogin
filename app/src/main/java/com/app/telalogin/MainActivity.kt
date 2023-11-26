package com.app.telalogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.telalogin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //verificando se usuario ta logado
        //verifiqUser()

        binding.cadastroPrimeiro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.BtnNext.setOnClickListener {
            if (validation()) {
                login(
                    User(
                        email = binding.logiEmail.text.toString(),
                        senha = binding.logiSenha.text.toString()
                    )
                )
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.logiEmail.text.toString().isBlank() ||
            binding.logiSenha.text.toString().isBlank()
        ) {
            isValid = false
            Toast.makeText(this, "texto em branco", Toast.LENGTH_SHORT).show()

        } else {
            if (binding.logiSenha.text.toString().length < 8) {
                isValid = false
                Toast.makeText(
                    this,
                    "a senha deve conter pelo menos 8 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (!binding.logiSenha.text.toString().matches(Regex(".*[A-Z].*")) ||
                    !binding.logiSenha.text.toString().matches(Regex(".*[a-z].*")) ||
                    !binding.logiSenha.text.toString().matches(Regex(".*[^A-Za-z].*"))
                ) {
                    isValid = false
                    Toast.makeText(
                        this,
                        "sua senha e fraca , deve ter letras maiusculas, menusculas e caracteres.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    isValid = true
                }
            }

        }
        return isValid
    }

    private fun login(user: User) {
        auth.signInWithEmailAndPassword(user.email!!, user.senha!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, SucessoActivity::class.java)).apply {
                        finish()
                    }

                } else {
                    Toast.makeText(this, "login deu erro", Toast.LENGTH_SHORT).show()

                }

            }.addOnFailureListener {
                Toast.makeText(this, "${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun verifiqUser() {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        if (user != null) {
            startActivity(Intent(this, SucessoActivity::class.java)).apply {
                finish()
            }
        }
    }
}

