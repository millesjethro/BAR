package com.auf.breweryapplication.Models

import java.io.Serializable

data class BrewingInformation(
    val address_2: Any,
    val address_3: Any,
    val brewery_type: String,
    val city: String,
    val country: String,
    val county_province: Any,
    val created_at: String,
    val id: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val phone: String,
    val postal_code: String,
    val state: String,
    val street: String,
    val updated_at: String,
    val website_url: String
) : Serializable