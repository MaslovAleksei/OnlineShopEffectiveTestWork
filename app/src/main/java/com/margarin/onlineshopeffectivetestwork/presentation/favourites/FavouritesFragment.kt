package com.margarin.onlineshopeffectivetestwork.presentation.favourites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentFavouritesBinding
import com.margarin.onlineshopeffectivetestwork.presentation.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.presentation.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.presentation.adapter.ProductAdapter
import com.margarin.onlineshopeffectivetestwork.utils.replaceFragment
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
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        observeViewModel()
        viewModel.sendEvent(FavouritesEvent.GetFavouriteList)
        setOnclickListeners()
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
                        is FavouritesState.Content -> {
                            adapter.submitList(it.products)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                        }

                        is FavouritesState.NoItems -> {
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                        }

                        is FavouritesState.Initial -> {}
                    }
                }
        }
    }

    private fun setOnclickListeners() {
        adapter.onProductItemClick = {
            replaceFragment(DetailsFragment.newInstance(it))
        }
        adapter.onAddToFavouriteClick = {
            viewModel.sendEvent(FavouritesEvent.ChangeFavouriteStatus(it))
        }
        binding.bBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}