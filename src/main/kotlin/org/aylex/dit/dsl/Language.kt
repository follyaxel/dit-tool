package org.aylex.dit.dsl

import org.aylex.dit.TaxCalculator
import uy.klutter.core.collections.asReadOnly
import java.text.NumberFormat



fun box1Income(block: IncomeBuilder.() -> Unit): Income = IncomeBuilder().apply(block).build()

class IncomeBuilder {
    private var euro: Euro = Euro.zero()
    var amount : String = "EUR 0"
        set(value) {
            euro = Euro.of(value)
        }


    fun build(): Income = Income(euro)
}

fun taxCalculator(block: TaxCalculatorBuilder.() -> Unit): TaxCalculator = TaxCalculatorBuilder().apply(block).build()

class TaxCalculatorBuilder {

    private val taxBrackets = mutableListOf<TaxBracket>()

    fun taxBracket(block: TaxBracketBuilder.() -> Unit) {
        taxBrackets.add(TaxBracketBuilder().apply(block).build())
    }

    fun taxBrackets(block: TAXBRACKETS.() -> Unit) {
        taxBrackets.addAll(TAXBRACKETS().apply(block))
    }

    fun build(): TaxCalculator = TaxCalculator(taxBrackets.asReadOnly())
}

class TAXBRACKETS: ArrayList<TaxBracket>() {
    fun taxBracket(block: TaxBracketBuilder.() -> Unit) {
        add(TaxBracketBuilder().apply(block).build())
    }
}

class TaxBracketBuilder {
    private var taxPercentage = Percentage.of(0)
    var tax : String = "0%"
        set(value) {
            val percentageFormat = NumberFormat.getPercentInstance()
            percentageFormat.minimumFractionDigits = 2
            taxPercentage = Percentage.of(percentageFormat.parse(value))
        }

    var minimum: String = "EUR 0"
        set(value) {
            min = Income(Euro.of(value))
        }

    var maximum: String = "EUR 0"
        set (value) {
            max = Income(Euro.of(value))
        }

    private var min: Income = Income(Euro.zero())
    fun min(block: IncomeBuilder.() -> Unit) {
        min = IncomeBuilder().apply(block).build()
    }

    private var max: Income = Income(Euro.maxValue())
    fun max(block: IncomeBuilder.() -> Unit) {
        max = IncomeBuilder().apply(block).build()
    }

    fun build(): TaxBracket {
        return TaxBracket(taxPercentage, min, max)
    }
}
