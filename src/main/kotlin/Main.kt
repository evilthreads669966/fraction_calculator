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

fun main(){
    while(true){
        println("Enter for example 1/2 + 2/5")
        println("Supports + - * /")

        val input = readlnOrNull()?.trim()
        if(input.isNullOrBlank()) continue
        var parts = input.split(" ")
        val result = evaluateFractionExpression(parts)
        if(result == null) continue
        result.reduce()
        println("The answer is: $result")
    }

}

fun evaluateFractionExpression(parts: List<String>): Fraction?{
    val leftFractionParts = parts[0].split("/")
    val leftNumerator = leftFractionParts[0].toIntOrNull()
    val leftDenominator = leftFractionParts[1].toIntOrNull()
    if(leftNumerator == null) return null
    if(leftDenominator == null) return null
    val leftFraction = Fraction(leftNumerator,leftDenominator)

    val rightFractionParts = parts[2].split("/")
    val rightNumerator = rightFractionParts[0].toIntOrNull()
    val rightDenominator = rightFractionParts[1].toIntOrNull()
    if(rightNumerator == null) return null
    if(rightDenominator == null) return null
    val rightFraction = Fraction(rightNumerator, rightDenominator)

    when(parts[1]){
        "+" -> return leftFraction + rightFraction
        "-" -> return leftFraction - rightFraction
        "*" -> return leftFraction * rightFraction
        "/" -> return leftFraction / rightFraction
        else -> return null
    }
}