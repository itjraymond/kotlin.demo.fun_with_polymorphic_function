package ca.jent.kotlin.demo.fun_with_polymorphic_function

/**
 * Polymorphic function composition
 * Takes two functions.  The output Type of the first functions must match the input Type of the second.
 */
fun <A,B,C> compose(f: (A) -> B, g: (B) -> C): (A) -> C = { g(f(it)) }

// composition usage:
// first function
val f: (Int) -> Double = { it * 1.07 }
// second function
val g: (Double) -> String = { "value is $it" }
// the composed function
val h: (Int) -> String = compose(f,g)

// composed function usage:
val result = h(3) // "value is 3.21"

/**
 * Another "fun" way to provide polymorphic function composition is to use curried functions techniques.
 * With the types defined in the body, the returned type is inferred.
 * hof: Higher Order Function
 */
fun <A,B,C> hof_1() =
        { f: (A) -> B ->
            { g: (B) -> C ->
                { x: A -> g(f(x)) }
            }
        }

/**
 * Or we can defined only the returned function type while the body infer the types.
 * Both hof_1 and hof_2 are equivalent (i.e. exactly the same).
 */
fun <A,B,C> hof_2(): ((A) -> B) -> ((B) -> C) -> (A) -> C =
        { f ->
            { g ->
                { x -> g(f(x)) }
            }
        }

// Usage of hof_1()
// hof will now be a function that takes an function of (Int) -> Double which returns another function
// that takes yet another function of (Double) -> String which return yet another function that takes
// an (Int) -> String. So if we supply the Int value of 3 as the third function call (see below), it will
// compute the value as a String:  "value is 3.21"
val hof = hof_1<Int,Double,String>()
// Usage of hof
val result2 = hof(f)(g)(3)


fun main() {
    println(result) // "value is 3.21"
    println(result2)
}