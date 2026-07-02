package com.topjohnwu.magicmask.events.dialog

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.arch.UIActivity
import com.topjohnwu.magicmask.core.Config
import com.topjohnwu.magicmask.view.MagicMaskDialog

class DarkThemeDialog : DialogEvent() {

    override fun build(dialog: MagicMaskDialog) {
        val activity = dialog.ownerActivity!!
        dialog.apply {
            setTitle(R.string.settings_dark_mode_title)
            setMessage(R.string.settings_dark_mode_message)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = R.string.settings_dark_mode_light
                icon = R.drawable.ic_day
                onClick { selectTheme(AppCompatDelegate.MODE_NIGHT_NO, activity) }
            }
            setButton(MagicMaskDialog.ButtonType.NEUTRAL) {
                text = R.string.settings_dark_mode_system
                icon = R.drawable.ic_day_night
                onClick { selectTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, activity) }
            }
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = R.string.settings_dark_mode_dark
                icon = R.drawable.ic_night
                onClick { selectTheme(AppCompatDelegate.MODE_NIGHT_YES, activity) }
            }
        }
    }

    private fun selectTheme(mode: Int, activity: Activity) {
        Config.darkTheme = mode
        (activity as UIActivity<*>).delegate.localNightMode = mode
    }
}
