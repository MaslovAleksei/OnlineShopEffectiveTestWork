package com.margarin.onlineshopeffectivetestwork

fun Int.createStringCountOfFavourites(): String {
    val string = when(this) {
        1 ->  "1 товар"
        in 2..4 ->  "$this товара"
        in 5..20 -> "$this товаров"
        else -> ""
    }
    return string
}

fun Int.createStringAvailableForOrder(): String {
    var string = ""
    if (this < 10) {
        string = convertAvailableDigitToString(this, this)
    } else if (this in 10..19) {
        string = "Доступно для заказа $this штук"
    } else if (this in 10..990) {
        val lastDigit = (this.toString().drop(this.toString().length - 1)).toInt()
        string = convertAvailableDigitToString(lastDigit, this)
    }
    return string
}

private fun convertAvailableDigitToString(digit: Int, number: Int): String {
    return when (digit) {
        0 -> "Доступно для заказа $number штук"
        1 -> "Доступно для заказа $number штука"
        in 2..4 -> "Доступно для заказа $number штуки"
        in 5..9 -> "Доступно для заказа $number штук"
        else -> ""
    }
}

fun Int.createStringCountOfFeedback(): String {
    var string = ""
    if (this < 10) {
        string = convertFeedbackDigitToString(this, this)
    } else if (this in 10..19) {
        string = "$this отзывов"
    } else if (this in 10..990) {
        val lastDigit = (this.toString().drop(this.toString().length - 1)).toInt()
        string = convertFeedbackDigitToString(lastDigit, this)
    }
    return string
}

private fun convertFeedbackDigitToString(digit: Int, number: Int): String {
    return when (digit) {
        0 -> "$number отзывов"
        1 -> "$number отзыв"
        in 2..4 -> "$number отзыва"
        in 5..9 -> "$number отзывов"
        else -> ""
    }
}

fun String.formatPhone(): String {
    val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
    return regex.replace(this, "+$1 $2 $3-$4-$5")
}