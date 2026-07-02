package com.topjohnwu.magicmask.events.dialog

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.view.MagicMaskDialog

class SuperuserRevokeDialog(
    builder: Builder.() -> Unit
) : DialogEvent() {

    private val callbacks = Builder().apply(builder)

    override fun build(dialog: MagicMaskDialog) {
        dialog.apply {
            setTitle(R.string.su_revoke_title)
            setMessage(R.string.su_revoke_msg, callbacks.appName)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = android.R.string.ok
                onClick { callbacks.listenerOnSuccess() }
            }
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = android.R.string.cancel
            }
        }
    }

    inner class Builder internal constructor() {
        var appName: String = ""

        internal var listenerOnSuccess: GenericDialogListener = {}

        fun onSuccess(listener: GenericDialogListener) {
            listenerOnSuccess = listener
        }
    }
}
