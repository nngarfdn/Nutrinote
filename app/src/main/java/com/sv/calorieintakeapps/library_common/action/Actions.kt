///**
// * Credit to Jeroen Mols
// * https://jeroenmols.com/blog/2019/04/02/modularizationexample/
// * */
//
//package com.sv.calorieintakeapps.core.common.action
//
//import android.content.Intent
//
//object Actions {
//
//    const val EXTRA_FOOD_ID = "extra_food_id"
//    const val EXTRA_FOOD_NAME = "extra_food_name"
//    const val EXTRA_FOOD_IMAGE = "extra_food_image"
//    const val EXTRA_MERCHANT_ID = "extra_merchant_id"
//    const val EXTRA_MERCHANT_NAME = "extra_merchant_name"
//    const val EXTRA_REPORT_ID = "extra_report_id"
//
//    private fun internalIntent(action: String): Intent {
//        return Intent(action).setPackage("com.sv.calorieintakeapps")
//    }
//
//    fun openFoodDetailsIntent(
//        merchantName: String,
//        foodId: Int,
//        foodName: String,
//        foodImage: String,
//    ): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_fooddetails")
//            .putExtra(EXTRA_MERCHANT_NAME, merchantName)
//            .putExtra(EXTRA_FOOD_ID, foodId)
//            .putExtra(EXTRA_FOOD_NAME, foodName)
//            .putExtra(EXTRA_FOOD_IMAGE, foodImage)
//    }
//
//    fun openHomepageIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_homepage")
//    }
//
//    fun openLoginIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_auth.login")
//    }
//
//    fun openMerchantListIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_merchantlist")
//    }
//
//    fun openMerchantMenuIntent(merchantId: Int): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_merchantmenu")
//            .putExtra(EXTRA_MERCHANT_ID, merchantId)
//    }
//
//    fun openProfileIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_profile")
//    }
//
//    fun openRegisterIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_auth.register")
//    }
//
//    fun openReportingIntent(foodId: Int, foodName: String): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_reporting")
//            .putExtra(EXTRA_FOOD_ID, foodId)
//            .putExtra(EXTRA_FOOD_NAME, foodName)
//    }
//
//    fun openReportEditingIntent(reportId: Int, foodName: String): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_reporting")
//            .putExtra(EXTRA_REPORT_ID, reportId)
//            .putExtra(EXTRA_FOOD_NAME, foodName)
//    }
//
//    fun openReportDetailsIntent(reportId: Int, foodId: Int, foodName: String): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_reportdetails")
//            .putExtra(EXTRA_REPORT_ID, reportId)
//            .putExtra(EXTRA_FOOD_ID, foodId)
//            .putExtra(EXTRA_FOOD_NAME, foodName)
//    }
//
//    fun openScannerIntent(): Intent {
//        return internalIntent("com.sv.calorieintakeapps.feature_scanner")
//    }
//}