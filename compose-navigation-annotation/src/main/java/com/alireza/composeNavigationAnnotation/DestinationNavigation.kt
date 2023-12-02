package com.alireza.composeNavigationAnnotation

import kotlin.reflect.KClass


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class DestinationNavigation(
    val route:String,
    vararg val argumentsToPass: KClass<*>
){
    companion object {
        const val route = "route"
        const val arguments = "argumentsToPass"
    }
}
