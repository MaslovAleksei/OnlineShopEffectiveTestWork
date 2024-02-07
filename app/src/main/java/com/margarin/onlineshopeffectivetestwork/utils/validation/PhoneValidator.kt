package com.margarin.onlineshopeffectivetestwork.utils.validation

import com.margarin.onlineshopeffectivetestwork.R
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.ValidateResult

class PhoneValidator(val phoneNumber: String) : BaseValidator() {

    override fun validate(): ValidateResult {

        return ValidateResult(true, R.string.text_validation_success)
    }

}