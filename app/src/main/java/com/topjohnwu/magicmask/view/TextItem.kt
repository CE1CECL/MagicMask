package com.topjohnwu.magicmask.view

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.databinding.DiffRvItem

class TextItem(val text: Int) : DiffRvItem<TextItem>() {
    override val layoutRes = R.layout.item_text

    override fun contentSameAs(other: TextItem) = text == other.text
}
