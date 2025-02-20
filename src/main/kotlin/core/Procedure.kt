
package core

import kotlin.reflect.KFunction

/*
* Done so far:
* So I can only have a single variable for parameters for each function I add to procedures
* My add procedures function works
* My remove procedures function works
* My execute function works
*
* Left to do:
* add flags to be able to handle easy toggling on and off of functions, based on index, name, return type, input type.
* Add checks to ensure safe handling of functions that have a return type that does not match the input type of the next function -> In fact allow them to skip and keep skipping until valid function is found
* */

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