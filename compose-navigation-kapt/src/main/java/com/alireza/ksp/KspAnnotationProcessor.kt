package com.alireza.ksp

import com.alireza.composeNavigationAnnotation.DestinationNavigation
import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import java.io.OutputStream

class KspAnnotationProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>)  : SymbolProcessor  {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val navigationVisitor = NavigationVisitor(resolver, codeGenerator)
        return resolver.getSymbolsWithAnnotation(DestinationNavigation::class.java.canonicalName)
            .filterIsInstance<KSFunctionDeclaration>()
            .toList()
            .also {
                it.forEach { function ->
                    function.accept(navigationVisitor, Unit)
                }
            }
    }

    override fun finish() {
        // Finalize processing, if needed
    }
}