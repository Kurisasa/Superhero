package com.kurisani.superhero.activity

import android.os.Bundle
import android.widget.PopupWindow
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.R

abstract class BaseActivity: AppCompatActivity() {

 //   @Inject lateinit var navigation: Navigation

    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out)
        SuperheroApplication.instance!!.getDependencyComponent().inject(this)
    }

    @CallSuper
    public override fun onDestroy() {
        super.onDestroy()
    }

    fun getPopupWindow(): PopupWindow? {
        return popupWindow
    }

    fun setPopupWindow(popupWindow: PopupWindow?) {
        this.popupWindow = popupWindow
    }

    fun dismissPopupWindow() {
        if (popupWindow != null) {
            popupWindow!!.dismiss()
            popupWindow = null
        }
    }

    fun isPopupWindowShowing(): Boolean {
        return popupWindow != null && popupWindow!!.isShowing
    }
}