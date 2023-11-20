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

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor

object MathUtils{
    @JvmStatic
    @Throws(NumberZeroException::class, NegativeNumberException::class)
    fun gcf(numbers: SortedSet<Long>): FactorResult<Long> {
        numbers.forEach {
            if(it == 0L) throw NumberZeroException()
            if(it < 0L) throw NegativeNumberException()
        }

        val factors = ConcurrentHashMap<Long, MutableList<Long>>()
        numbers.forEach { num ->
            factors[num] = mutableListOf()
        }

        var pool = Executors.newFixedThreadPool(numbers.size) as ThreadPoolExecutor

        val tasks = mutableListOf<Future<*>>()
        numbers.forEach { num ->
            val task = pool.submit {
                for (i in 1..num) {
                    if (num % i == 0L) {
                        synchronized(factors){
                            factors[num]!!.add(i)
                        }
                    }
                }
            }
            tasks.add(task)
        }
        tasks.forEach { it.get() }

        if (numbers.size == 1) {
            factors[numbers.first()]!!
            return FactorResult(factors[numbers.first()]!!.last(), factors[numbers.first()]!!, factors)
        }

        val commonFactors = Collections.synchronizedList(mutableListOf<Long>())
        numbers.drop(1)
        tasks.clear()
        if(pool.maximumPoolSize < factors[factors.keys.first()]!!.size)
            pool.maximumPoolSize = factors[factors.keys.first()]!!.size
        factors[factors.keys.first()]!!.forEach { factor ->
            val task = pool.submit {
                var count = 0
                numbers.forEach { num ->
                    if (factors[num]!!.contains(factor))
                        count++
                }
                if (count == numbers.size)
                    synchronized(commonFactors){
                        commonFactors.add(factor)
                    }
            }
            tasks.add(task)
        }

        tasks.forEach { it.get() }
        val result = FactorResult<Long>(commonFactors.max(), commonFactors, factors)
        return result
    }

    /*this is wierd and hacked together. It needs to be rewritten*/
    @JvmStatic
    fun lcm(numbers: List<Int>): Int{
        var lcm: Int = numbers.max()
        while(true){
            var isMultiple = false
            for(number in numbers) {
                if(lcm % number != 0) {
                    lcm++
                    continue
                }else{
                    if(!isMultiple)
                        isMultiple = true
                }
            }
            if(isMultiple){
                return lcm
            }
        }
    }
}