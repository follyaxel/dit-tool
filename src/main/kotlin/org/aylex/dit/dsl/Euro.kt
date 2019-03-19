package org.aylex.dit.dsl

import org.javamoney.moneta.Money
import org.javamoney.moneta.format.CurrencyStyle
import java.math.BigDecimal
import java.util.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import javax.money.format.AmountFormatQueryBuilder
import javax.money.format.MonetaryAmountFormat
import javax.money.format.MonetaryFormats

data class Euro private constructor(private val value: MonetaryAmount) {
    fun multiplyBy(number: BigDecimal): Euro {
        return Euro(value.multiply(number))
    }

    fun add(euro: Euro): Euro {
        return Euro(value.add(euro.value))
    }

    fun subtract(euro: Euro): Euro {
        return Euro(value.subtract(euro.value))
    }

    fun isGreaterThan(euro: Euro): Boolean {
        return this.value.isGreaterThan(euro.value)
    }

    fun isGreaterThanOrEqualTo(euro: Euro): Boolean {
        return this.value.isGreaterThanOrEqualTo(euro.value)
    }


    companion object {
        private val locale = Locale.forLanguageTag("nl-NL")
        private val currency = Monetary.getCurrency(locale)
        private val format: MonetaryAmountFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder
                .of(locale)
                .set(CurrencyStyle.SYMBOL)
                .set("pattern", "¤ ###,##0.00")
                .build())

        fun of(value: Int): Euro {
            return Euro(Money.of(value, currency))
        }

        fun of(value: Double): Euro {
            return Euro(Money.of(value, currency))
        }

        fun of(value: String): Euro {
            return Euro(format.parse(value))
        }

        fun of(monetaryAmount: MonetaryAmount): Euro {
            return Euro(monetaryAmount)
        }
    }
}

