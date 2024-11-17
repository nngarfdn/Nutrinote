package com.sv.calorieintakeapps.library_database.data.source.remote.urt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UrtFoodDetail(
    val id: Int,
    val kode: String? = null,
    @Json(name = "nama_bahan") val name: String? = null,
    val sumber: String? = null,
    val air: String? = null,
    val energi: String? = null,
    val protein: String? = null,
    val lemak: String? = null,
    val karbohidrat: String? = null,
    val serat: String? = null,
    val abu: String? = null,
    val kalsium: String? = null,
    val fosfor: String? = null,
    val besi: String? = null,
    val natrium: String? = null,
    val kalium: String? = null,
    val tembaga: String? = null,
    val seng: String? = null,
    val retinol: String? = null,
    @Json(name = "b_kar") val bKar: String? = null,
    @Json(name = "kar_tot") val karTot: String? = null,
    val thiamin: String? = null,
    val rifobla: String? = null,
    val niasin: String? = null,
    @Json(name = "vit_c") val vitC: String? = null,
    val bdd: String? = null,
    @Json(name = "urt_tersedia") val urt: List<Urt>? = null
)

@JsonClass(generateAdapter = true)
data class Urt(
    val id: Int,
    @Json(name = "nama_urt") val name: String? = null,
    @Json(name = "gram_ml_per_porsi") val gramMlPerPorsi: String? = null,
//    @Json(name = "gambar_makanan") val imageLink: String? = null
) {
    override fun toString(): String {
        val str = StringBuilder().append(name)
        if (gramMlPerPorsi != null) str.append(" ($gramMlPerPorsi g/ml)")
        return str.toString()
    }
}


