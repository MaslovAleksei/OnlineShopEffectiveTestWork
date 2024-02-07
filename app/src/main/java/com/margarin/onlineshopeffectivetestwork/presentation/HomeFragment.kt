package com.margarin.onlineshopeffectivetestwork.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    private val component by lazy {
        (requireActivity().application as ShopApp)
    }

    override fun onAttach(context: Context) {
        component.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            viewModel.remove()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}