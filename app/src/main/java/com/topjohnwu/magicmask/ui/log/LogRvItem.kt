package com.topjohnwu.magicmask.ui.log

import androidx.databinding.Bindable
import com.topjohnwu.magicmask.BR
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.core.model.su.SuLog
import com.topjohnwu.magicmask.databinding.ObservableDiffRvItem
import com.topjohnwu.magicmask.databinding.RvContainer
import com.topjohnwu.magicmask.databinding.set
import com.topjohnwu.magicmask.ktx.timeDateFormat
import com.topjohnwu.magicmask.ktx.toTime

class LogRvItem(
    override val item: SuLog
) : ObservableDiffRvItem<LogRvItem>(), RvContainer<SuLog> {

    override val layoutRes = R.layout.item_log_access_md2

    val date = item.time.toTime(timeDateFormat)

    @get:Bindable
    var isTop = false
        set(value) = set(value, field, { field = it }, BR.top)

    @get:Bindable
    var isBottom = false
        set(value) = set(value, field, { field = it }, BR.bottom)

    override fun itemSameAs(other: LogRvItem) = item.appName == other.item.appName
}
