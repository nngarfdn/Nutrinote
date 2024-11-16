package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.FoodNutrientsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantMenuResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.MerchantsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.ReportsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.UserResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionDetailsResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.domain.model.Merchant
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User

fun mapResponseToDomain(input: FoodNutrientsResponse): List<FoodNutrient> {
    return input.foodNutrients?.map { item ->
        FoodNutrient(
            id = item?.id ?: -1,
            foodId = item?.foodId ?: -1,
            nutrientId = item?.nutrientId ?: -1,
            nutrientName = item?.nutrientName.orEmpty(),
            nutrientUnit = item?.nutrientUnit.orEmpty(),
            value = item?.value ?: 0.0,
            akgDay = item?.akgDay.orEmpty(),
        )
    } ?: listOf()
}

fun mapResponseToDomain(input: List<FoodNutrientsResponse>): List<FoodNutrient> {
    val mapped = mutableListOf<FoodNutrient>()
    input.forEach { foodNutrientsResponse ->
        val list = mapResponseToDomain(foodNutrientsResponse)
        mapped.addAll(list)
    }
    return mapped
}

fun mapResponseToDomain(input: MerchantMenuResponse): List<Food> {
    return input.foods?.map { item ->
        Food(
            id = item?.id ?: -1,
            merchantId = item?.merchantId ?: -1,
            name = item?.name.orEmpty(),
            portion = item?.portion.orEmpty(),
            label = FoodLabel.new(item?.label.orEmpty()),
            price = item?.price.orEmpty(),
            image = item?.image.orEmpty()
        )
    } ?: listOf()
}

fun mapResponseToDomain(input: MerchantsResponse): List<Merchant> {
    return input.merchants?.map { item ->
        Merchant(
            id = item?.id ?: -1,
            name = item?.name.orEmpty(),
            address = item?.address.orEmpty()
        )
    } ?: listOf()
}

fun mapResponseToDomain(input: ReportResponse): Report {
    with(input.report) {
        return Report(
            id = this?.id ?: -1,
            userId = this?.userId ?: -1,
            foodName = this?.foodName.orEmpty(),
            date = this?.date.orEmpty(),
            percentage = this?.percentage ?: 0,
            mood = this?.mood.orEmpty(),
            preImageUrl = this?.preImage.orEmpty(),
            postImageUrl = this?.postImage.orEmpty(),
            air = this?.totalAir.toString(),
            calories = this?.totalEnergi.toString(),
            carbs = this?.totalKarbo.toString(),
            fat = this?.totalLemak.toString(),
            protein = this?.totalProtein.toString(),
            gramPerUrt = this?.gramPerUrt ?: 0f,
            gramTotalDikonsumsi = this?.gramTotalDikonsumsi ?: 0f,
            isUsingUrt = this?.isUsingUrt ?: false,
            porsiUrt = this?.porsiUrt?.toInt() ?: 0,
            idMakananNewApi = this?.idMakananNewApi ?: -1
        )
    }
}

fun mapResponseToDomain(input: ReportsResponse): List<Report> {
    return input.reports?.map { item ->
        Report(
            id = item?.id ?: -1,
            userId = item?.userId ?: -1,
            foodName = item?.foodName.orEmpty(),
            date = item?.date.orEmpty(),
            percentage = item?.percentage ?: 0,
            mood = item?.mood.orEmpty(),
            preImageUrl = item?.preImage.orEmpty(),
            postImageUrl = item?.postImage.orEmpty(),
            air = item?.totalAir.toString(),
            calories = item?.totalEnergi.toString(),
            carbs = item?.totalKarbo.toString(),
            fat = item?.totalLemak.toString(),
            protein = item?.totalProtein.toString(),
            gramPerUrt = item?.gramPerUrt ?: 0f,
            gramTotalDikonsumsi = item?.gramTotalDikonsumsi ?: 0f,
            isUsingUrt = item?.isUsingUrt ?: false,
            porsiUrt = item?.porsiUrt?.toInt() ?: 0,
            idMakananNewApi = item?.idMakananNewApi ?: -1
        )
    } ?: listOf()
}

fun mapResponseToDomain(input: UserResponse): User {
    with(input.user) {
        return User(
            id = this?.id ?: -1,
            name = this?.name.orEmpty(),
            email = this?.email.orEmpty(),
            photo = this?.photo.orEmpty(),
            password = this?.password.orEmpty(),
            gender = Gender.new(this?.gender ?: 0),
            age = this?.age ?: 0,
            height = this?.height ?: 0,
            weight = this?.weight ?: 0,
            activityLevel = ActivityLevel.new(this?.activity ?: 1),
            stressLevel = StressLevel.new(this?.stress ?: 1),
        )
    }
}

fun mapResponseToDomain(input: FoodNutritionSearchResponse): List<FoodNutrition> {
    return input.data?.data?.map {
        mapResponseToDomain(it)
    }.orEmpty()
}

fun mapResponseToDomain(input: FoodNutritionSearchResponse.DataItem?): FoodNutrition {
    return FoodNutrition(
        foodId = input?.id ?: -1,
        name = input?.name.orEmpty(),
        imageUrl = input?.images?.firstOrNull().orEmpty(),
        calories = "",
        protein = "",
        fat = "",
        carbs = "",
    )
}

fun mapResponseToDomain(input: FoodNutritionDetailsResponse): FoodNutrition {
    with(input.data) {
        val calories = this?.nutritions?.firstOrNull { it?.name == "Energi" }
        val protein = this?.nutritions?.firstOrNull { it?.name == "Protein" }
        val fat = this?.nutritions?.firstOrNull { it?.name == "Lemak total" }
        val carbs = this?.nutritions?.firstOrNull { it?.name == "Karbohidrat total" }
        
        return FoodNutrition(
            foodId = this?.id ?: -1,
            name = this?.name.orEmpty(),
            imageUrl = this?.images?.firstOrNull().orEmpty(),
            calories = "${calories?.value} ${calories?.unit}",
            protein = "${protein?.value} ${protein?.unit}",
            fat = "${fat?.value} ${fat?.unit}",
            carbs = "${carbs?.value} ${carbs?.unit}",
        )
    }
}