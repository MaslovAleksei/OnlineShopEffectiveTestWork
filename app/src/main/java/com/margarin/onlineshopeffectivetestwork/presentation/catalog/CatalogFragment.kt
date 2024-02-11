package com.margarin.onlineshopeffectivetestwork.presentation.catalog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.margarin.onlineshopeffectivetestwork.R
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentCatalogBinding
import com.margarin.onlineshopeffectivetestwork.presentation.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.presentation.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.presentation.adapter.ProductAdapter
import com.margarin.onlineshopeffectivetestwork.utils.replaceFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class CatalogFragment : Fragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CatalogViewModel::class.java]
    }

    private var _binding: FragmentCatalogBinding? = null
    private val binding: FragmentCatalogBinding
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
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sendEvent(CatalogEvent.GetProductList)
        configureSpinner()
        configureRecyclerView()
        observeViewModel()
        setOnclickListeners()
        configureChips()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (parent?.getItemAtPosition(position)) {
            SPINNER_ITEM_BY_RATING -> {
                viewModel.sendEvent(CatalogEvent.SortListByRating)
                binding.recyclerView.smoothScrollToPosition(0)
            }

            SPINNER_ITEM_BY_PRICE_ASC -> {
                viewModel.sendEvent(CatalogEvent.SortListByAsc)
                binding.recyclerView.smoothScrollToPosition(0)
            }

            SPINNER_ITEM_BY_PRICE_DESC -> {
                viewModel.sendEvent(CatalogEvent.SortListByDesc)
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun configureRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ProductAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun configureSpinner() {
        binding.spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is CatalogState.Content -> {
                            adapter.submitList(it.products)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.tvLoadingError.visibility = View.GONE
                        }

                        is CatalogState.Error -> {
                            binding.recyclerView.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            binding.tvLoadingError.visibility = View.VISIBLE
                        }

                        is CatalogState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.tvLoadingError.visibility = View.GONE
                        }

                        is CatalogState.Initial -> {}
                    }
                }
        }
    }

    private fun configureChips() {

        with(binding) {

            chipAll.setOnClickListener {
                chooseDefaultChip()
            }

            chipFace.apply {
                setOnClickListener {
                    setOnChipClickListener(
                        event = CatalogEvent.FilterByFace,
                        view = it,
                        id = R.id.chip_face
                    )
                }

                setOnCloseIconClickListener {
                    chooseDefaultChip()
                }
            }

            chipBody.apply {
                setOnClickListener {
                    setOnChipClickListener(
                        event = CatalogEvent.FilterByBody,
                        view = it,
                        id = R.id.chip_body
                    )
                }

                setOnCloseIconClickListener {
                    chooseDefaultChip()
                }
            }

            chipMask.apply {
                setOnClickListener {
                    setOnChipClickListener(
                        event = CatalogEvent.FilterByMask,
                        view = it,
                        id = R.id.chip_mask
                    )
                }

                setOnCloseIconClickListener {
                    chooseDefaultChip()
                }
            }

            chipSuntan.setOnClickListener {
                setOnChipClickListener(
                    event = CatalogEvent.FilterBySuntan,
                    view = it,
                    id = R.id.chip_suntan
                )
            }

            chipSuntan.setOnCloseIconClickListener {
                chooseDefaultChip()
            }
        }
    }

    private fun setOnChipClickListener(event: CatalogEvent, view: View, id: Int) {
        hideCloseIcons()
        viewModel.sendEvent(event)
        binding.recyclerView.smoothScrollToPosition(0)
        (view as Chip).isCloseIconVisible = true
        binding.chipGroup.check(id)
    }

    private fun chooseDefaultChip() {
        with(binding) {
            hideCloseIcons()
            chipGroup.check(R.id.chip_all)
            viewModel.sendEvent(CatalogEvent.GetProductList)
            recyclerView.smoothScrollToPosition(0)
            spinner.setSelection(0)
        }
    }

    private fun hideCloseIcons() {
        with(binding) {
            chipFace.isCloseIconVisible = false
            chipBody.isCloseIconVisible = false
            chipSuntan.isCloseIconVisible = false
            chipMask.isCloseIconVisible = false
        }
    }

    private fun setOnclickListeners() {
        adapter.onProductItemClick = {
            replaceFragment(DetailsFragment.newInstance(it))
        }
        adapter.onAddToFavouriteClick = {
            viewModel.sendEvent(CatalogEvent.ChangeFavouriteStatus(it))
        }
    }

    companion object {
        private const val SPINNER_ITEM_BY_RATING = "По популярности"
        private const val SPINNER_ITEM_BY_PRICE_DESC = "По уменьшению цены"
        private const val SPINNER_ITEM_BY_PRICE_ASC = "По увеличению цены"
    }
}