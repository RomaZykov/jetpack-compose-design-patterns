package com.cavin.designpatterns.patterns.singleton

// 1.Object Declaration** (Most Common)
// Kotlin's `object` keyword inherently implements the singleton pattern.

object MySingleton {
    fun doSomething() {
        println("Singleton Instance")
    }
}


// 2. Lazy Initialization
// Use the `lazy` delegate to create a singleton only when accessed for the first time.

class MySingleton2 private constructor() {
    companion object {
        val instance: MySingleton2 by lazy { MySingleton2() }
    }
}


// 3. Double-Checked Locking (Thread-Safe Singleton)
// Ensures thread safety in a multithreaded environment.

class MySingleton3 private constructor() {
    companion object {
        @Volatile
        private var instance: MySingleton3? = null

        fun getInstance(): MySingleton3 {
            return instance ?: synchronized(this) {
                instance ?: MySingleton3().also { instance = it }
            }
        }
    }
}
