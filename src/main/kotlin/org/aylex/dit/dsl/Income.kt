package org.aylex.dit.dsl

data class Income(var value: Euro = Euro.of(0)): Comparable<Income> {
    fun subtract(euro: Euro): Income {
        return Income(value.subtract(euro))
    }

    fun isGreaterThanOrEqualTo(euro: Euro): Boolean {
        return this.value.isGreaterThanOrEqualTo(euro)
    }

    override fun compareTo(other: Income): Int {
        return value.compareTo(other.value)
    }

    operator fun rangeTo(that: Income) = IncomeRange(this, that)

    operator fun inc(): Income {
        return Income(value.add(Euro.of(1)))
    }
}

class IncomeRange(override val start: Income, override val endInclusive: Income) : ClosedRange<Income>, Iterable<Income> {
    override fun iterator(): Iterator<Income> {
        return IncomeIterator(start, endInclusive)
    }
}

class IncomeIterator(val start: Income, val endInclusive: Income) : Iterator<Income> {
    var initValue = start

    override fun hasNext(): Boolean {
        return initValue <= endInclusive
    }

    override fun next(): Income {
        return initValue++
    }
}