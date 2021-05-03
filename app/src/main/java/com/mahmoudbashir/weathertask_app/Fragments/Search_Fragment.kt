package com.mahmoudbashir.weathertask_app.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.adapters.OnClickSearchItemInterface
import com.mahmoudbashir.weathertask_app.adapters.searchAdapter
import com.mahmoudbashir.weathertask_app.databinding.FragmentSearchBinding
import com.mahmoudbashir.weathertask_app.pojo.cities_list.Cities
import com.mahmoudbashir.weathertask_app.pojo.cities_list.CitiesRoot
import com.mahmoudbashir.weathertask_app.ui.MainActivity
import com.mahmoudbashir.weathertask_app.viewModel.WeatherViewModel
import java.io.IOException
import java.io.InputStream

class Search_Fragment : Fragment() ,OnClickSearchItemInterface{

    lateinit var searchBinding: FragmentSearchBinding
    lateinit var viewModel: WeatherViewModel
    lateinit var citiesRoot: CitiesRoot
    lateinit var listCities:ArrayList<Cities>
    lateinit var filtered:ArrayList<Cities>
    lateinit var adapterSearch:searchAdapter
    var n = arrayOf("Mahmoud", "Mido")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_, container, false
        )
        doBackward()
        viewModel = (activity as MainActivity).weather_viewModel
        searchBinding.btnSearch.setOnClickListener{
            searchBinding.isSearch = true
            searchBinding.edtSearchInput.requestFocus()
        }
        searchBinding.cancelBtn.setOnClickListener{
            searchBinding.isSearch = false
        }

        getCitiesListFromJson()
        setUpViews()
        searchBinding.recSearchResult.visibility = View.GONE
        searchBinding.edtSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("current_state", "write")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("current_state", "mm")
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    filter(s.toString())
                    searchBinding.recSearchResult.visibility = View.VISIBLE
                }
            }
        })

        return searchBinding.root
    }

    private fun filter(txt: String) {
       filtered = ArrayList()
        for (elem in listCities){
            if (elem.name.toLowerCase().contains(txt.toLowerCase())) {filtered.add(elem)}
        }
        adapterSearch.filterList(filtered)
    }

    private fun setUpViews(){
        adapterSearch = searchAdapter(listCities,this)

        searchBinding.recSearchResult.apply {
            setHasFixedSize(true)
            adapter = adapterSearch
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }
    private fun getCitiesListFromJson(){
        listCities = ArrayList()

        val i : InputStream? = this.activity?.assets?.open("cities.json")
        if (i !=null){
            inputStreamToString(i)
        }
        val myJson = activity?.resources?.let { inputStreamToString(it.openRawResource(R.raw.cities))}
        citiesRoot = Gson().fromJson(myJson, CitiesRoot::class.java)
        listCities.clear()
        listCities.addAll(citiesRoot.cities)
    }


    private fun doBackward(){
        searchBinding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCLickItem(city_name: String) {
       findNavController().navigate(Search_FragmentDirections.actionSearchFragmentToForecastFragment("search","$city_name"))
    }
}