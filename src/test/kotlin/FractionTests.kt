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

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FractionTests {
    val fractionA = Fraction(1, 2)
    val fractionB = Fraction(1,5)
    val fractionC = Fraction(3,10)

    @Test
    fun addFractionTest(){
        val result = fractionA + fractionB
        assertEquals(Fraction(7,10), result)
    }

    @Test
    fun subtractFractionTest(){
        val result = fractionA - fractionB
        assertEquals(Fraction(3,10), result)
    }

    @Test
    fun multiplyFractionTest(){
        val result = fractionA * fractionB
        assertEquals(Fraction(1,10), result)
    }

    @Test
    fun divideFractionTest(){
        val result = fractionA / fractionB
        assertEquals(Fraction(5,2), result)
    }

    @Test
    fun sortFractionsTest(){
        val fractions = listOf(fractionA,fractionB,fractionC)
        val sortedFractions = fractions.sort()
        sortedFractions.reduce()
        assertContentEquals(listOf(fractionB, fractionC, fractionA), sortedFractions)
    }

    @Test
    fun reduceFractionTest(){
        val fraction = Fraction(2,10)
        fraction.reduce()
        assertEquals(Fraction(1,5), fraction)
    }

    @Test
    fun lcmTest(){
        val lcm = MathUtils.lcm(listOf(2,5,10))
        assertEquals(10, lcm)
    }

    @Test
    fun gcfTest(){
        val gcf = MathUtils.gcf(sortedSetOf(10, 20, 100))
        assertEquals(10, gcf.gcf)
    }

    @Test
    fun fractionCompareToTest(){
        val result = fractionA > fractionB
        assertTrue(result)
    }
}