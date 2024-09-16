package com.golive.godrive.btppublic.service

import com.google.common.base.MoreObjects

class ApiError {
    internal var text: String? = null
    private var throwable: Throwable? = null

    constructor(t: Throwable) {
        text = t.toString()
        throwable = t
    }

    constructor(message: String) {
        text = message
    }

    constructor()

    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    fun getThrowable(): Throwable? {
        return throwable
    }

    fun setThrowable(throwable: Throwable) {
        this.throwable = throwable
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("text", text)
            .add("throwable", throwable)
            .toString()
    }
}