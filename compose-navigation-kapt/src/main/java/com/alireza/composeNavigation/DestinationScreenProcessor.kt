package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationScreen
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class DestinationScreenProcessor: AnnotationProcessor {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment,
        processingEnv: ProcessingEnvironment
    ): Boolean {
        for (element in roundEnv.getElementsAnnotatedWith(DestinationScreen::class.java)) {
            if (element.kind == ElementKind.METHOD) {
                val executableElement = element as ExecutableElement
                generateExtensionFunction(executableElement,processingEnv)
            }
        }
        return true
    }

    override fun supportsAnnotation(annotationType: String): Boolean {
        return annotationType == DestinationScreen::class.java.canonicalName
    }

    @OptIn(DelicateKotlinPoetApi::class)
    private fun generateExtensionFunction(
        executableElement: ExecutableElement,
        processingEnv: ProcessingEnvironment
    ) {
        val annotation = executableElement.getAnnotation(DestinationScreen::class.java)
        val functionName = executableElement.simpleName.toString()
        val packageName = processingEnv.elementUtils.getPackageOf(executableElement).toString()

        val parameters = executableElement.parameters.map {
            ParameterSpec.builder(it.toString(), it.asType().asTypeName()).build()
        }

        val builder = FunSpec.builder(functionName)
            .receiver(ClassName.bestGuess("androidx.navigation.NavGraphBuilder")) // Replace with your actual package and class names
            .addParameters(parameters)
            .addCode(
                """
                composable("${annotation.route}") {
                    ${functionName}(${parameters.joinToString { it.name }})
                }
                """.trimIndent()
            )

        val file = FileSpec.builder(packageName, "${functionName}Extensions")
            .addFunction(builder.build())
            .addImport("androidx.navigation.compose", "composable")
            .build()

        file.writeTo(processingEnv.filer)
    }
}