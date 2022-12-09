package com.auf.breweryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auf.breweryapplication.Adaprters.DataAdapters
import com.auf.breweryapplication.Models.BrewingInfoData
import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.Services.Helper.Retrofit
import com.auf.breweryapplication.Services.Repository.BreweriesAPI
import com.auf.breweryapplication.databinding.ActivityListBreweriesBinding
import com.auf.breweryapplication.realm.config.RealmConfig
import com.auf.breweryapplication.realm.operations.brewDBOperations
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class ListBreweries : AppCompatActivity(), DataAdapters.DataAdaptersInterface, View.OnClickListener{
    private lateinit var binding: ActivityListBreweriesBinding
    private lateinit var adapter: DataAdapters
    private lateinit var brewingData: ArrayList<BrewingInformation>
    private var isLoading:Boolean = false
    private var pageCount = 1
    private var newData:String = ""
    private lateinit var config: RealmConfiguration
    private lateinit var operation: brewDBOperations
    private lateinit var coroutine: CoroutineContext


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBreweriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        brewingData = arrayListOf()
        adapter = DataAdapters(brewingData, this, this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvBrew.layoutManager = layoutManager
        binding.rvBrew.adapter = adapter


        binding.loadingimg.visibility = View.INVISIBLE

        config = RealmConfig.getConfiguration()
        operation = brewDBOperations(config)
        coroutine = Job() + Dispatchers.IO

        binding.brewpubrb.setOnClickListener(this)
        binding.planningrb.setOnClickListener(this)
        binding.largerb.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
        binding.microrb.setOnClickListener(this)

        BreweriesData(newData)

        binding.rvBrew.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(!isLoading){
                        isLoading = true
                        loadingData()
                        startLoading()
                        BreweriesData(newData)
                    }
                }
            }
        })
    }

    private fun BreweriesData(type: String) {
        super.onResume()

        val BreweriesAPI = Retrofit.getInstance().create(BreweriesAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val result = BreweriesAPI.getListBreweries(5,type,pageCount)
            val breweries = result.body()

            if(breweries != null){
                brewingData.addAll(breweries)
                withContext(Dispatchers.Main){
                    adapter.UpdateData(brewingData)
                    binding.loadingimg.visibility = View.INVISIBLE
                    binding.rvBrew.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun resetDatas(){
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                brewingData.clear()
            }
        }
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btnReset)->{
                startLoading()
                pageCount = 1
                resetDatas()
                BreweriesData(newData)
            }
            (R.id.brewpubrb)->{
                startLoading()
                resetDatas()
                newData="brewpub"
                BreweriesData(newData)
            }
            (R.id.largerb)->{
                startLoading()
                resetDatas()
                newData="large"
                BreweriesData(newData)
            }
            (R.id.microrb)->{
                startLoading()
                resetDatas()
                newData="micro"
                BreweriesData(newData)
            }
            (R.id.planningrb)->{
                startLoading()
                resetDatas()
                newData="planning"
                BreweriesData(newData)
            }
        }
    }


    private fun loadingData(){
        isLoading = false
        pageCount++
        BreweriesData(newData)
        Data()
    }


    private fun Data(){
        object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                BreweriesData(newData)
            }

        }.start()
    }


    private fun startLoading(){
        binding.loadingimg.visibility = View.VISIBLE
        binding.rvBrew.visibility = View.INVISIBLE
    }

    override fun addBrew(
        id: String,
        name: String,
        brewType: String,
        country: String,
        city: String,
        state: String
    ) {
        val scope = CoroutineScope(coroutine)
        scope.launch(Dispatchers.IO){
            operation.insertBrew(id, name, brewType, country, city, state)
            withContext(Dispatchers.Main){
            }
        }
    }
    override fun removeBrew(id: String) {
        val scope = CoroutineScope(coroutine)
        scope.launch(Dispatchers.IO){
            operation.removeBrew(id)
        }
    }

}