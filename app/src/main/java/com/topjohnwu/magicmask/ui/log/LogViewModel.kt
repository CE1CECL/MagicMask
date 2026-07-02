package com.topjohnwu.magicmask.ui.log

import android.system.Os
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.topjohnwu.magicmask.BR
import com.topjohnwu.magicmask.BuildConfig
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.arch.AsyncLoadViewModel
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.repository.LogRepository
import com.topjohnwu.magicmask.core.utils.MediaStoreUtils
import com.topjohnwu.magicmask.core.utils.MediaStoreUtils.outputStream
import com.topjohnwu.magicmask.databinding.DiffRvItemList
import com.topjohnwu.magicmask.databinding.bindExtra
import com.topjohnwu.magicmask.databinding.set
import com.topjohnwu.magicmask.events.SnackbarEvent
import com.topjohnwu.magicmask.ktx.timeFormatStandard
import com.topjohnwu.magicmask.ktx.toTime
import com.topjohnwu.magicmask.view.TextItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream

class LogViewModel(
    private val repo: LogRepository
) : AsyncLoadViewModel() {

    // --- empty view

    val itemEmpty = TextItem(R.string.log_data_none)
    val itemMagicMaskEmpty = TextItem(R.string.log_data_magicmask_none)

    // --- su log

    val items = DiffRvItemList<LogRvItem>()
    val extraBindings = bindExtra {
        it.put(BR.viewModel, this)
    }

    // --- magicmask log
    @get:Bindable
    var consoleText = " "
        set(value) = set(value, field, { field = it }, BR.consoleText)

    override suspend fun doLoadWork() {
        consoleText = repo.fetchMagicMaskLogs()
        val (suLogs, diff) = withContext(Dispatchers.Default) {
            val suLogs = repo.fetchSuLogs().map { LogRvItem(it) }
            suLogs to items.calculateDiff(suLogs)
        }
        items.firstOrNull()?.isTop = false
        items.lastOrNull()?.isBottom = false
        items.update(suLogs, diff)
        items.firstOrNull()?.isTop = true
        items.lastOrNull()?.isBottom = true
    }

    fun saveMagicMaskLog() = withExternalRW {
        viewModelScope.launch(Dispatchers.IO) {
            val filename = "magicmask_log_%s.log".format(
                System.currentTimeMillis().toTime(timeFormatStandard))
            val logFile = MediaStoreUtils.getFile(filename, true)
            logFile.uri.outputStream().bufferedWriter().use { file ->
                file.write("---Detected Device Info---\n\n")
                file.write("isAB=${Info.isAB}\n")
                file.write("isSAR=${Info.isSAR}\n")
                file.write("ramdisk=${Info.ramdisk}\n")
                val uname = Os.uname()
                file.write("kernel=${uname.sysname} ${uname.machine} ${uname.release} ${uname.version}\n")

                file.write("\n\n---System Properties---\n\n")
                ProcessBuilder("getprop").start()
                    .inputStream.reader().use { it.copyTo(file) }

                file.write("\n\n---Environment Variables---\n\n")
                System.getenv().forEach { (key, value) -> file.write("${key}=${value}\n") }

                file.write("\n\n---System MountInfo---\n\n")
                FileInputStream("/proc/self/mountinfo").reader().use { it.copyTo(file) }

                file.write("\n---MagicMask Logs---\n")
                file.write("${Info.env.versionString} (${Info.env.versionCode})\n\n")
                if (Info.env.isActive) file.write(consoleText)

                file.write("\n---Manager Logs---\n")
                file.write("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})\n\n")
                ProcessBuilder("logcat", "-d").start()
                    .inputStream.reader().use { it.copyTo(file) }
            }
            SnackbarEvent(logFile.toString()).publish()
        }
    }

    fun clearMagicMaskLog() = repo.clearMagicMaskLogs {
        SnackbarEvent(R.string.logs_cleared).publish()
        startLoading()
    }

    fun clearLog() = viewModelScope.launch {
        repo.clearLogs()
        SnackbarEvent(R.string.logs_cleared).publish()
        startLoading()
    }
}
