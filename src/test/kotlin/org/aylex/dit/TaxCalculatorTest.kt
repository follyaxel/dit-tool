package org.aylex.dit

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.aylex.dit.dsl.Euro
import org.aylex.dit.dsl.box1Income
import org.aylex.dit.dsl.taxCalculator
import kotlin.test.Test

class TaxCalculatorTest {
    private val taxCalculator = TaxCalculator();

    @Test fun when_no_income_then_zero_tax_is_calculated() {
        val income = box1Income{ amount = "€ 0" }

        val tax = taxCalculator.calculateTax(income);

        assertThat(tax, equalTo(Euro.of(0)));
    }

    @Test fun when_income_tax_is_calculated() {
        val income = box1Income{ amount = "€ 1" }
        val taxCalculator = taxCalculator {
            taxBracket {
                min = "EUR 0"
                minIncome {
                  amount = "EUR 0"
                }
                tax = "10%"
            }
        }
        val tax = taxCalculator.calculateTax(income);

        assertThat(tax, equalTo(Euro.of(0.1)));
    }

    @Test fun when_income_divided_over_two_tax_brackets_tax_is_calculated() {
        val income = box1Income{ amount = "€ 10" }
        val taxCalculator = taxCalculator {
            taxBrackets {
                taxBracket {
                    min = "EUR 0"
                    minIncome {
                      amount = "EUR 0"
                    }
                    max = "EUR 5"
                    maxIncome {
                       amount = "EUR 5"
                    }
                    tax = "10%"
                }
                taxBracket {
                    min = "EUR 6"
                    minIncome {
                        amount = "EUR 6"
                    }
                    tax = "20%"
                }
            }
        }

        val tax = taxCalculator.calculateTax(income);

        assertThat(tax, equalTo(Euro.of(1.5)));
    }
}
