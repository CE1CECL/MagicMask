package com.topjohnwu.magicmask.events.dialog

import com.topjohnwu.magicmask.arch.ActivityExecutor
import com.topjohnwu.magicmask.arch.UIActivity
import com.topjohnwu.magicmask.arch.ViewEvent
import com.topjohnwu.magicmask.view.MagicMaskDialog

abstract class DialogEvent : ViewEvent(), ActivityExecutor {

    override fun invoke(activity: UIActivity<*>) {
        MagicMaskDialog(activity).apply(this::build).show()
    }

    abstract fun build(dialog: MagicMaskDialog)

}

typealias GenericDialogListener = () -> Unit
