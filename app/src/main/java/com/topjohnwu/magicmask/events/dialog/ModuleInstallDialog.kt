package com.topjohnwu.magicmask.events.dialog

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.core.di.ServiceLocator
import com.topjohnwu.magicmask.core.download.Action
import com.topjohnwu.magicmask.core.download.DownloadService
import com.topjohnwu.magicmask.core.download.Subject
import com.topjohnwu.magicmask.core.model.module.OnlineModule
import com.topjohnwu.magicmask.view.MagicMaskDialog

class ModuleInstallDialog(private val item: OnlineModule) : MarkDownDialog() {

    private val svc get() = ServiceLocator.networkService

    override suspend fun getMarkdownText(): String {
        val str = svc.fetchString(item.changelog)
        return if (str.length > 1000) str.substring(0, 1000) else str
    }

    override fun build(dialog: MagicMaskDialog) {
        super.build(dialog)
        dialog.apply {

            fun download(install: Boolean) {
                val action = if (install) Action.Flash else Action.Download
                val subject = Subject.Module(item, action)
                DownloadService.start(activity, subject)
            }

            val title = context.getString(R.string.repo_install_title,
                item.name, item.version, item.versionCode)

            setTitle(title)
            setCancelable(true)
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = R.string.download
                onClick { download(false) }
            }
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = R.string.install
                onClick { download(true) }
            }
            setButton(MagicMaskDialog.ButtonType.NEUTRAL) {
                text = android.R.string.cancel
            }
        }
    }

}
