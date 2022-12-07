package com.auf.breweryapplication.realm.operations

import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.realm.config.RealmConfig
import com.auf.breweryapplication.realm.datebase.BrewRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class brewDBOperations(private var config: RealmConfiguration) {

    suspend fun insertBrew(title: String, brewType: String, country: String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val brew = BrewRealm(name = title, brewType = brewType, country = country)
            realmTransaction.insert(brew)
        }
    }

    suspend fun retrieveBrew(): ArrayList<BrewingInformation>{
        val realm = Realm.getInstance(config)
        val realmResult = arrayListOf<BrewingInformation>()
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResult.addAll(realmTransaction
                .where(BrewRealm::class.java)
                .findAll()
                .map{
                    mapbrew(it)
                })

        }
        return realmResult
    }

    private fun mapbrew(brew: BrewRealm): BrewingInformation{
        return BrewingInformation(
            id = brew.id,
            address_2 = brew.address2,
            address_3 = brew.address3,
            brewery_type = brew.brewType,
            city = brew.city,
            country = brew.country,
            county_province = brew.countyProvince,
            created_at = brew.createdat,
            latitude = brew.latitude,
            longitude = brew.longitude,
            name = brew.name,
            phone = brew.phone,
            postal_code = brew.postal,
            state = brew.state,
            street = brew.Street,
            updated_at = brew.updatedat,
            website_url = brew.websiteurl
        )
    }
}