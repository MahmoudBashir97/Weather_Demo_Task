package com.mahmoudbashir.weathertask_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel

class cities_forecast_adapter(private val mlist:ArrayList<WeatherModel>,private val citiesOnClickInterface: CitiesListOnClickInterface): RecyclerView.Adapter<cities_forecast_adapter.ViewHolder>() {

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var txt_city_name :TextView=itemView.findViewById(R.id.txt_city_name)
        var txt_weather_degree_max :TextView=itemView.findViewById(R.id.txt_weather_degree_max)
        var txt_weather_degree_min :TextView=itemView.findViewById(R.id.txt_weather_degree_min)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.single_item_city,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_city_name.text = mlist[position].city.name
        holder.txt_weather_degree_max.text = mlist[position].list[1].main.temp_max.toInt().toString()
        holder.txt_weather_degree_min.text = mlist[position].list[1].main.temp_min.toInt().toString()
        holder.itemView.setOnClickListener {
            citiesOnClickInterface.CitiesOnClick(city_name = mlist[position].city.name.toLowerCase(),position = position)
        }
    }

    override fun getItemCount(): Int = mlist.size
}