package com.alireza.composeNavigation

import com.alireza.composeNavigationAnnotation.DestinationScreen
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypeException
import kotlin.reflect.KClass

class FactoryAnnotatedClass(private val classElement: TypeElement) {
    private var qualifiedSuperClassName: String
    private var simpleTypeName: String
    private var id: String

    init {
        val annotation: DestinationScreen = classElement.getAnnotation(DestinationScreen::class.java)
        id = annotation.route // Read the rout value (like "Calzone" or "Tiramisu")

        if (id.isEmpty()){
            throw IllegalArgumentException(
                String.format(
                    "id() in @%s for class %s is null or empty! that's not allowed",
                    DestinationScreen::class.java.simpleName, classElement.qualifiedName.toString()
                )
            )
        }

        // Get the full QualifiedTypeName
        try {
            val clazz: KClass<*> = annotation.annotationClass
            qualifiedSuperClassName = clazz.qualifiedName.toString()
            simpleTypeName = clazz.simpleName.toString()
        }catch (mte: MirroredTypeException){
            val classTypeMirror: DeclaredType = mte.typeMirror as DeclaredType
            val classTypeElement: TypeElement = classTypeMirror.asElement() as TypeElement
            qualifiedSuperClassName = classTypeElement.qualifiedName.toString()
            simpleTypeName = classTypeElement.simpleName.toString()
        }
    }


    /**
     * Get the id as specified in [Factory.id].
     * return the id
     */
    val Id: String
        get()= id

    /**
     * Get the full qualified name of the type specified in  [Factory.type].
     *
     * @return qualified name
     */
    val qualifiedFactoryGroupName: String
        get()= qualifiedSuperClassName


    /**
     * Get the simple name of the type specified in  [Factory.type].
     *
     * @return qualified name
     */
    val simpleFactoryGroupName: String
        get() = simpleTypeName

    /**
     * The original element that was annotated with @Factory
     */
    val typeElement: TypeElement
        get()= classElement
}