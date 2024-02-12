package com.margarin.onlineshopeffectivetestwork.utils.validation

import com.margarin.onlineshopeffectivetestwork.R
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.ValidateResult

class PhoneValidator(private val phoneNumber: String) : BaseValidator() {

    override fun validate(): ValidateResult {
        val isValid = phoneNumber.length == 10
        return ValidateResult(
            isValid,
            if (isValid) {
                R.string.text_validation_success
            } else {
                R.string.please_enter_full_phone_number
            }
        )
    }
}