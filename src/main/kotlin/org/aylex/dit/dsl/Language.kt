package org.aylex.dit.dsl

import org.aylex.dit.TaxCalculator
import uy.klutter.core.collections.asReadOnly
import java.text.NumberFormat



fun box1Income(block: IncomeBuilder.() -> Unit): Income = IncomeBuilder().apply(block).build()

class IncomeBuilder {
    private var euro: Euro = Euro.of(0)
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

    private var percentage = Percentage.of(0)
    var value : String = "0%"
        set(value) {
            val percentageFormat = NumberFormat.getPercentInstance()
            percentageFormat.minimumFractionDigits = 2
            percentage = Percentage.of(percentageFormat.parse(value))
        }

    private var minEuro: Euro = Euro.of(0)
    var min : String = "EUR 0"
        set(value) {
            minEuro = Euro.of(value)
        }

    private var maxEuro: Euro = Euro.of(0)
    var max : String = "EUR 0"
        set(value) {
            maxEuro = Euro.of(value)
        }

    fun build(): TaxBracket {
        return TaxBracket(percentage, minEuro, maxEuro)
    }
}
