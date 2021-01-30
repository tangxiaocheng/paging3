package com.adrena.commerce.paging3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.databinding.FragmentMovieListBinding

class MovieFlowRemoteFragment : Fragment() {

    private lateinit var mBinding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieListBinding.inflate(inflater, container, false)

        val view = mBinding.root

        activity?.title = getString(R.string.kotlin_flow_with_remote_mediator)

        return view
    }
}