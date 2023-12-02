package com.alireza.ksp

import com.alireza.composeNavigationAnnotation.DestinationNavigation
import com.alireza.utils.getParameterValue
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import kotlinx.metadata.KmClass
import java.io.IOException
import java.util.Locale
import kotlin.reflect.KClass

class NavigationVisitor(private val resolver: Resolver, private val codeGenerator: CodeGenerator) : KSVisitorVoid() {


    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        val annotation = function.annotations.find {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == DestinationNavigation::class.java.canonicalName
        }

        if (annotation != null) {
            processNavigationFunction(function, annotation)
        }
    }

    private fun processNavigationFunction(function: KSFunctionDeclaration, annotation: KSAnnotation) {
        val route = annotation.arguments.find { it.name?.asString() == DestinationNavigation.route}?.value?.toString() ?: ""
        val arguments = annotation.arguments.getParameterValue<ArrayList<KmClass>>(DestinationNavigation.arguments)

        generateNavigationExtensionFunction(function, route, arguments)
    }

    private fun generateNavigationExtensionFunction(function: KSFunctionDeclaration, route: String, arguments: ArrayList<KmClass>) {
        val packageName = function.packageName.asString()
        val functionName = function.simpleName.asString()
        val extensionFunctionName = "navigateTo${functionName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }}"

        // Generate the extension function using KotlinPoet
        val extensionFunction = FunSpec.builder(extensionFunctionName)
            .receiver(ClassName("androidx.navigation", "NavController"))
            .addParameters(arguments.mapIndexed { index, ksClassDeclaration ->
                ParameterSpec.builder("arg$index", ksClassDeclaration.javaClass).build()
            })
            .addStatement("this.navigate(\"$route\")")
            .build()

        // Create a file using KotlinPoet
        val file = FileSpec.builder(packageName, "GeneratedNavigationExtensions")
            .addFunction(extensionFunction)
            .build()

        // Write the file using the KSP CodeGenerator
        try {
            codeGenerator.createNewFile(
                dependencies = Dependencies.ALL_FILES,
                packageName = packageName,
                fileName = "GeneratedNavigationExtensions"
            ).writer().use { writer ->
                file.writeTo(writer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun KSClassDeclaration.asTypeName(): TypeName {
        return ClassName.bestGuess(qualifiedName?.asString() ?: "")
    }
//
//    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
//        val annotation = function.annotations.find {
//            it.annotationType.resolve().declaration.qualifiedName?.asString() == DestinationNavigation::class.java.canonicalName
//        }
//
//        if (annotation != null) {
//            processNavigationFunction(function, annotation)
//        }
//    }
//
//    private fun processNavigationFunction(function: KSFunctionDeclaration, annotation: KSAnnotation) {
//        val route = annotation.arguments.find { it.name?.asString() == "route" }?.value?.toString() ?: ""
//        val arguments = annotation.arguments.find { it.name?.asString() == "arguments" }?.value as List<KSClassDeclaration>
//
//        generateNavigationExtensionFunction(function, route, arguments)
//    }
//
//    private fun generateNavigationExtensionFunction(function: KSFunctionDeclaration, route: String, arguments: List<KSClassDeclaration>) {
//        val packageName = function.packageName.asString()
//        val functionName = function.simpleName.asString()
//        val extensionFunctionName = "navigateTo${functionName.capitalize()}"
//
//        val extensionFunction = FunSpec.builder(extensionFunctionName)
//            .receiver(ClassName("androidx.navigation", "NavController"))
//            .addParameters(arguments.mapIndexed { index, ksClassDeclaration ->
//                ParameterSpec.builder("arg$index", ksClassDeclaration.asTypeName()).build()
//            })
//            .addStatement("this.navigate(\"$route\")")
//            .build()
//
//        val file = FileSpec.builder(packageName, "GeneratedNavigationExtensions")
//            .addFunction(extensionFunction)
//            .build()
//
//        try {
//            codeGenerator.createNewFile(
//                dependencies = Dependencies.ALL_FILES,
//                packageName = packageName,
//                fileName = "GeneratedNavigationExtensions"
//            ).writer().use { writer ->
//                file.writeTo(writer)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun KSClassDeclaration.asTypeName(): TypeName {
//        return ClassName.bestGuess(qualifiedName?.asString() ?: "")
//    }
}