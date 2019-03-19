package org.aylex.dit.dsl

data class Income(var value: Euro = Euro.of(0)) {
    fun subtract(euro: Euro): Income {
        return Income(value.subtract(euro))
    }

    fun isGreaterThanOrEqualTo(euro: Euro): Boolean {
        return this.value.isGreaterThanOrEqualTo(euro)
    }
}
