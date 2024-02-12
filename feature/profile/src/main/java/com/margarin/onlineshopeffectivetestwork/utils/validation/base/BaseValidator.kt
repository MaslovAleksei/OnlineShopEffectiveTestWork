package com.margarin.onlineshopeffectivetestwork.utils.validation.base

import com.margarin.profile.R

abstract class BaseValidator : Validation {

    companion object {

        fun validate(vararg validators: Validation): ValidateResult {
            validators.forEach {
                val result = it.validate()
                if (!result.isSuccess)
                    return result
            }
            return ValidateResult(true, R.string.text_validation_success)
        }
    }
}