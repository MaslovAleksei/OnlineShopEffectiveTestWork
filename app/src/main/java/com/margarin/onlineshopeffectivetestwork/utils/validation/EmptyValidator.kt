package com.margarin.onlineshopeffectivetestwork.utils.validation

import com.margarin.onlineshopeffectivetestwork.R
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.ValidateResult

class EmptyValidator(val input: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid = input.isNotEmpty()
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success else R.string.text_validation_error_empty_field
        )
    }
}