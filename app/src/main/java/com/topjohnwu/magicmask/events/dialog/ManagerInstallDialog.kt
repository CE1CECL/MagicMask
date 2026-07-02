package com.topjohnwu.magicmask.events.dialog

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.di.AppContext
import com.topjohnwu.magicmask.core.di.ServiceLocator
import com.topjohnwu.magicmask.core.download.DownloadService
import com.topjohnwu.magicmask.core.download.Subject
import com.topjohnwu.magicmask.view.MagicMaskDialog
import java.io.File

class ManagerInstallDialog : MarkDownDialog() {

    private val svc get() = ServiceLocator.networkService

    override suspend fun getMarkdownText(): String {
        val text = svc.fetchString(Info.remote.magicmask.note)
        // Cache the changelog
        AppContext.cacheDir.listFiles { _, name -> name.endsWith(".md") }.orEmpty().forEach {
            it.delete()
        }
        File(AppContext.cacheDir, "${Info.remote.magicmask.versionCode}.md").writeText(text)
        return text
    }

    override fun build(dialog: MagicMaskDialog) {
        super.build(dialog)
        dialog.apply {
            setCancelable(true)
            setButton(MagicMaskDialog.ButtonType.POSITIVE) {
                text = R.string.install
                onClick { DownloadService.start(activity, Subject.App()) }
            }
            setButton(MagicMaskDialog.ButtonType.NEGATIVE) {
                text = android.R.string.cancel
            }
        }
    }

}
