package com.alireza.composeNavigation

import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

interface AnnotationProcessor {
    fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment,
        processingEnv: ProcessingEnvironment
    ): Boolean
    fun supportsAnnotation(annotationType: String): Boolean
}