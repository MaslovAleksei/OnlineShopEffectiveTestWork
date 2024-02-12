package com.margarin.onlineshopeffectivetestwork.utils.validation

import android.text.TextUtils
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.BaseValidator
import com.margarin.onlineshopeffectivetestwork.utils.validation.base.ValidateResult
import com.margarin.profile.R
import java.util.regex.Pattern

class NameValidator(val name: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val regex = "[а-яёА-ЯЁ]+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(name)
        val isValid =
            !TextUtils.isEmpty(name) && matcher.matches()

        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success else R.string.text_validation_error
        )
    }
}