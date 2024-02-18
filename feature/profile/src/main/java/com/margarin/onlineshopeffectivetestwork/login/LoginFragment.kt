package com.margarin.onlineshopeffectivetestwork.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.margarin.onlineshopeffectivetestwork.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.di.ProfileComponentProvider
import com.margarin.onlineshopeffectivetestwork.utils.validation.NameValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.PhoneValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import com.margarin.profile.R
import com.margarin.profile.databinding.FragmentLoginBinding
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as ProfileComponentProvider)
            .getProfileComponent()
            .injectLoginFragment(this)
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
        setTextWatchers()

        binding.inputEditTextPhoneNumber.setOnFocusChangeListener { _, isFocused ->
            binding.inputLayoutPhoneNumber.hint = if (isFocused) {
                getString(R.string.phone_mask)
            } else {
                getString(R.string.phone_number)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {

        binding.bSignIn.setOnClickListener {
            val firstName = binding.inputEditTextFirstname.text.toString().replaceFirstChar {
                it.uppercase()
            }
            val lastName = binding.inputEditTextLastname.text.toString().replaceFirstChar {
                it.uppercase()
            }
            val phoneNumber = "7${binding.inputEditTextPhoneNumber.text.toString()}"

            viewModel.addProfile(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        }
    }

    private fun setTextWatchers() {
        setTextNameWatcher()
        setTextLastNameWatcher()
        setPhoneNumberWatcher()
    }

    private fun setTextNameWatcher() {
        val textNameWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val string = charSequence.toString()
                if (string.isNotEmpty()) {
                    val nameValidations = BaseValidator.validate(NameValidator(string))
                    binding.inputLayoutFirstname.error =
                        if (!nameValidations.isSuccess) {
                            getString(nameValidations.message)
                        } else {
                            null
                        }
                } else {
                    binding.inputLayoutFirstname.error = null
                }
                checkAllFieldsValidated()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        binding.inputEditTextFirstname.addTextChangedListener(textNameWatcher)
    }

    private fun setTextLastNameWatcher() {
        val textLastNameWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val string = charSequence.toString()
                if (string.isNotEmpty()) {

                    val nameValidations = BaseValidator.validate(NameValidator(string))
                    binding.inputLayoutLastname.error =
                        if (!nameValidations.isSuccess) {
                            getString(nameValidations.message)
                        } else {
                            null
                        }
                } else {
                    binding.inputLayoutLastname.error = null
                }
                checkAllFieldsValidated()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.inputEditTextLastname.addTextChangedListener(textLastNameWatcher)
    }

    private fun setPhoneNumberWatcher() {
        val textPhoneNumberWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var string = charSequence.toString()
                if (string.isNotEmpty()) {

                    if (string.startsWith("+") || string.startsWith("7")) {
                        string = string.drop(1)
                        binding.inputEditTextPhoneNumber.setText(string)
                        return
                    }
                    if (string.startsWith("+7") || string.startsWith("(7")) {
                        string = string.drop(2)
                        binding.inputEditTextPhoneNumber.setText(string)
                        return
                    }

                    val phoneNumberValidations = BaseValidator.validate(PhoneValidator(string))
                    binding.inputLayoutPhoneNumber.error =
                        if (!phoneNumberValidations.isSuccess) {
                            getString(phoneNumberValidations.message)
                        } else {
                            null
                        }
                } else {
                    binding.inputLayoutPhoneNumber.error = null
                }
                checkAllFieldsValidated()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.inputEditTextPhoneNumber.addTextChangedListener(textPhoneNumberWatcher)
    }

    private fun checkAllFieldsValidated() {
        with(binding) {
            bSignIn.isEnabled =
                inputLayoutFirstname.error == null &&
                        inputLayoutLastname.error == null &&
                        inputLayoutPhoneNumber.error == null &&
                        inputEditTextFirstname.text?.isNotEmpty() == true &&
                        inputEditTextLastname.text?.isNotEmpty() == true &&
                        inputEditTextPhoneNumber.text?.isNotEmpty() == true
        }
    }
}