/**
 * Credit to Jeroen Mols
 * https://jeroenmols.com/blog/2019/04/02/modularizationexample/
 * */

package com.sv.calorieintakeapps.library_common.action

import android.content.Context
import android.content.Intent
import com.sv.calorieintakeapps.feature.reportdetails.presentation.ReportDetailsActivity
import com.sv.calorieintakeapps.feature_auth.presentation.login.LoginActivity
import com.sv.calorieintakeapps.feature_auth.presentation.register.RegisterActivity
import com.sv.calorieintakeapps.feature_fooddetails.presentation.FoodDetailActivity
import com.sv.calorieintakeapps.feature_homepage.presentation.HomepageActivity
import com.sv.calorieintakeapps.feature_merchantlist.presentation.MerchantsActivity
import com.sv.calorieintakeapps.feature_merchantmenu.presentation.MerchantMenuActivity
import com.sv.calorieintakeapps.feature_profile.presentation.ProfileActivity
import com.sv.calorieintakeapps.feature_reporting.presentation.ReportingActivity
import com.sv.calorieintakeapps.feature_scanner.presentation.ScanActivity

object Actions {

    const val EXTRA_FOOD_ID = "extra_food_id"
    const val EXTRA_FOOD_NAME = "extra_food_name"
    const val EXTRA_FOOD_IMAGE = "extra_food_image"
    const val EXTRA_MERCHANT_ID = "extra_merchant_id"
    const val EXTRA_MERCHANT_NAME = "extra_merchant_name"
    const val EXTRA_REPORT_ID = "extra_report_id"

    fun openFoodDetailsIntent(
        context: Context,
        merchantName: String,
        foodId: Int,
        foodName: String,
        foodImage: String,
    ): Intent {
        return Intent(context, FoodDetailActivity::class.java)
            .putExtra(EXTRA_MERCHANT_NAME, merchantName)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
            .putExtra(EXTRA_FOOD_IMAGE, foodImage)
    }

    fun openHomepageIntent(context: Context): Intent {
        return Intent(context, HomepageActivity::class.java)
    }

    fun openLoginIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    fun openMerchantListIntent(context: Context): Intent {
        return Intent(context, MerchantsActivity::class.java)
    }

    fun openMerchantMenuIntent(context: Context, merchantId: Int): Intent {
        return Intent(context, MerchantMenuActivity::class.java)
            .putExtra(EXTRA_MERCHANT_ID, merchantId)
    }

    fun openProfileIntent(context: Context): Intent {
        return Intent(context, ProfileActivity::class.java)
    }

    fun openRegisterIntent(context: Context): Intent {
        return Intent(context, RegisterActivity::class.java)
    }

    fun openReportingIntent(context: Context, foodId: Int, foodName: String): Intent {
        return Intent(context, ReportingActivity::class.java)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun openReportEditingIntent(context: Context, reportId: Int, foodName: String): Intent {
        return Intent(context, ReportingActivity::class.java)
            .putExtra(EXTRA_REPORT_ID, reportId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun openReportDetailsIntent(
        context: Context,
        reportId: Int,
        foodId: Int,
        foodName: String
    ): Intent {
        return Intent(context, ReportDetailsActivity::class.java)
            .putExtra(EXTRA_REPORT_ID, reportId)
            .putExtra(EXTRA_FOOD_ID, foodId)
            .putExtra(EXTRA_FOOD_NAME, foodName)
    }

    fun openScannerIntent(context: Context): Intent {
        return Intent(context, ScanActivity::class.java)
    }
}