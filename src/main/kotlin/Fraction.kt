/*
Copyright 2023 Chris Basinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import kotlin.math.abs
import kotlin.properties.Delegates

data class Fraction (var numerator: Int, var denominator: Int): Comparable<Fraction>{
    companion object{
        @JvmStatic
        fun sort(fractions: Collection<Fraction>): List<Fraction>{
            val lcm = MathUtils.lcm(fractions.map { it.denominator })
            val copy = mutableListOf<Fraction>()
            fractions.forEach { copy.add(it.copy()) }

            copy.forEach {
                it.numerator = it.numerator * (lcm / it.denominator)
                it.denominator = lcm
            }
            return copy.sortedBy { it.numerator }
        }
    }

    fun reduce(){
        try{
            val gcf = MathUtils.gcf(sortedSetOf(abs(denominator.toLong()), abs(numerator.toLong()))).gcf.toInt()
            denominator = denominator / gcf
            numerator = numerator /gcf
        }catch (e: NumberZeroException){ }
    }

    fun add(fraction: Fraction): Fraction {
        var lcm = MathUtils.lcm(listOf(denominator, fraction.denominator))
        numerator = numerator * (lcm / denominator)
        fraction.numerator = fraction.numerator * (lcm / fraction.denominator)
        val result = Fraction(numerator + fraction.numerator, lcm)
        return result
    }

    fun subtract(fraction: Fraction): Fraction {
        var lcm = MathUtils.lcm(listOf(denominator, fraction.denominator))
        numerator = numerator * (lcm / denominator)
        fraction.numerator = fraction.numerator * (lcm / fraction.denominator)
        val result = Fraction(numerator - fraction.numerator, lcm)
        result.reduce()
        return result
    }

    fun multiply(fraction: Fraction): Fraction {
        val result = Fraction(numerator * fraction.numerator, denominator * fraction.denominator)
        result.reduce()
        return result
    }

    fun divide(fraction: Fraction): Fraction {
        var result: Fraction by Delegates.notNull()
        if(fraction.numerator < 0)
            result = Fraction(numerator * -abs(fraction.denominator), denominator * abs(fraction.numerator))
        else
            result = Fraction(numerator * fraction.denominator, denominator * fraction.numerator)
        result.reduce()
        return result
    }


    override fun compareTo(other: Fraction): Int {
        val lcm = MathUtils.lcm(listOf(denominator, other.denominator))
        numerator = numerator * (lcm / denominator)
        other.numerator = other.numerator * (lcm / other.denominator)
        if(numerator > other.numerator)
            return 1
        else if(numerator < other.numerator)
            return -1
        else
            return 0
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(!(other is Fraction)) return false
        if(numerator == other.numerator && denominator == other.denominator)
            return true
        return false
    }

    operator fun plus(fraction: Fraction): Fraction = add(fraction)

    operator fun minus(fraction: Fraction): Fraction = subtract(fraction)

    operator fun times(fraction: Fraction): Fraction = multiply(fraction)

    operator fun div(fraction: Fraction): Fraction = divide(fraction)

    override fun toString(): String {
        return "$numerator/$denominator"
    }
}