package com.auf.breweryapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.auf.breweryapplication.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners



class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        Glide.with(this)
            .load("https://img.icons8.com/color/512/bavarian-beer-mug.png")
            .circleCrop()
            .into(binding.imgListBrew)
        Glide.with(this)
            .load("https://img.icons8.com/color/512/bavarian-beer-mug.png")
            .circleCrop()
            .into(binding.imgRandomBrewery)
        Glide.with(this)
            .load("https://img.icons8.com/color/512/bavarian-beer-mug.png")
            .circleCrop()
            .into(binding.imgFavBrew)
        Glide.with(this)
            .load("https://static.vecteezy.com/system/resources/thumbnails/007/522/796/small/abstract-geometric-white-stripe-shapes-with-golden-light-in-gradient-white-background-free-vector.jpg")
            .transform(RoundedCorners(25))
            .override(390,120)
            .into(binding.brewList)
        Glide.with(this)
            .load("https://static.vecteezy.com/system/resources/thumbnails/007/522/796/small/abstract-geometric-white-stripe-shapes-with-golden-light-in-gradient-white-background-free-vector.jpg")
            .transform(RoundedCorners(25))
            .override(390,120)
            .into(binding.brewRandom)
        Glide.with(this)
            .load("https://static.vecteezy.com/system/resources/thumbnails/007/522/796/small/abstract-geometric-white-stripe-shapes-with-golden-light-in-gradient-white-background-free-vector.jpg")
            .transform(RoundedCorners(25))
            .override(390,120)
            .into(binding.favorites)


        binding.brewList.setOnClickListener(this)
        binding.brewRandom.setOnClickListener(this)
        binding.favorites.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.brewList)->{
                val intent = Intent(this,ListBreweries::class.java)
                startActivity(intent)
            }
            (R.id.brewRandom)->{
                val intent = Intent(this,RandomBreweries::class.java)
                startActivity(intent)
            }
            (R.id.favorites)->{
                val intent = Intent(this,Favorites::class.java)
                startActivity(intent)
            }

        }
    }
}

