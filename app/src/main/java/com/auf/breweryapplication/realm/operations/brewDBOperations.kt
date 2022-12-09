package com.auf.breweryapplication.realm.operations

import android.util.Log
import com.auf.breweryapplication.Models.BrewDB
import com.auf.breweryapplication.Models.BrewingInformation
import com.auf.breweryapplication.realm.config.RealmConfig
import com.auf.breweryapplication.realm.datebase.BrewRealm
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class brewDBOperations(private var config: RealmConfiguration) {

    suspend fun insertBrew(id: String, name: String, brewType: String, country: String,city:String, state: String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val brew = BrewRealm(id = id,name = name, brewType = brewType, country = country, city = city,state = state )
            realmTransaction.insert(brew)
        }

    }

    // delete

    // filter
    suspend fun filterBrew(id: String): ArrayList<BrewDB>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<BrewDB>()
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(BrewRealm::class.java)
                .contains("id",id, Case.SENSITIVE)
                .findAll()
                .map{
                    mapbrew(it)
                })

        }
        return realmResults
    }



    suspend fun retrieveBrew(): ArrayList<BrewDB>{
        val realm = Realm.getInstance(config)
        val realmResult = arrayListOf<BrewDB>()
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

    suspend fun removeBrew(id : String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val breweryToRemove = realmTransaction
                .where(BrewRealm::class.java)
                .equalTo("id",id)
                .findFirst()
            breweryToRemove?.deleteFromRealm()
        }
    }

    private fun mapbrew(brew: BrewRealm): BrewDB{
        return BrewDB(
            id = brew.id,
            name = brew.name,
            brew = brew.brewType,
            city = brew.city,
            country = brew.country,
            state = brew.state,

        )
    }
}