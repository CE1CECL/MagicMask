package com.topjohnwu.magicmask.core.repository

import com.topjohnwu.magicmask.core.Const
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.data.SuLogDao
import com.topjohnwu.magicmask.core.model.su.SuLog
import com.topjohnwu.magicmask.ktx.await
import com.topjohnwu.superuser.Shell


class LogRepository(
    private val logDao: SuLogDao
) {

    suspend fun fetchSuLogs() = logDao.fetchAll()

    suspend fun fetchMagicMaskLogs(): String {
        val list = object : AbstractMutableList<String>() {
            val buf = StringBuilder()
            override val size get() = 0
            override fun get(index: Int): String = ""
            override fun removeAt(index: Int): String = ""
            override fun set(index: Int, element: String): String = ""
            override fun add(index: Int, element: String) {
                if (element.isNotEmpty()) {
                    buf.append(element)
                    buf.append('\n')
                }
            }
        }
        if (Info.env.isActive) {
            Shell.cmd("cat ${Const.MAGICMASK_LOG} || logcat -d -s MagicMask").to(list).await()
        } else {
            Shell.cmd("logcat -d").to(list).await()
        }
        return list.buf.toString()
    }

    suspend fun clearLogs() = logDao.deleteAll()

    fun clearMagicMaskLogs(cb: (Shell.Result) -> Unit) =
        Shell.cmd("echo -n > ${Const.MAGICMASK_LOG}").submit(cb)

    suspend fun insert(log: SuLog) = logDao.insert(log)

}
