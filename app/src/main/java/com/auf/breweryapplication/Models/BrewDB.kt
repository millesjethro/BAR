package com.auf.breweryapplication.Models

import java.io.Serializable

data class BrewDB(
    val id: String,
    val name: String,
    val brew: String,
    val country: String,
    val city: String,
    val state: String
): Serializable
