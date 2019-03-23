package org.aylex.util

import javax.money.MonetaryAmount


inline operator fun MonetaryAmount.plus(other: MonetaryAmount): MonetaryAmount = this.add(other)

inline operator fun MonetaryAmount.minus(other: MonetaryAmount): MonetaryAmount = this.subtract(other)

inline operator fun MonetaryAmount.times(other: Number): MonetaryAmount = this.multiply(other)
