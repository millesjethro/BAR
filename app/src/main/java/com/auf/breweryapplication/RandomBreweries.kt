    package com.auf.breweryapplication

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.auf.breweryapplication.Adaprters.DataAdapters
    import com.auf.breweryapplication.Models.BrewingInfoData
    import com.auf.breweryapplication.Models.BrewingInformation
    import com.auf.breweryapplication.Services.Helper.Retrofit
    import com.auf.breweryapplication.Services.Repository.BreweriesAPI
    import com.auf.breweryapplication.databinding.ActivityRandomBreweriesBinding
    import com.bumptech.glide.Glide
    import com.bumptech.glide.load.resource.bitmap.RoundedCorners
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import java.util.*
    import kotlin.random.Random

    class RandomBreweries : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRandomBreweriesBinding
    private lateinit var adapter: DataAdapters
    private lateinit var brewingData: ArrayList<BrewingInformation>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Glide.with(this)
            .load("https://img.icons8.com/ios/512/ask-question.png")
            .transform(RoundedCorners(25))
            .into(binding.imgBtnRandom);

        brewingData = arrayListOf()
        adapter = DataAdapters(brewingData, this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvRandom.layoutManager = layoutManager
        binding.rvRandom.adapter = adapter

        binding.imgBtnRandom.setOnClickListener(this)

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
                }
            }

        }
    }

        override fun onClick(p0: View?) {
            when(p0!!.id){
                (R.id.imgBtnRandom)->{
                    BreweriesData()
                }
            }
        }
    }