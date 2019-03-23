package org.aylex.dit.dsl

data class Income(var value: Euro = Euro.zero()): Comparable<Income> {

    operator fun compareTo(other: Int): Int {
        return compareTo(Euro.of(other))
    }

    operator fun compareTo(other: Euro): Int {
        return compareTo(Income(other))
    }

    override fun compareTo(other: Income): Int {
        return value.compareTo(other.value)
    }

    operator fun rangeTo(that: Income) = IncomeRange(this, that)

    operator fun inc(): Income {
        return Income(value + Euro.one())
    }

    operator fun dec(): Income {
        return this - Euro.one()
    }

    operator fun minus(other: Income): Income {
        return Income(value - other.value);
    }

    operator fun minus(other: Euro): Income {
        return minus(Income(other))
    }

    operator fun minus (other: Int): Income {
        return minus(Euro.of(other))
    }

    operator fun plus(other: Income): Income {
        return Income(this.value + other.value)
    }

    operator fun plus(other: Euro): Income {
        return plus(Income(other))
    }

    operator fun plus (other: Int): Income {
        return plus(Euro.of(other))
    }
}

class IncomeRange(override val start: Income, override val endInclusive: Income) : ClosedRange<Income>, Iterable<Income> {
    override fun iterator(): Iterator<Income> {
        return IncomeIterator(start, endInclusive)
    }

    fun rangeOfClosedInterval(): Income {
        return if(start > 0) endInclusive - (start -1) else endInclusive
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
