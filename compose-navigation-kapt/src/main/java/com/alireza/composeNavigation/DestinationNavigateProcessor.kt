package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationNavigate
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class DestinationNavigateProcessor: AnnotationProcessor {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        for (element in roundEnv.getElementsAnnotatedWith(DestinationNavigate::class.java)) {
            if (element.kind == ElementKind.METHOD) {
                val executableElement = element as ExecutableElement
                generateNavigateFunction(executableElement)
            }
        }
        return true
    }

    override fun supportsAnnotation(annotationType: String): Boolean {
        return annotationType == DestinationNavigate::class.java.canonicalName
    }

    private fun generateNavigateFunction(executableElement: ExecutableElement) {
        // Implementation for generating navigate function for DestinationNavigate
        // ...
    }
}