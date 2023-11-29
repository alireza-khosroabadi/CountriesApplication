package com.alireza.composeNavigation

import javax.lang.model.element.Element

class ProcessingException(private val element: Element?, msg: String? = null, vararg args: Any) :
    Exception("$element, ${args.joinToString()}") {

    fun getElement(): Element? {
        return element
    }

}