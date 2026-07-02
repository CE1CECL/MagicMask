package com.topjohnwu.magicmask.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.core.Config
import com.topjohnwu.magicmask.core.Const
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.di.AppContext
import com.topjohnwu.superuser.internal.UiThreadHandler

object Utils {

    fun toast(msg: CharSequence, duration: Int) {
        UiThreadHandler.run { Toast.makeText(AppContext, msg, duration).show() }
    }

    fun toast(resId: Int, duration: Int) {
        UiThreadHandler.run { Toast.makeText(AppContext, resId, duration).show() }
    }

    fun showSuperUser(): Boolean {
        return Info.env.isActive && (Const.USER_ID == 0
                || Config.suMultiuserMode == Config.Value.MULTIUSER_MODE_USER)
    }

    fun openLink(context: Context, link: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, link)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            toast(R.string.open_link_failed_toast, Toast.LENGTH_SHORT)
        }
    }
}
