package com.alireza.composeNavigationAnnotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class DestinationScreen(val route:String)