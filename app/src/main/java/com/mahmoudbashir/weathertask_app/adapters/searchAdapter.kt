package com.mahmoudbashir.weathertask_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.pojo.cities_list.Cities

class searchAdapter (private var listCities: ArrayList<Cities>,val onClickItemInterface:OnClickSearchItemInterface): RecyclerView.Adapter<searchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.single_search_item,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_city_name.text = listCities[position].name
        holder.itemView.setOnClickListener {
            onClickItemInterface.onCLickItem(listCities[position].name)
        }
    }

    fun filterList(filteredList : ArrayList<Cities>){
        listCities = filteredList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = listCities.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val txt_city_name:TextView = itemView.findViewById<TextView>(R.id.txt_city_name)
    }
}