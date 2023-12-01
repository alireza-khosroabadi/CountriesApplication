package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationNavigation
import com.alireza.composeNavigationAnnotation.DestinationScreen
import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedOptions("kapt.kotlin.generated")
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
                if (processor.process(annotations, roundEnv, processingEnv)) {
                    return true
                }
            }
        }
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf<String>().apply {
            add(DestinationScreen::class.java.canonicalName)
            add(DestinationNavigation::class.java.canonicalName)
        }
    }
}