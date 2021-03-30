package com.m7.orcasforecast.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.m7.orcasforecast.R
import com.m7.orcasforecast.databinding.ActivityForecastBinding
import com.m7.orcasforecast.ui.LoadingDialog
import com.m7.orcasforecast.util.Logger
import java.lang.IllegalStateException

abstract class BaseActivity : AppCompatActivity() {

    private val loadingDialog = LoadingDialog()
    private var msgSnake: Snackbar? = null
    private var errSnake: Snackbar? = null

    lateinit var viewBinding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityForecastBinding>(this, R.layout.activity_forecast)
            .apply {
                viewBinding = this
                initSnakes(root)
            }
    }

    private fun initSnakes(root: View) {
        msgSnake = Snackbar.make(root, "", Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(getColor(R.color.baby_blue))
            setTextColor(Color.WHITE)
        }
        errSnake = Snackbar.make(root, "", Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(Color.RED)
            setTextColor(Color.WHITE)
        }
    }

    fun displayMessage(msg: String) {
        Logger.d("snack", "msg snake -> $msg")

        try {
            msgSnake?.setText(msg)?.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_LONG).show()
        }
    }

    fun displayError(errMsg: String) {
        Logger.d("snack", "err snake -> $errMsg")

        try {
            errSnake?.setText(errMsg)?.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show()
        }
    }

    fun displayLoading(display: Boolean = true) {
        Logger.d("loading", "display -> $display")

        if (display) {
            supportFragmentManager.executePendingTransactions()
            if (!loadingDialog.isAdded)
                loadingDialog.show(supportFragmentManager, loadingDialog.TAG)
        } else {
            try {
                loadingDialog.dismiss()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    fun dismissKeypad(activity: Activity) {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }
}