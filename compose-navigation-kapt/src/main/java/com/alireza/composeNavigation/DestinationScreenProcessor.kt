package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationScreen
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class DestinationScreenProcessor: AnnotationProcessor {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        for (element in roundEnv.getElementsAnnotatedWith(DestinationScreen::class.java)) {
            if (element.kind == ElementKind.METHOD) {
                val executableElement = element as ExecutableElement
                generateExtensionFunction(executableElement)
            }
        }
        return true
    }

    override fun supportsAnnotation(annotationType: String): Boolean {
        return annotationType == DestinationScreen::class.java.canonicalName
    }

    private fun generateExtensionFunction(executableElement: ExecutableElement) {
        // Implementation for generating extension function for DestinationScreen
        // ...
    }
}