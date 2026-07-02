package com.topjohnwu.magicmask.events.dialog

import androidx.lifecycle.lifecycleScope
import com.topjohnwu.magicmask.BuildConfig
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.base.BaseActivity
import com.topjohnwu.magicmask.core.tasks.MagicMaskInstaller
import com.topjohnwu.magicmask.ui.home.HomeViewModel
import com.topjohnwu.magicmask.view.MagicMaskDialog
import kotlinx.coroutines.launch

class EnvFixDialog(private val vm: HomeViewModel) : DialogEvent() {

    override fun build(dialog: MagicMaskDialog) {
        dialog.apply {
            setTitle(R.string.env_fix_title)
            setMessage(R.string.env_fix_msg)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = android.R.string.ok
                doNotDismiss = true
                onClick {
                    dialog.apply {
                        setTitle(R.string.setup_title)
                        setMessage(R.string.setup_msg)
                        resetButtons()
                        setCancelable(false)
                    }
                    (dialog.ownerActivity as BaseActivity).lifecycleScope.launch {
                        MagicMaskInstaller.FixEnv {
                            dialog.dismiss()
                        }.exec()
                    }
                }
            }
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = android.R.string.cancel
            }
        }

        if (Info.env.versionCode != BuildConfig.VERSION_CODE ||
            Info.env.versionString != BuildConfig.VERSION_NAME) {
            dialog.setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = android.R.string.ok
                onClick {
                    vm.onMagicMaskPressed()
                    dialog.dismiss()
                }
            }
        }
    }
}
