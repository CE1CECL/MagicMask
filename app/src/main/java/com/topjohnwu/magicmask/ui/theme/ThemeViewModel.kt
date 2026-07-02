package com.topjohnwu.magicmask.ui.theme

import com.topjohnwu.magicmask.arch.BaseViewModel
import com.topjohnwu.magicmask.core.Config
import com.topjohnwu.magicmask.events.RecreateEvent
import com.topjohnwu.magicmask.events.dialog.DarkThemeDialog
import com.topjohnwu.magicmask.view.TappableHeadlineItem

class ThemeViewModel : BaseViewModel(), TappableHeadlineItem.Listener {

    val themeHeadline = TappableHeadlineItem.ThemeMode

    override fun onItemPressed(item: TappableHeadlineItem) = when (item) {
        is TappableHeadlineItem.ThemeMode -> DarkThemeDialog().publish()
    }

    fun saveTheme(theme: Theme) {
        if (!theme.isSelected) {
            Config.themeOrdinal = theme.ordinal
            RecreateEvent().publish()
        }
    }
}
