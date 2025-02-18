
package core

import kotlin.reflect.KFunction

// Figure out how to define ambigious type
// Finish, so I can implement into convert
// So turns out, I cant use varargs for function parameter definitions, so I will have to settle with a single variable


class Procedure
{
    private val procedures = mutableListOf<Transform<*,*>>()

    data class Transform<in T, out R>(val transform: (T) -> R, val name: String){
        var use = true
    }

    fun <T, R> addProcedure(
        input: (T) -> R,
        name: String = if (input is KFunction<*>) input.name else "anonymous"
    ) {// Take in an input of code for a function such that it accepts a list of type T and returns type R
        procedures.add(Transform(input, name))
        // Add that code to procedures after instantiating it is a data class of transform
    }

    fun <J> execute(input: J): Any? {
        var currentValue: Any? = input
        for (p in procedures) {
            // Use safe cast and null check
            val transform = p as? Transform<Any, Any> // Specify what type p is because procedures is * which is any
            if (transform != null && transform.use) {
                currentValue = currentValue?.let { transform.transform(it) }
            }
        }
        return currentValue
    }

    fun removeProcedure(input: String){
        procedures.removeAll { it.name == input }
    }
}


var myProcedure = Procedure()
fun add10(x:Int): Int {
    return x+10
}

fun add20(x:Int): Int {
    return x+20
}

fun printVal(x: Int): Int {
    print("We start with a value of: " + x + "\n")
    return x
}

fun endVal(x: Int) : String {
    return "We end with a value of: $x \n"
}

myProcedure.addProcedure<Int, Int> (::printVal)
myProcedure.addProcedure<Int, Int> (::add10)
myProcedure.addProcedure<Int,Int> (::add20)
myProcedure.removeProcedure("add20")
myProcedure.addProcedure<Int, String>(::endVal)


myProcedure.execute(1)