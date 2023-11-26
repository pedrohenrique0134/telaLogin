package com.app.telalogin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.telalogin.User
import com.app.telalogin.databinding.ItemUsuarioBinding
import com.squareup.picasso.Picasso

class AdapterPerfil(private val context: Context, private var list: List<User>, val tclick: click) :
    RecyclerView.Adapter<AdapterPerfil.ViewHolder>() {

    interface click {

        fun clickUser(user: User)


    }


    inner class ViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(user: User) {
            binding.perfNome.text = user.nome
            binding.perfEmail.text = user.email
            binding.lineerClick.setOnClickListener {
                tclick.clickUser(user)
            }

            Picasso.get().load(user.foto).into(binding.foto)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPerfil.ViewHolder {
        val v = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterPerfil.ViewHolder, position: Int) {
        val userCurr = list[position]
        holder.bind(userCurr)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}