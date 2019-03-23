package org.aylex.dit.dsl

data class TaxBracket(val tax: Percentage, val min: Income = Income(Euro.zero()), val max: Income = Income(Euro.maxValue())) {
    private val taxRange: IncomeRange = IncomeRange(min, max)

    fun calculateTax(income: Income): Euro {
        var taxValue: Euro = Euro.zero()

        if(income >= min) {
            val taxableRange = min..income.coerceIn(taxRange)
            val taxableAmount = taxableRange.rangeOfClosedInterval()
            taxValue = tax.of(taxableAmount)
        }

        return taxValue;
    }
}
