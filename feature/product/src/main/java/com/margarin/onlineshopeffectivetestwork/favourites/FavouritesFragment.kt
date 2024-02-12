package com.margarin.onlineshopeffectivetestwork.favourites

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.margarin.core.R.*
import com.margarin.onlineshopeffectivetestwork.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.adapter.ProductAdapter
import com.margarin.onlineshopeffectivetestwork.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.di.ProductComponentProvider
import com.margarin.onlineshopeffectivetestwork.replaceFragment
import com.margarin.product.databinding.FragmentFavouritesBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouritesViewModel::class.java]
    }

    private var _binding: FragmentFavouritesBinding? = null
    private val binding: FragmentFavouritesBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    private lateinit var adapter: ProductAdapter

    private var containerId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as ProductComponentProvider)
            .getProductComponent()
            .injectFavouritesFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        container?.let { containerId = it.id }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        observeViewModel()
        viewModel.sendEvent(FavouritesEvent.GetFavouriteList)
        setOnClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun configureRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ProductAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.animation = null
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is FavouritesState.Favorites -> {
                            adapter.submitList(it.products)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                            setTabsState(true)
                        }

                        FavouritesState.Brands -> {
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            setTabsState(false)
                        }

                        is FavouritesState.NoItems -> {
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                            setTabsState(true)
                        }

                        is FavouritesState.Initial -> {}
                    }
                }
        }
    }

    private fun setOnClickListeners() {
        adapter.onProductItemClick = {
            replaceFragment(containerId, DetailsFragment.newInstance(it))
        }
        adapter.onAddToFavouriteClick = {
            viewModel.sendEvent(FavouritesEvent.ChangeFavouriteStatus(it))
        }
        binding.bBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.cardProducts.setOnClickListener {
            viewModel.sendEvent(FavouritesEvent.GetFavouriteList)
        }
        binding.cardBrands.setOnClickListener {
            viewModel.sendEvent(FavouritesEvent.GetBrandsList)
        }
    }

    private fun setTabsState(isDefault: Boolean) {
        when(isDefault) {
            true -> {
                binding.cardProducts.setBackgroundColor(requireContext()
                    .getColor(color.white))
                binding.tvProducts.setTextColor(requireContext()
                    .getColor(color.black))
                binding.tvProducts.typeface = Typeface.DEFAULT_BOLD
                binding.cardBrands.setBackgroundColor(requireContext()
                    .getColor(color.light_grey))
                binding.tvBrands.setTextColor(requireContext()
                    .getColor(color.medium_grey))
                binding.tvBrands.typeface = Typeface.DEFAULT
            }
            false -> {
                binding.cardProducts.setBackgroundColor(requireContext()
                    .getColor(color.light_grey))
                binding.tvProducts.setTextColor(requireContext()
                    .getColor(color.medium_grey))
                binding.tvProducts.typeface = Typeface.DEFAULT
                binding.cardBrands.setBackgroundColor(requireContext()
                    .getColor(color.white))
                binding.tvBrands.setTextColor(requireContext()
                    .getColor(color.black))
                binding.tvBrands.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }
}