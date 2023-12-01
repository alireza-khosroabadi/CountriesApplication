package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationNavigation
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import java.util.Locale
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

class DestinationNavigateProcessor: AnnotationProcessor {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment,
        processingEnv: ProcessingEnvironment
    ): Boolean {
        for (element in roundEnv.getElementsAnnotatedWith(DestinationNavigation::class.java)) {
            if (element.kind == ElementKind.METHOD) {
                val executableElement = element as ExecutableElement
                generateNavigateFunction(executableElement, processingEnv)
            }
        }
        return true
    }

    override fun supportsAnnotation(annotationType: String): Boolean {
        return annotationType == DestinationNavigation::class.java.canonicalName
    }

    @OptIn(DelicateKotlinPoetApi::class)
    private fun generateNavigateFunction(
        executableElement: ExecutableElement,
        processingEnv: ProcessingEnvironment
    ) {
        try {
            val annotation = executableElement.getAnnotation(DestinationNavigation::class.java)
            val route = annotation.route
            val packageName = processingEnv.elementUtils.getPackageOf(executableElement).toString()
            val functionName = executableElement.simpleName.toString()
//            val parameters = executableElement.parameters.map { parameter ->
//                ParameterSpec.builder(
//                    parameter.simpleName.toString(),
//                    parameter.asType().asTypeName()
//                )
//                    .build()
//            }

            val arguments = try {
                annotation.arguments // This may throw MirroredTypesException
                emptyList() // If no exception is thrown, return an empty list
            } catch (e: MirroredTypesException) {
                handleMirroredTypesException(e)
            }

            val parameters = arguments.mapIndexed { index, parameter ->
                ParameterSpec.builder(
                    "arg$index",
                    parameter.asTypeName()
                )
                    .build()
            }

            val navigateFunctionBuilder = FunSpec.builder(
                "navigateTo${
                    functionName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                }"
            )
                .receiver(ClassName("androidx.navigation", "NavController"))
                .addParameters(parameters)
                .addCode(
                    """
                this.navigate("$route/${parameters.joinToString("/") { param -> "${'$'}${param.name}" }}"
                 //${arguments.joinToString()}   
                )
                """.trimIndent()
                )

            val file = FileSpec.builder(packageName, "${functionName}NavigationExtensions")
                .addFunction(navigateFunctionBuilder.build())
                .build()

            file.writeTo(processingEnv.filer)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun handleMirroredTypesException(exception: MirroredTypesException): List<TypeMirror> {
        val types = mutableListOf<TypeMirror>()

        // Access the TypeMirrors indirectly
        exception.typeMirrors.forEach { typeMirror ->
            types.add(typeMirror)
        }

        return types
    }
}