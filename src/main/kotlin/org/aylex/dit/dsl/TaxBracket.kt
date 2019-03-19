package org.aylex.dit.dsl

import java.rmi.UnexpectedException
import kotlin.sequences.generateSequence

data class TaxBracket(val tax: Percentage, val min: Euro, val max: Euro, val minIncome: Income, val maxIncome: Income) {
    fun hasMax(): Boolean {
        return maxIncome.value.isGreaterThan(Euro.of(0))
    }

    fun hasMin(): Boolean {
        return minIncome.value.isGreaterThan(Euro.of(0))
    }

    fun calculateTax(income: Income): Euro {
        val incomeRange = determineRange(income)
        return if(incomeRange.isEmpty()) Euro.of(0)
               else tax.of(income.coerceIn(incomeRange))
    }

    private fun determineRange(income: Income): IncomeRange {
        return if(hasMin() && hasMax()) minIncome..maxIncome
        else if (hasMin()) minIncome..income
        else if (hasMax()) income..maxIncome
        else throw UnexpectedException("invalid tax bracket configuration")
    }
}
