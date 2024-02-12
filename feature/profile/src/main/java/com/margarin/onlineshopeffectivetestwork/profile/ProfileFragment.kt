package com.margarin.onlineshopeffectivetestwork.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.margarin.onlineshopeffectivetestwork.REFERENCE_FAVOURITE_FRAGMENT
import com.margarin.onlineshopeffectivetestwork.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.createStringCountOfFavourites
import com.margarin.onlineshopeffectivetestwork.di.ProfileComponentProvider
import com.margarin.onlineshopeffectivetestwork.formatPhone
import com.margarin.onlineshopeffectivetestwork.replaceFragmentInOtherModule
import com.margarin.profile.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    private var containerId = 0

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as ProfileComponentProvider)
            .getProfileComponent()
            .injectProfileFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        container?.let { containerId = it.id }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(ProfileEvent.GetFavouriteList)
        observeViewModel()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.apply {
                        when (it) {
                            is ProfileState.Favorites -> {
                                with(binding) {
                                    val name = "${it.profile.firstName} ${it.profile.lastName}"
                                    profileName.text = name
                                    profileNumber.text = it.profile.phoneNumber.formatPhone()
                                    tvCount.text =
                                        it.favourites.size.createStringCountOfFavourites()
                                }
                            }

                            ProfileState.Initial -> {}
                        }
                    }
                }
        }
    }

    private fun setOnClickListeners() {
        binding.bExit.setOnClickListener {
            viewModel.sendEvent(ProfileEvent.RemoveProfileUseCase)
        }
        binding.cardFavourites.setOnClickListener {
            replaceFragmentInOtherModule(containerId, REFERENCE_FAVOURITE_FRAGMENT)
        }
    }
}