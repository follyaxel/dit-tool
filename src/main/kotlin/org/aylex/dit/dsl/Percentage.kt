package org.aylex.dit.dsl

import java.math.BigDecimal

data class Percentage private constructor(val value: BigDecimal) {
    fun of(income: Income): Euro {
        return of(income.value)
    }

    private fun of(euro: Euro): Euro {
        return euro * value
    }

    companion object {
        fun of(value: Int): Percentage {
            return Percentage(BigDecimal(value))
        }

        fun of(value: Number): Percentage {
            return Percentage(BigDecimal(value.toString()))
        }
    }
}
