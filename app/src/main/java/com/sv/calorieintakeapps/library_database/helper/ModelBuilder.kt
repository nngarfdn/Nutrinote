package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.domain.enum.ActivityLevel
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.enum.StressLevel
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User
import java.io.File

object ReportBuilder {
    
    fun create(
        userId: Int,
        foodId: Int?,
        date: String,
        time: String,
        percentage: Int?,
        mood: String,
        preImageFile: File?,
        postImageFile: File?,
        nilaigiziComFoodId: Int?,
        portionCount: Float?,
    ): Report {
        return Report(
            userId = userId,
            foodId = foodId,
            date = "$date $time",
            percentage = percentage,
            mood = mood,
            preImageFile = preImageFile,
            postImageFile = postImageFile,
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
        preImageFile: File?,
        postImageFile: File?,
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
            preImageFile = preImageFile,
            postImageFile = postImageFile,
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
        activityLevel: ActivityLevel,
        stressLevel: StressLevel,
    ): User {
        return User(
            id = id,
            name = name,
            photo = photoUri,
            password = password,
            gender = gender,
            age = age,
            height = height,
            weight = weight,
            activityLevel = activityLevel,
            stressLevel = stressLevel,
        )
    }
    
}