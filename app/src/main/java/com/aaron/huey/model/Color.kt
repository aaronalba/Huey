package com.aaron.huey.model


/**
 * Class representing a color
 */
class Color(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    /**
     * Returns the hexadecimal representation of this color
     */
    fun getHex(): String {
        var buf = ""
        buf += decToHex(red)
        buf += decToHex(green)
        buf += decToHex(blue)
        return buf
    }


    /*
        Converts a decimal number to hexadecimal as string
     */
    private fun decToHex(num: Int): String {
        val list: ArrayList<Int> = ArrayList()
        var n = num

        // repeatedly divide the decimal number with 16 and store the remainders
        while(n > 0) {
            list.add(n%16)
            n /= 16
        }

        // reverse the remainder list
        list.reverse()

        // convert each remainder to hexadecimal and append it to the buffer string
        var buf = ""
        list.forEach{
            buf += when(it) {
                10 -> "A"
                11 -> "B"
                12 -> "C"
                13 -> "D"
                14 -> "E"
                15 -> "F"
                else -> it
            }
        }

        return buf
    }




    override fun toString(): String {
        return "color{${this.red}, ${this.green}, ${this.blue}}"
    }

}