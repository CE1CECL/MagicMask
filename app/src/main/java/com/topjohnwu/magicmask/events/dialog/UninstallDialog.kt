package com.topjohnwu.magicmask.events.dialog

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.arch.NavigationActivity
import com.topjohnwu.magicmask.ui.flash.FlashFragment
import com.topjohnwu.magicmask.utils.Utils
import com.topjohnwu.magicmask.view.MagicMaskDialog
import com.topjohnwu.superuser.Shell

class UninstallDialog : DialogEvent() {

    override fun build(dialog: MagicMaskDialog) {
        dialog.apply {
            setTitle(R.string.uninstall_magicmask_title)
            setMessage(R.string.uninstall_magicmask_msg)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = R.string.restore_img
                onClick { restore(dialog.context) }
            }
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = R.string.complete_uninstall
                onClick { completeUninstall(dialog) }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun restore(context: Context) {
        val dialog = ProgressDialog(context).apply {
            setMessage(context.getString(R.string.restore_img_msg))
            show()
        }

        Shell.cmd("restore_imgs").submit { result ->
            dialog.dismiss()
            if (result.isSuccess) {
                Utils.toast(R.string.restore_done, Toast.LENGTH_SHORT)
            } else {
                Utils.toast(R.string.restore_fail, Toast.LENGTH_LONG)
            }
        }
    }

    private fun completeUninstall(dialog: MagicMaskDialog) {
        (dialog.ownerActivity as NavigationActivity<*>)
            .navigation.navigate(FlashFragment.uninstall())
    }

}
