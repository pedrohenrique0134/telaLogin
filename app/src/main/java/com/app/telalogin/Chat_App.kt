package com.app.telalogin

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.telalogin.databinding.ActivityChatAppBinding
import com.squareup.picasso.Picasso

class Chat_App : AppCompatActivity() {
    private lateinit var binding: ActivityChatAppBinding
    private var nome: String? = null
    private var foto: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        nome = intent.getStringExtra("NOME")
        foto = intent.getStringExtra("FOTO")

        binding.NomePerfilChat.text = nome
        binding.fotoPerfilChat.setOnClickListener {
            inflarFoto()
        }

        Picasso.get().load(foto).into(binding.fotoPerfilChat)


    }
    private fun inflarFoto(){
        val viewIntem = android.view.View.inflate(this, R.layout.dialog_ver_foto, null)
        val uildIntem = AlertDialog.Builder(this)
        uildIntem.setView(viewIntem)

        val dialogIntem = uildIntem.create()
        dialogIntem.show()
        dialogIntem.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val fotoInfalte = viewIntem.findViewById<ImageView>(R.id.bangFotoPerfil)
        Picasso.get().load(foto).into(fotoInfalte)
    }
}