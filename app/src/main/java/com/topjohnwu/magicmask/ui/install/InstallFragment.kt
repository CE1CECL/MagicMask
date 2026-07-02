package com.topjohnwu.magicmask.ui.install

import com.topjohnwu.magicmask.R
import com.topjohnwu.magicmask.arch.BaseFragment
import com.topjohnwu.magicmask.arch.viewModel
import com.topjohnwu.magicmask.databinding.FragmentInstallMd2Binding

class InstallFragment : BaseFragment<FragmentInstallMd2Binding>() {

    override val layoutRes = R.layout.fragment_install_md2
    override val viewModel by viewModel<InstallViewModel>()

    override fun onStart() {
        super.onStart()
        requireActivity().setTitle(R.string.install)
    }
}
