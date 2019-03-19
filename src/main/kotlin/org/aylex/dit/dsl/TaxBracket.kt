package org.aylex.dit.dsl

data class TaxBracket(val value: Percentage, val min: Euro, val max: Euro) {
    fun hasMax(): Boolean {
        return max.isGreaterThan(Euro.of(0))
    }

    fun hasMin(): Boolean {
        return min.isGreaterThan(Euro.of(0))
    }
}
