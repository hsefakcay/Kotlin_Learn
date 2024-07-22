package com.hsefakcay.yemekkitabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hsefakcay.yemekkitabi.databinding.RecyclerRowBinding
import com.hsefakcay.yemekkitabi.model.Tarif
import com.hsefakcay.yemekkitabi.view.ListeFragmentDirections

class TarifAdapter(val tarifListesi: List<Tarif>) : RecyclerView.Adapter<TarifAdapter.TarifHolder>(){

    class TarifHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TarifHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return tarifListesi.size
    }

    override fun onBindViewHolder(holder: TarifHolder, position: Int) {
        holder.binding.recylerViewTextView.text = tarifListesi[position].isim
        holder.itemView.setOnClickListener{
            val action = ListeFragmentDirections.actionListeFragmentToTarifFragment(bilgi = "eski", id = tarifListesi[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

}