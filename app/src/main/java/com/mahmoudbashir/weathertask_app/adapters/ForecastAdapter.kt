package com.mahmoudbashir.weathertask_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.pojo.InfoList
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastAdapter(private val mlist:ArrayList<InfoList>,val context:Context):RecyclerView.Adapter<ForecastAdapter.ViewHolder>(){


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day_name:TextView=itemView.findViewById(R.id.txt_day_name)
        val date_txt:TextView=itemView.findViewById(R.id.txt_date)
        val wind_speed:TextView=itemView.findViewById(R.id.txt_wind_speed)
        val weather_degree:TextView=itemView.findViewById(R.id.txt_weather_degree)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_forecast_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dt = mlist[position].dt_txt.split(" ").toTypedArray()
        val stdate = dt[0]
        holder.day_name.text =getDayName(stdate)
        holder.date_txt.text ="${dt[0].split("-").toTypedArray()[1]}-${dt[0].split("-").toTypedArray()[2]}"

        holder.weather_degree.text = mlist[position].main.temp.toInt().toString()
        holder.wind_speed.text = "${mlist[position].wind.speed}km/h"
    }

    override fun getItemCount(): Int = mlist.size

    private fun getDayName(stdate:String):String
    {
        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val d:Date= inFormat.parse(stdate)
        val outFormat = SimpleDateFormat("EEEE")
        val output =outFormat.format(d)

        val currentDate:String = inFormat.format(Date())
        return if (stdate == currentDate){
            "Today"
        }else output
    }
}