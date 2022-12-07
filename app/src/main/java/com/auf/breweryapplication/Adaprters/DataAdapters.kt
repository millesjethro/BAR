package com.auf.breweryapplication.Adaprters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.R
import com.auf.breweryapplication.databinding.BreweriesContentRvBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.Serializable
import kotlin.coroutines.coroutineContext

class DataAdapters(private var brewlist: ArrayList<BrewingInformation>, context: Context) : RecyclerView.Adapter<DataAdapters.BrewDataViewHolder>(), Serializable {
    inner class BrewDataViewHolder(private val binding: BreweriesContentRvBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: BrewingInformation){
            binding.companyName.text = itemData.name
            binding.txtCity.text = itemData.city
            binding.txtCountry.text = itemData.country
            binding.txtState.text = itemData.state
            binding.txtBrewerytype.text = itemData.brewery_type
            binding.txtid.text = "ID: "+itemData.id
            Glide.with(binding.root)
                .load("#99365475")
                .transform(RoundedCorners(25))
                .into(binding.brewtype)

            when(itemData.brewery_type) {
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


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrewDataViewHolder {
        val binding = BreweriesContentRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BrewDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrewDataViewHolder, position: Int) {
        val brewData = brewlist[position]
        holder.bind(brewData)
    }

    override fun getItemCount(): Int {
        return brewlist.size
    }

    fun UpdateData(breweryDataList: ArrayList<BrewingInformation>){
        this.brewlist = arrayListOf()
        notifyDataSetChanged()
        this.brewlist = breweryDataList
        this.notifyItemInserted(this.brewlist.size)
    }


}
