package com.auf.breweryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auf.breweryapplication.Adaprters.BrewDBAdapters
import com.auf.breweryapplication.Adaprters.BreweryAdapter
import com.auf.breweryapplication.Adaprters.DataAdapters
import com.auf.breweryapplication.Models.BrewDB
import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.Services.Helper.Retrofit
import com.auf.breweryapplication.Services.Repository.BreweriesAPI
import com.auf.breweryapplication.databinding.ActivityFavoritesBinding
import com.auf.breweryapplication.realm.config.RealmConfig
import com.auf.breweryapplication.realm.operations.brewDBOperations
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class Favorites : AppCompatActivity(), BrewDBAdapters.BrewDBAdaptersInterface {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: BrewDBAdapters
    private lateinit var brewingData: ArrayList<BrewDB>
    private lateinit var config: RealmConfiguration
    private lateinit var operation: brewDBOperations
    private lateinit var coroutine: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brewingData = arrayListOf()
        adapter = BrewDBAdapters(brewingData,this,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.favrv.adapter = adapter
        binding.favrv.layoutManager = layoutManager

        config = RealmConfig.getConfiguration()
        operation = brewDBOperations(config)
        coroutine = Job() + Dispatchers.IO
    }

    override fun onResume() {
        super.onResume()
        getBrew()
    }

    private fun getBrew(){
        val scope = CoroutineScope(coroutine)
        scope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                val result = operation.retrieveBrew()
                withContext(Dispatchers.Main){
                    adapter.updateData(result)
                }
            }
        }
    }

    override fun removeFav(id: String) {
        val scope = CoroutineScope(coroutine)
        scope.launch (Dispatchers.IO) {
            operation.removeBrewery(id)
            withContext(Dispatchers.Main){
                getBrew()
            }
        }
    }


}