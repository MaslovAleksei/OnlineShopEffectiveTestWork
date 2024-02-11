package com.margarin.onlineshopeffectivetestwork.presentation.profile

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
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentProfileBinding
import com.margarin.onlineshopeffectivetestwork.presentation.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.presentation.createStringCountOfFavourites
import com.margarin.onlineshopeffectivetestwork.presentation.favourites.FavouritesFragment
import com.margarin.onlineshopeffectivetestwork.utils.replaceFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }


    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    private val component by lazy {
        (requireActivity().application as ShopApp)
    }

    override fun onAttach(context: Context) {
        component.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
                                    val phoneNumber = "+${it.profile.phoneNumber}"
                                    profileNumber.text = phoneNumber
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
            replaceFragment(FavouritesFragment())
        }
    }

}