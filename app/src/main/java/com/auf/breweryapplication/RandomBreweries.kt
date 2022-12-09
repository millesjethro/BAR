    package com.auf.breweryapplication

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.provider.ContactsContract.Data
    import android.view.View
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.auf.breweryapplication.Adaprters.DataAdapters
    import com.auf.breweryapplication.Models.BrewingInfoData
    import com.auf.breweryapplication.Models.BrewingInformation
    import com.auf.breweryapplication.Services.Helper.Retrofit
    import com.auf.breweryapplication.Services.Repository.BreweriesAPI
    import com.auf.breweryapplication.databinding.ActivityRandomBreweriesBinding
    import com.auf.breweryapplication.realm.config.RealmConfig
    import com.auf.breweryapplication.realm.operations.brewDBOperations
    import com.bumptech.glide.Glide
    import com.bumptech.glide.load.resource.bitmap.RoundedCorners
    import io.realm.RealmConfiguration
    import kotlinx.coroutines.*
    import java.util.*
    import kotlin.coroutines.CoroutineContext
    import kotlin.random.Random

    class RandomBreweries : AppCompatActivity(), View.OnClickListener, DataAdapters.DataAdaptersInterface {
    private lateinit var binding: ActivityRandomBreweriesBinding
    private lateinit var adapter: DataAdapters
    private lateinit var brewingData: ArrayList<BrewingInformation>
    private lateinit var config: RealmConfiguration
    private lateinit var operation: brewDBOperations
    private lateinit var coroutine: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        brewingData = arrayListOf()
        adapter = DataAdapters(brewingData, this,this)
        binding.loadimg.visibility = View.INVISIBLE
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvRandom.layoutManager = layoutManager
        binding.rvRandom.adapter = adapter

        binding.imgBtnRandom.setOnClickListener(this)
        config = RealmConfig.getConfiguration()
        operation = brewDBOperations(config)
        coroutine = Job() + Dispatchers.IO
    }

    private fun BreweriesData() {
        super.onResume()

        val BreweriesAPI = Retrofit.getInstance().create(BreweriesAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val result = BreweriesAPI.getRandomBreweries(Random.nextInt(10,20))
            val breweries = result.body()

            if(breweries != null){
                brewingData.clear()
                brewingData.addAll(breweries)
                withContext(Dispatchers.Main){
                    adapter.UpdateData(brewingData)
                    binding.imgBtnRandom.cancelAnimation()
                    binding.loadimg.visibility = View.INVISIBLE
                    binding.rvRandom.visibility = View.VISIBLE
                }
            }

        }
    }

        override fun onClick(p0: View?) {
            when(p0!!.id){
                (R.id.imgBtnRandom)->{
                    binding.imgBtnRandom.playAnimation()
                    binding.loadimg.visibility = View.VISIBLE
                    binding.rvRandom.visibility = View.INVISIBLE
                    BreweriesData()
                }
            }
        }

        override fun addBrew(
            name: String,
            brewType: String,
            country: String,
            city: String,
            state: String
        ) {
            val scope = CoroutineScope(coroutine + CoroutineName("AddToDatabase"))
            scope.launch(Dispatchers.IO) {
                operation.insertBrew(name,brewType , country, city, state)
            }

        }

        override fun removeBrew(id: String) {
            val scope = CoroutineScope(coroutine + CoroutineName("RemoveDBEntry"))
            scope.launch (Dispatchers.IO) {
                operation.removeBrew(id)
            }
        }
    }