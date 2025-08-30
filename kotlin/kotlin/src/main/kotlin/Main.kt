package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    // print
    println("Hello, world!!!")

    // 'val' cannot be reassigned
    // ========= variables and primitive data type =======
    // int
    val x = 5
    val y: Int = 5

    // double
    val f: Double = 10.0

    // float
    val flt = 5.123f

    val married: Boolean = true
    //     val married = true

    // string
    val name = "Shahriar"
    // name = "Md Shahriar Hosen"
    val child:String = "Ayesha"

    println(x)
    println(y)
    println(f)
    println(married)
    println(name)

    //  ============ var changeable ===========
    var myName = "Md. Shahriar"
    myName += " Hosen"
    println(myName)

    // string template
    println("Your name is $myName")

    println("10 mod 2 is true: ${10 % 2 == 0}")

    // ================== operators ==========
    // || &&

    // input
//    println("Please enter a number: ")
//    val input = readln()
//    val inputAsInt = input.toInt()

    println("Please enter a number: ")
    val inputIntOrNull = readln().toIntOrNull() ?: 0 // default value

//    println("Input $inputAsInt")
    println("Input $inputIntOrNull")

    // problem
    if(inputIntOrNull != null) {
        val isEven = inputIntOrNull!! % 2 == 0 // !! null safety
        println(isEven)
    } else {
        println("Dude, enter a valid number.")
    }

    // soln: !! execute if not equal to null
    val isEven = inputIntOrNull!! % 2 == 0 // !! null safety
    println(isEven)

    // conditional statements if else elif
    // when : switch
    val mark = 79
    val result = when{
        mark >= 80 -> "A+"
        mark >= 70 ->  "A"
        mark >= 60 -> "B"
        mark >= 50 -> "C"
        mark >= 40 -> "D"
        else -> "F"
    }
    println(result)

    // exception handler
    var input = readln()
    val inputAsIntAgain = try {
        input.toInt()
    } catch(e: NumberFormatException) {
        0 // if no input then set 0
    } finally {
        // close something like file close, network close
    }

    // array
    val numbers = intArrayOf(1, 2, 3, 4)
    println(numbers)
    println(numbers[0])

    println(numbers.getOrNull(4)) // 4th index

    // ====================== loops ====================
    for (number in numbers) {
        println(number)
    }

    val cars = arrayOf("Volvo", "BMW", "Ford", "Mazda")
    for(car in cars) {
        println(car)
    }

    // while
    println("how many numbers will you enter? ")
    val amountOfNumbers = readln().toIntOrNull() ?: 0
    var sum = 0
    var i = 0
//    while (i < amountOfNumbers) {
//        println("Please enter number #${i + 1} ")
//        val number = readln().toIntOrNull() ?: 0
//        sum += number
//        i++
//    }
//    println("Sum is $sum")

    // mutable list
    var mutableNumbers = mutableListOf<Int>()
    while (i < amountOfNumbers) {
        println("Please enter number #${i + 1} ")
        val number = readln().toIntOrNull() ?: continue
        mutableNumbers.add(number)
        i++
    }
    println("Numbers ${mutableNumbers}")

    println("Enter a line")
    val input2 = readln()
    val finalString = buildString {
        for(i in input2.lastIndex downTo 0) {
            append(input2[i])
        }
    }
    print("finalString $finalString")

    // ================= function ==================
    fun greet(name: String) {
        println("Welcome $name")
    }
    greet("Shahriar")

    println("Enter a line")
    val input3 = readln()

    fun reversed(
        stringToReverse: String = "Hello World" // default
    ): String {
        val finalString = buildString {
            for(i in input3.lastIndex downTo 0) {
                append(input3[i])
            }
        }
        return  finalString
        //        println("The reverse string $finalString")
    }
    val revStr1 = reversed(input3)
    val revStr2 = reversed(stringToReverse=input3)
    println(revStr1)
    println(revStr2)

    fun String.reversed(): String { // inject String class a reverse method
        val finalString = buildString {
            for(i in this.lastIndex downTo 0) {
                append(this[i])
            }
        }
        return  finalString
    }
    val strRev3 = readln().reversed() // inject
    println("The reverse string $strRev3")

    // ==================== built-in functions filter ============
    val lettersOnly = input.filter {
        it.isLetter()
    }
    println(lettersOnly)

    val favouriteNumbers = intArrayOf(1, 2, 3, 4, 5, 6)
    val evenNumbers =  favouriteNumbers.filter { it % 2 == 0 }

    val lambda: (Char) -> Boolean = {
        it.isLetter()
    }
    val lettersOnly2 = input.filter(lambda)
}