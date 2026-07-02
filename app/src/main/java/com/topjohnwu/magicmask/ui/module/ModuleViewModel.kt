package com.topjohnwu.magicmask.ui.module

import android.net.Uri
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.topjohnwu.magicmask.BR
import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.arch.AsyncLoadViewModel
import com.topjohnwu.magicmask.core.Info
import com.topjohnwu.magicmask.core.base.ContentResultCallback
import com.topjohnwu.magicmask.core.model.module.LocalModule
import com.topjohnwu.magicmask.core.model.module.OnlineModule
import com.topjohnwu.magicmask.databinding.DiffRvItemList
import com.topjohnwu.magicmask.databinding.MergeObservableList
import com.topjohnwu.magicmask.databinding.RvItem
import com.topjohnwu.magicmask.databinding.bindExtra
import com.topjohnwu.magicmask.databinding.set
import com.topjohnwu.magicmask.events.GetContentEvent
import com.topjohnwu.magicmask.events.SnackbarEvent
import com.topjohnwu.magicmask.events.dialog.ModuleInstallDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

class ModuleViewModel : AsyncLoadViewModel() {

    val bottomBarBarrierIds = intArrayOf(R.id.module_update, R.id.module_remove)

    private val itemsInstalled = DiffRvItemList<LocalModuleRvItem>()

    val items = MergeObservableList<RvItem>()
    val extraBindings = bindExtra {
        it.put(BR.viewModel, this)
    }

    val data get() = uri

    @get:Bindable
    var loading = true
        private set(value) = set(value, field, { field = it }, BR.loading)

    override suspend fun doLoadWork() {
        loading = true
        val moduleLoaded = Info.env.isActive &&
                withContext(Dispatchers.IO) { LocalModule.loaded() }
        if (moduleLoaded) {
            loadInstalled()
            if (items.isEmpty()) {
                items.insertItem(InstallModule)
                    .insertList(itemsInstalled)
            }
        }
        loading = false
        loadUpdateInfo()
    }

    override fun onNetworkChanged(network: Boolean) = startLoading()

    private suspend fun loadInstalled() {
        val installed = LocalModule.installed().map { LocalModuleRvItem(it) }
        val diff = withContext(Dispatchers.Default) {
            itemsInstalled.calculateDiff(installed)
        }
        itemsInstalled.update(installed, diff)
    }

    private suspend fun loadUpdateInfo() {
        withContext(Dispatchers.IO) {
            itemsInstalled.forEach {
                if (it.item.fetch())
                    it.fetchedUpdateInfo()
            }
        }
    }

    fun downloadPressed(item: OnlineModule?) =
        if (item != null && Info.isConnected.value == true) {
            withExternalRW { ModuleInstallDialog(item).publish() }
        } else {
            SnackbarEvent(R.string.no_connection).publish()
        }

    fun installPressed() = withExternalRW {
        GetContentEvent("application/zip", UriCallback()).publish()
    }

    @Parcelize
    class UriCallback : ContentResultCallback {
        override fun onActivityResult(result: Uri) {
            uri.value = result
        }
    }

    companion object {
        private val uri = MutableLiveData<Uri?>()
    }
}
