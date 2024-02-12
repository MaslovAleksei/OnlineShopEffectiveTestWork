package com.margarin.onlineshopeffectivetestwork.details

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.margarin.core.R.drawable
import com.margarin.onlineshopeffectivetestwork.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.createStringAvailableForOrder
import com.margarin.onlineshopeffectivetestwork.createStringCountOfFeedback
import com.margarin.onlineshopeffectivetestwork.details.adapter.DetailsAdapter
import com.margarin.onlineshopeffectivetestwork.di.ProductComponentProvider
import com.margarin.onlineshopeffectivetestwork.model.Product
import com.margarin.product.R
import com.margarin.product.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
    }

    private var product: Product? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    private lateinit var adapter: DetailsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as ProductComponentProvider)
            .getProductComponent()
            .injectDetailsFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(ITEM_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product?.let { viewModel.sendEvent(DetailsEvent.ObserveFavouriteStatus(it.id)) }
        parseProductInfo()
        setOnClickListeners()
        observeViewModel()
        configureRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun parseProductInfo() {
        with(binding) {
            val imageList = ArrayList<SlideModel>()
            product?.imageResId?.forEach {
                imageList.add(SlideModel(imagePath = it))
            }
            itemImageSlider.setImageList(imageList)
            tvTitle.text = product?.title
            tvSubtitle.text = product?.subtitle
            tvAvailable.text = product?.available?.createStringAvailableForOrder()
            if (product?.count == 0) llRating.visibility = View.GONE
            ratingBar.rating = product?.rating ?: 0f
            tvRating.text = product?.rating.toString()
            tvFeedbackCount.text = product?.count?.createStringCountOfFeedback()
            val newPrice = "${product?.priceWithDiscount} ₽"
            tvNewPrice.text = newPrice
            tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            val oldPrice = "${product?.price} ₽"
            tvOldPrice.text = oldPrice
            val discount = "-${product?.discount.toString()}%"
            tvDiscount.text = discount
            tvToBrand.text = product?.title
            tvDescription.text = product?.description
            tvCompound.text = product?.ingredients
            tvOnButtonPrice.text = newPrice
            tvOnButtonOldPrice.paintFlags =
                tvOnButtonOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvOnButtonOldPrice.text = oldPrice
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.apply {
                        when (it) {
                            is DetailsState.Details -> {
                                val iconLike = if (it.isFavourite) {
                                    drawable.ic_like_filled
                                } else {
                                    drawable.ic_like_border
                                }
                                binding.bLike.setImageResource(iconLike)
                            }

                            DetailsState.Initial -> {}
                        }
                    }
                }
        }
    }

    private fun configureRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = DetailsAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(product?.info)
    }

    private fun setOnClickListeners() {
        with(binding) {
            bBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            bLike.setOnClickListener {
                product?.let {
                    viewModel.sendEvent(
                        DetailsEvent.ChangeFavouriteStatus(
                            it
                        )
                    )
                }
            }
            bHideDescription.setOnClickListener {
                if (tvDescription.visibility == View.VISIBLE) {
                    tvDescription.visibility = View.GONE
                    bToBrand.visibility = View.GONE
                    bHideDescription.text = getString(R.string.more)
                } else {
                    tvDescription.visibility = View.VISIBLE
                    bToBrand.visibility = View.VISIBLE
                    bHideDescription.text = getString(R.string.hide)
                }
            }
            bMore.setOnClickListener {
                if (tvCompound.maxLines != Int.MAX_VALUE) {
                    tvCompound.maxLines = Int.MAX_VALUE
                    bMore.text = getString(R.string.hide)
                } else {
                    tvCompound.maxLines = 2
                    bMore.text = getString(R.string.more)
                }
            }
        }
    }

    companion object {
        private const val ITEM_PARAM = "ITEM_PARAM"

        fun newInstance(product: Product) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM_PARAM, product)
                }
            }
    }
}