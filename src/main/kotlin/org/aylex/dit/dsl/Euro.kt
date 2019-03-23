package org.aylex.dit.dsl

import org.aylex.util.minus
import org.aylex.util.plus
import org.aylex.util.times
import org.javamoney.moneta.Money
import org.javamoney.moneta.format.CurrencyStyle
import java.util.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import javax.money.format.AmountFormatQueryBuilder
import javax.money.format.MonetaryAmountFormat
import javax.money.format.MonetaryFormats

data class Euro private constructor(val value: MonetaryAmount): Comparable<Euro> {

    override fun compareTo(other: Euro): Int {
        return value.compareTo(other.value)
    }

    operator fun inc(): Euro {
        return plus(Euro.one())
    }

    operator fun plus(euro: Euro): Euro {
        return Euro(this.value + euro.value)
    }

    operator fun times(by: Number): Euro {
        return Euro(value * by)
    }

    operator fun minus(other: Euro): Euro {
        return Euro(value - other.value)
    }

    operator fun rangeTo(that: Euro) = Range(this,that)

    companion object {
        private val locale = Locale.forLanguageTag("nl-NL")
        private val currency = Monetary.getCurrency(locale)
        private val format: MonetaryAmountFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder
                .of(locale)
                .set(CurrencyStyle.SYMBOL)
                .set("pattern", "¤ ###,##0.00")
                .build())

        fun zero(): Euro {
            return of(0)
        }

        fun one(): Euro {
            return of(1)
        }

        fun maxValue(): Euro {
            return of(Integer.MAX_VALUE)
        }

        fun of(value: Int): Euro {
            return Euro(Money.of(value, currency))
        }

        fun of(value: Double): Euro {
            return Euro(Money.of(value, currency))
        }

        fun of(value: String): Euro {
            return Euro(format.parse(value))
        }
    }
}

class Range(override val start: Euro, override val endInclusive: Euro) : ClosedRange<Euro>, Iterable<Euro> {
    override fun iterator(): Iterator<Euro> {
        return EuroIterator(start, endInclusive)
    }
}

class EuroIterator(val start: Euro, val endInclusive: Euro) : Iterator<Euro> {
    var initValue = start

    override fun hasNext(): Boolean {
        return initValue <= endInclusive
    }

    override fun next(): Euro {
        return initValue++
    }
}
