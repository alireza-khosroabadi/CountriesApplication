package com.alireza.composeNavigation

import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

interface AnnotationProcessor {
    fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean
    fun supportsAnnotation(annotationType: String): Boolean
}