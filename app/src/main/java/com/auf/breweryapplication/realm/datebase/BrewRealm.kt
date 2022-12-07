package com.auf.breweryapplication.realm.datebase

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.sql.Struct

open class BrewRealm(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    @Required
    var address2: String = "",
    var address3: String = "",
    var brewType: String = "",
    var city: String = "",
    var country: String = "",
    var countyProvince: String = "",
    var createdat: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var name: String = "",
    var phone: String = "",
    var postal: String = "",
    var state: String = "",
    var Street: String = "",
    var updatedat: String ="",
    var websiteurl: String="",

): RealmObject()
