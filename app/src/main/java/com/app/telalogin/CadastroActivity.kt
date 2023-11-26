package com.app.telalogin

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.telalogin.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    val auth = FirebaseAuth.getInstance()

    val database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addImageCadastro.setOnClickListener {
           val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
           startActivityForResult(intent, 1)
        }

        binding.Btn.setOnClickListener {
            if (validation()) {
                register(User(
                    nome = binding.cadastroName.text.toString(),
                    senha = binding.cadastroSenha.text.toString(),
                    email = binding.cadastroEmail.text.toString(),
                    id = FirebaseAuth.getInstance().currentUser?.uid
                ))
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null ){

            val imageselecionada = data.data
            binding.imageCadastro.setImageURI(imageselecionada)
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.cadastroName.text.toString().isBlank() ||
            binding.cadastroEmail.text.toString().isBlank() ||
            binding.cadastroSenha.text.toString().isBlank()
        ) {
            isValid = false
            Toast.makeText(this, "texto em branco", Toast.LENGTH_SHORT).show()

        } else {
            isValid = true
        }

        return isValid

    }

    private fun register(user: User) {
        auth.createUserWithEmailAndPassword(user.email!!, user.senha!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.id = FirebaseAuth.getInstance().currentUser?.uid
                    updateUser(user)

                } else {
                    Toast.makeText(this, "autenticaçao deu erro", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }


    }

    private fun updateUser(user: User) {
        val dt = database.collection("user").document(auth.currentUser?.uid!!)
        dt.set(user)
            .addOnSuccessListener {
                startActivity(Intent(this, SucessoActivity::class.java)).apply {
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "falha ao cadastrar o usuario no banco de dados",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}