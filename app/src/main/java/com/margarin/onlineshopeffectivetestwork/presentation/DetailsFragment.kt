package com.margarin.onlineshopeffectivetestwork.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentDetailsBinding
import com.margarin.onlineshopeffectivetestwork.domain.model.Product

class DetailsFragment : Fragment() {
    private var product: Product? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("Binding == null")



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
        parseProductInfo()
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
            tvAvailable.text = product?.available.toString()
            tvRating.text = product?.rating.toString()
            tvFeedbackCount.text = product?.count.toString()
            bToBrand.text = product?.title
            tvDescription.text = product?.description
            tvCompound.text = product?.ingredients


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