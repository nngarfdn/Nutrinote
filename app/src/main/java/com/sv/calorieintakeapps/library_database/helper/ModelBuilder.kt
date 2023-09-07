package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User

object ReportBuilder {
    
    fun create(
        userId: Int,
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageUri: String,
        postImageUri: String,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ): Report {
        return Report(
            userId = userId,
            foodId = foodId,
            date = "$date $time",
            percentage = percentage,
            mood = mood,
            preImage = preImageUri,
            postImage = postImageUri,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
        )
    }
    
    fun update(
        id: Int,
        userId: Int,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageUri: String,
        postImageUri: String,
        foodId: Int?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ): Report {
        return Report(
            id = id,
            userId = userId,
            date = "$date $time",
            percentage = percentage,
            mood = mood,
            preImage = preImageUri,
            postImage = postImageUri,
            foodId = foodId,
            nilaigiziComFoodId = nilaigiziComFoodId,
            portionCount = portionCount,
        )
    }
    
}

object UserBuilder {
    
    fun update(
        id: Int,
        name: String,
        photoUri: String,
        password: String,
        gender: Gender,
        age: Int,
        height: Int,
        weight: Int,
    ): User {
        return User(
            id = id,
            name = name,
            photo = photoUri,
            password = password,
            gender = gender,
            age = age,
            height = height,
            weight = weight
        )
    }
    
}