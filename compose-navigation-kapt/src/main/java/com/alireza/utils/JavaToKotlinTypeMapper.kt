package com.alireza.utils

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.type.ArrayType
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types
import kotlin.reflect.KClass


object JavaToKotlinTypeMapper {

    private lateinit var processingEnv: ProcessingEnvironment
    fun init(processingEnv: ProcessingEnvironment) {
        this.processingEnv = processingEnv
    }

    private val JAVA_TO_KOTLIN_TYPE_MAP: Map<Class<*>, KClass<*>> = mapOf(
        Int::class.java to Int::class,
        Integer::class.java to Int::class,
        Long::class.java to Long::class,
        java.lang.Long::class.java to Long::class,
        Float::class.java to Float::class,
        java.lang.Float::class.java to Float::class,
        Double::class.java to Double::class,
        java.lang.Double::class.java to Double::class,
        Boolean::class.java to Boolean::class,
        java.lang.Boolean::class.java to Boolean::class,
        Char::class.java to Char::class,
        java.lang.Character::class.java to Char::class,
        Short::class.java to Short::class,
        java.lang.Short::class.java to Short::class,
        Byte::class.java to Byte::class,
        java.lang.Byte::class.java to Byte::class,
        String::class.java to String::class,
        java.util.List::class.java to List::class,
        java.util.Set::class.java to Set::class,
        java.util.Map::class.java to Map::class,
        Array<Any>::class.java to Array<Any>::class
        // Add more mappings as needed
    )

    fun mapToKotlinType(javaType: Class<*>): KClass<*> {
        return JAVA_TO_KOTLIN_TYPE_MAP[javaType] ?: run {
            // Handle array types
            if (javaType.isArray) {
                mapArrayToKotlinType(javaType)
            } else {
                // Handle other cases
                javaType.kotlin
            }
        }
    }

    fun mapToKotlinType(typeMirror: TypeMirror): KClass<*> {
        // Handle TypeMirror cases using processingEnv
val typeUtils = processingEnv.typeUtils
        return when {
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.INT)) -> Int::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.LONG)) -> Long::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.FLOAT)) -> Float::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.DOUBLE)) -> Double::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.BOOLEAN)) -> Boolean::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.CHAR)) -> Char::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.SHORT)) -> Short::class
            typeUtils.isSameType(typeMirror, typeUtils.getPrimitiveType(TypeKind.BYTE)) -> Byte::class

            typeUtils.isSameType(typeMirror, typeUtils.getDeclaredType(processingEnv.elementUtils.getTypeElement("java.lang.String"))) -> String::class
            // Arrays
            typeMirror.kind == TypeKind.ARRAY -> {
                val componentType = (typeMirror as ArrayType).componentType
                val kotlinComponentType = mapToKotlinType(componentType)
                (kotlinComponentType.qualifiedName + "Array")::class
            }

            // Lists and Collections
            typeUtils.isAssignable(typeMirror, typeUtils.getDeclaredType(processingEnv.elementUtils.getTypeElement(List::class.java.toString()))) ||
                    typeUtils.isAssignable(typeMirror, typeUtils.getDeclaredType(processingEnv.elementUtils.getTypeElement(Collection::class.java.toString()))) -> {
                val elementType = (typeMirror as DeclaredType).typeArguments[0]
                val kotlinElementType = mapToKotlinType(elementType)
                (List::class.qualifiedName + "<" + kotlinElementType.qualifiedName + ">")::class
            }

            // Sets
            typeUtils.isAssignable(typeMirror, typeUtils.getDeclaredType(processingEnv.elementUtils.getTypeElement(Set::class.java.toString()))) -> {
                val elementType = (typeMirror as DeclaredType).typeArguments[0]
                val kotlinElementType = mapToKotlinType(elementType)
                (Set::class.qualifiedName + "<" + kotlinElementType.qualifiedName + ">")::class
            }

            // Maps
            typeUtils.isAssignable(typeMirror, typeUtils.getDeclaredType(processingEnv.elementUtils.getTypeElement(Map::class.java.toString()))) -> {
                val keyType = (typeMirror as DeclaredType).typeArguments[0]
                val valueType = (typeMirror as DeclaredType).typeArguments[1]
                val kotlinKeyType = mapToKotlinType(keyType)
                val kotlinValueType = mapToKotlinType(valueType)
                (Map::class.qualifiedName + "<" + kotlinKeyType.qualifiedName + ", " + kotlinValueType.qualifiedName + ">")::class
            }

            // Handle other cases or return typeMirror.toString() if needed
            else -> {
                Class.forName(typeMirror.toString()).kotlin::class
            }
        }
        }
    }

    private fun mapArrayToKotlinType(arrayType: Class<*>): KClass<*> {
        // Handle array types using reflection
        val elementType = arrayType.componentType
        return if (elementType.isPrimitive) {
            // If it's a primitive array, map it using array reflection
            getKotlinArrayType(elementType)
        } else {
            // If it's an object array, use Kotlin array directly
            Array<Any>::class
        }
    }

    private fun getKotlinArrayType(javaComponentType: Class<*>): KClass<*> {
        val primitiveTypeName = javaComponentType.name
        return when (primitiveTypeName) {
            "int" -> IntArray::class
            "long" -> LongArray::class
            "float" -> FloatArray::class
            "double" -> DoubleArray::class
            "boolean" -> BooleanArray::class
            "char" -> CharArray::class
            "short" -> ShortArray::class
            "byte" -> ByteArray::class
            // Add more array type mappings as needed

            // If not a special case, return the array type as is
            else -> Array<Any>::class
        }
    }
