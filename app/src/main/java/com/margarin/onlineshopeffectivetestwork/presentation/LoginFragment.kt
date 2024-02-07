package com.margarin.onlineshopeffectivetestwork.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.databinding.FragmentLoginBinding
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile
import com.margarin.onlineshopeffectivetestwork.utils.validation.EmptyValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.NameValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.PhoneValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }


    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {
        binding.bSignIn.setOnClickListener {

            val name = binding.inputEditTextFirstname.text.toString()
            val lastName = binding.inputEditTextLastname.text.toString()
            val phoneNumber = binding.inputEditTextPassword.text.toString()


            val nameValidations = BaseValidator.validate(
                EmptyValidator(name), NameValidator(name)
            )
            binding.inputLayoutFirstname.error =
                if (!nameValidations.isSuccess) getString(nameValidations.message) else null


            val lastNameValidations = BaseValidator.validate(
                EmptyValidator(lastName), NameValidator(lastName)
            )
            binding.inputLayoutLastname.error =
                if (!lastNameValidations.isSuccess) getString(lastNameValidations.message) else null


            val phoneNumberValidations = BaseValidator.validate(
                EmptyValidator(phoneNumber), PhoneValidator(phoneNumber)
            )
            binding.inputLayoutPhoneNumber.error =
                if (!phoneNumberValidations.isSuccess) {
                    getString(phoneNumberValidations.message)
                } else {
                    null
                }
            if (nameValidations.isSuccess && lastNameValidations.isSuccess && phoneNumberValidations.isSuccess) {
                viewModel.add(Profile(name, lastName, phoneNumber))
            }
        }
    }
}