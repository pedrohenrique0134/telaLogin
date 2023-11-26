package com.app.telalogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.telalogin.adapters.AdapterPerfil
import com.app.telalogin.databinding.ActivitySucessoBinding
import com.google.firebase.firestore.FirebaseFirestore

class SucessoActivity : AppCompatActivity(), AdapterPerfil.click {

    private lateinit var binding: ActivitySucessoBinding
    // instaciando firestore
    private var database = FirebaseFirestore.getInstance()

    private val list: ArrayList<User> = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySucessoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDados()
    }

    private fun getDados(){
        val doc = database.collection("user")

        doc.addSnapshotListener { value, error ->
            val usuario = value?.toObjects(User::class.java)

            list.addAll(usuario!!)
            binding.recyUsuario.layoutManager = LinearLayoutManager(this)
            binding.recyUsuario.adapter = AdapterPerfil(this, list, this)
        }
    }

    override fun clickUser(user: User) {

        val intent = Intent(this@SucessoActivity, Chat_App::class.java)
        intent.putExtra("NOME", user.nome)
        intent.putExtra("FOTO", user.foto)
        startActivity(intent)

    }
}
