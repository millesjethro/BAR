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
    var brewType: String = "",
    var city: String = "",
    var country: String = "",
    var name: String = "",
    var state: String = "",

): RealmObject()
