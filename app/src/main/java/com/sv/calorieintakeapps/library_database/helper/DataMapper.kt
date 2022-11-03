package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.data.source.remote.response.*
import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.ReportStatus
import com.sv.calorieintakeapps.library_database.domain.model.Food
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
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
            value = item?.value ?: 0.0
        )
    } ?: listOf()
}

fun mapResponseToDomain(input: MerchantMenuResponse): List<Food> {
    return input.foods?.map { item ->
        Food(
            id = item?.id ?: -1,
            merchantId = item?.merchantId ?: -1,
            name = item?.name.orEmpty(),
            portion = item?.portion ?: 0,
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
            foodId = this?.foodId ?: -1,
            foodName = this?.foodName.orEmpty(),
            postImage = this?.postImage.orEmpty(),
            preImage = this?.preImage.orEmpty(),
            date = this?.date.orEmpty(),
            status = ReportStatus.new(this?.status.orEmpty()),
            percentage = this?.percentage ?: 0,
            mood = this?.mood.orEmpty(),
        )
    }
}

fun mapResponseToDomain(input: ReportsResponse): List<Report> {
    return input.reports?.map { item ->
        Report(
            id = item?.id ?: -1,
            userId = item?.userId ?: -1,
            foodId = item?.foodId ?: -1,
            foodName = item?.foodName.orEmpty(),
            postImage = item?.postImage.orEmpty(),
            preImage = item?.preImage.orEmpty(),
            date = item?.date.orEmpty(),
            status = ReportStatus.new(item?.status.orEmpty()),
            percentage = item?.percentage ?: 0,
            mood = item?.mood.orEmpty(),
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
            weight = this?.weight ?: 0
        )
    }
}