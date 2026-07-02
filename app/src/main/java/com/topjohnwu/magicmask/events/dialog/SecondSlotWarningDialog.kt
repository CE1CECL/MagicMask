package com.topjohnwu.magicmask.events.dialog

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.view.MagicMaskDialog

class SecondSlotWarningDialog : DialogEvent() {

    override fun build(dialog: MagicMaskDialog) {
        dialog.apply {
            setTitle(android.R.string.dialog_alert_title)
            setMessage(R.string.install_inactive_slot_msg)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = android.R.string.ok
            }
            setCancelable(true)
        }
    }
}
