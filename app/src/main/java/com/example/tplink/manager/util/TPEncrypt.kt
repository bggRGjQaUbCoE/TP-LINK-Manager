package com.example.tplink.manager.util

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 * convert from https://github.com/gyje/tplink_encrypt/blob/master/tpencrypt.py
 */

val String.encrypt: String
    get() = run {
        val a = "RDpbLfCPsJZ7fiv"
        val c =
            "yLwVl0zKqws7LgKPRQ84Mdt708T1qQ3Ha7xv3H7NyU84p21BriUWBU43odz3iP4rBL3cD02KZciXTysVXiV8ngg6vL48rPJyAUw0HurW20xqxv9aYb4M9wK1Ae0wlro510qXeU07kV57fQMc8L6aLgMLwygtc0F10a0Dg70TOoouyFhdysuRMO51yY5ZlOZZLEal1h0t9YQW0Ko7oBwmCAHoic4HYbUyVeU3sfQ1xtXcPcf1aT303wAQhv66qzW"
        var e = ""
        val f: Int
        var l: Int
        var n: Int
        val g: Int = a.length
        val h: Int = this.length
        val k: Int = c.length
        f = if (g > h) {
            g
        } else {
            h
        }
        for (p in 0 until f) {
            l = 187
            n = l
            if (p >= g) {
                n = this[p].code
            } else {
                if (p >= h) {
                    l = a[p].code
                } else {
                    l = a[p].code
                    n = this[p].code
                }
            }
            e += c[(l xor n) % k]
        }
        e
    }