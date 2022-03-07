package com.eventgithubdemo.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.eventgithubdemo.R
import com.eventgithubdemo.databinding.FragmentDetailEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailEventFragment : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding

    private val safeArgs: DetailEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_event, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.event = safeArgs.event
    }

}
