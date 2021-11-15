/**
 * Credit to Jeroen Mols
 * https://jeroenmols.com/blog/2019/04/02/modularizationexample/
 * */

package com.sv.calorieintakeapps.library_common.action

import android.content.Context
import android.content.Intent
import com.sv.calorieintakeapps.feature_auth.presentation.login.LoginActivity
import com.sv.calorieintakeapps.feature_auth.presentation.register.RegisterActivity
import com.sv.calorieintakeapps.feature_fooddetails.presentation.FoodDetailActivity
import com.sv.calorieintakeapps.feature_homepage.presentation.HomepageActivity
import com.sv.calorieintakeapps.feature_merchantlist.presentation.MerchantsActivity
import com.sv.calorieintakeapps.feature_merchantmenu.presentation.MerchantMenuActivity
import com.sv.calorieintakeapps.feature_profile.presentation.ProfileActivity
import com.sv.calorieintakeapps.feature_reportdetails.presentation.ReportDetailsActivity
import com.sv.calorieintakeapps.feature_reporting.presentation.ReportingActivity
import com.sv.calorieintakeapps.feature_scanner.presentation.ScanActivity
import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel

object Actions {

    const val EXTRA_FOOD_ID = "extra_food_id"
    const val EXTRA_FOOD_NAME = "extra_food_name"
    const val EXTRA_FOOD_IMAGE = "extra_food_image"
    const val EXTRA_FOOD_LABEL = "extra_food_label"
    const val EXTRA_MERCHANT_ID = "extra_merchant_id"
    const val EXTRA_MERCHANT_NAME = "extra_merchant_name"
    const val EXTRA_REPORT_ID = "extra_report_id"

    fun Context?.openFoodDetailsIntent(
        merchantName: String,
        foodId: Int,
        foodName: String,
        foodImage: String,
        foodLabel : String
    ): Intent {
        return Intent(this?.applicationContext, FoodDetailActivity::class.java)
            .putExtra(EXTRA_MERCHANT_NAME, merchantName)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
            .putExtra(EXTRA_FOOD_IMAGE, foodImage)
            .putExtra(EXTRA_FOOD_LABEL, foodLabel)
    }

    fun Context?.openHomepageIntent(): Intent {
        return Intent(this?.applicationContext, HomepageActivity::class.java)
    }

    fun Context?.openLoginIntent(): Intent {
        return Intent(this?.applicationContext, LoginActivity::class.java)
    }

    fun Context?.openMerchantListIntent(): Intent {
        return Intent(this?.applicationContext, MerchantsActivity::class.java)
    }

    fun Context?.openMerchantMenuIntent(merchantId: Int): Intent {
        return Intent(this?.applicationContext, MerchantMenuActivity::class.java)
            .putExtra(EXTRA_MERCHANT_ID, merchantId)
    }

    fun Context?.openProfileIntent(): Intent {
        return Intent(this?.applicationContext, ProfileActivity::class.java)
    }

    fun Context?.openRegisterIntent(): Intent {
        return Intent(this?.applicationContext, RegisterActivity::class.java)
    }

    fun Context?.openReportingIntent(foodId: Int, foodName: String): Intent {
        return Intent(this?.applicationContext, ReportingActivity::class.java)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun Context?.openReportEditingIntent(reportId: Int, foodName: String): Intent {
        return Intent(this?.applicationContext, ReportingActivity::class.java)
            .putExtra(EXTRA_REPORT_ID, reportId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun Context?.openReportDetailsIntent(reportId: Int, foodId: Int, foodName: String): Intent {
        return Intent(this?.applicationContext, ReportDetailsActivity::class.java)
            .putExtra(EXTRA_REPORT_ID, reportId)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun Context?.openScannerIntent(): Intent {
        return Intent(this?.applicationContext, ScanActivity::class.java)
    }
}