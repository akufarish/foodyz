package com.example.stylish.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(nominal: Int): String {

    return NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(nominal)
}