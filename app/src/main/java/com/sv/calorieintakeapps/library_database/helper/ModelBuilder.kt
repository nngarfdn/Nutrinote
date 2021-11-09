package com.sv.calorieintakeapps.library_database.helper

import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.domain.model.User

object ReportBuilder {

    fun create(
        userId: Int,
        foodId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ): Report {
        return Report(
            userId = userId,
            foodId = foodId,
            date = "$date $time",
            preImage = preImageUri,
            postImage = postImageUri
        )
    }

    fun update(
        id: Int,
        userId: Int,
        date: String,
        time: String,
        preImageUri: String,
        postImageUri: String
    ): Report {
        return Report(
            id = id,
            userId = userId,
            date = "$date $time",
            preImage = preImageUri,
            postImage = postImageUri
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
        weight: Int
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