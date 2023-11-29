package com.alireza.composeNavigation

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

class CompositeProcessor (
) : AbstractProcessor() {

    private val processors: List<AnnotationProcessor> = listOf(
        DestinationScreenProcessor(),
        DestinationNavigateProcessor()
        // Add more processors for other annotation types as needed
    )

    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        for (annotation in annotations) {
            val annotationType = annotation.toString()

            val matchingProcessor = processors.find { processor ->
                processor.supportsAnnotation(annotationType)
            }

            matchingProcessor?.let { processor ->
                if (processor.process(annotations, roundEnv)) {
                    return true
                }
            }
        }
        return false
    }
}