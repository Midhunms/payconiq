package com.payconiq.app.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View

object ActivityUtils {


    fun startActivityWithTransition(
        mActivity: Activity,
        aClass: Class<*>,
        mView: View,
        sharedElementName: String,
        bundle: Bundle?,
        finishActivity: Boolean,
    ) {

        try {
            val intent = Intent(mActivity, aClass)
            val options =
                ActivityOptions.makeSceneTransitionAnimation(mActivity, mView, sharedElementName)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            mActivity.startActivity(intent, options.toBundle())
            if (finishActivity) {
                mActivity.finishAfterTransition()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

}