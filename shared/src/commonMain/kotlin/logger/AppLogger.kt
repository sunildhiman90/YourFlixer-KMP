package logger

import co.touchlab.kermit.Logger


/**
 * Try to use this logger for logging as much as possible, this wil avoid direct dependency on any specific Library
 * If due to any reason, we need to migrate to different library , only this file will require change and not the whole code
 * and secondly if we need to implement custom logger that will also impact this class only
 */
object AppLogger {

    const val tag: String = ""

    inline fun v(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.v(tag, throwable, message)
    }

    inline fun d(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.d(tag, throwable, message)
    }

    inline fun i(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.i(tag, throwable, message)
    }

    inline fun w(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.w(tag, throwable, message)
    }

    inline fun e(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.e(tag, throwable, message)
    }

    inline fun a(message: String, throwable: Throwable? = null, tag: String = this.tag) {
        Logger.a(tag, throwable, message)
    }
}