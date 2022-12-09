package com.auf.breweryapplication.Adaprters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auf.breweryapplication.Models.BrewDB
import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.R
import com.auf.breweryapplication.databinding.BreweriesContentRvBinding
import com.auf.breweryapplication.realm.config.RealmConfig
import com.auf.breweryapplication.realm.operations.brewDBOperations
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.*
import java.io.Serializable


class BrewDBAdapters(private var brewlist: ArrayList<BrewDB>, private var context: Context,private var favorite:BrewDBAdaptersInterface) : RecyclerView.Adapter<BrewDBAdapters.BrewDBViewHolder>(),
    Serializable {
    interface BrewDBAdaptersInterface{
        fun removeFav(id:String)
    }
    inner class BrewDBViewHolder(private val binding: BreweriesContentRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: BrewDB) {
            with (binding){
                binding.companyName.text = itemData.name
                binding.txtCity.text = itemData.city
                binding.txtCountry.text = itemData.country
                binding.txtState.text = itemData.state
                binding.txtBrewerytype.text = itemData.brew
                binding.txtid.text = "ID: "+itemData.id
                Glide.with(binding.root)
                    .load("#99365475")
                    .transform(RoundedCorners(25))
                    .into(binding.brewtype)

                when(itemData.brew) {
                    ("micro")->{
                        Glide.with(binding.root)
                            .load(R.drawable.micro)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("brewpub")->{
                        Glide.with(binding.root)
                            .load(R.drawable.brewpub)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("closed")->{
                        Glide.with(binding.root)
                            .load(R.drawable.closed)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("large")->{
                        Glide.with(binding.root)
                            .load(R.drawable.large)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("planning")->{
                        Glide.with(binding.root)
                            .load(R.drawable.large)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("regional")->{
                        Glide.with(binding.root)
                            .load(R.drawable.regional)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("bar")->{
                        Glide.with(binding.root)
                            .load(R.drawable.bar)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("contract")->{
                        Glide.with(binding.root)
                            .load(R.drawable.contract)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("nano")->{
                        Glide.with(binding.root)
                            .load(R.drawable.nano)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                    ("proprietor")->{
                        Glide.with(binding.root)
                            .load(R.drawable.proprietor)
                            .circleCrop()
                            .into(binding.brewtype)
                    }
                }

                val country = itemData.country.replace(" ", "_")
                Glide.with(binding.root)
                    .load("https://www.sciencekids.co.nz/images/pictures/flags120/"+country+".jpg")
                    .circleCrop()
                    .into(binding.flagImg)

                val realmConfig = RealmConfig.getConfiguration()
                val brewDBOperations = brewDBOperations(realmConfig)
                val coroutineContext = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutineContext+CoroutineName("AddToDatabase"))
                scope.launch(Dispatchers.IO){
                    val result = brewDBOperations.filterBrew(itemData.id)
                    if (result.size != 0) {
                        binding.favbtn.tag = "favorited"
                        binding.favbtn.setImageResource(R.drawable.favorited)
                    }
                }

                binding.favbtn.setOnClickListener{
                        binding.favbtn.setImageResource(R.drawable.favorite)
                        binding.favbtn.tag = "favorite"
                        favorite.removeFav(itemData.id)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrewDBViewHolder {
        val binding = BreweriesContentRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BrewDBViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrewDBViewHolder, position: Int) {
        val brewData = brewlist[position]
        holder.bind(brewData)
    }

    override fun getItemCount(): Int {
        return brewlist.size
    }

    fun updateData(brewlists: ArrayList<BrewDB>){
        this.brewlist = arrayListOf()
        notifyDataSetChanged()
        this.brewlist.addAll(brewlists)
        this.notifyItemInserted(this.brewlist.size)
    }
}