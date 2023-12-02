package com.alireza.ksp

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Nullability
import com.google.devtools.ksp.symbol.Variance
import java.io.OutputStream

class Visitor(private val file: OutputStream,val logger: KSPLogger):KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        if (classDeclaration.classKind != ClassKind.INTERFACE){
            logger.error("Only interface can be annotated with @Function", classDeclaration)
            return
        }

    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        // Process function declaration
        val functionName = function.simpleName.asString()
        function.parameters
        val returnType = function.returnType?.resolve()?.declaration?.simpleName?.asString()

        logger.error("Found function:$functionName -> ${function.parameters} with return type: $returnType")

        // You can perform additional processing or code generation here based on the function

    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: Unit) {
        when (val variance: Variance = typeArgument.variance) {
            Variance.STAR -> {
                file += "*"
                return
            }
            Variance.COVARIANT, Variance.CONTRAVARIANT -> {
                file += variance.label // 'out' or 'in'
                file += " "
            }
            Variance.INVARIANT -> {
                // do nothing
            }
        }

        val resolvedType: KSType? = typeArgument.type?.resolve()
        file += resolvedType?.declaration?.qualifiedName?.asString() ?: run {
            logger.error("Invalid type argument", typeArgument)
            return
        }

        // Generating nested generic parameters if any
        val genericArguments: List<KSTypeArgument> = typeArgument.type?.element?.typeArguments ?: emptyList()
        visitTypeArguments(genericArguments)

        // Handling nullability
        file += if (resolvedType?.nullability == Nullability.NULLABLE) "?" else ""
    }

    private fun visitTypeArguments(typeArguments: List<KSTypeArgument>) {
        if (typeArguments.isNotEmpty()) {
            file += "<"
            typeArguments.forEachIndexed { i, arg ->
                visitTypeArgument(arg, data = Unit)
                if (i < typeArguments.lastIndex) file += ", "
            }
            file += ">"
        }
    }

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }
}