package com.alireza.composeNavigation

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier
import javax.lang.model.util.Elements

class FactoryGroupedClasses (private val qualifiedClassName:String){

    /**
     * Will be added to the name of the generated factory class
     */
    private val SUFFIX = "Factory"
    private val itemsMap = linkedMapOf<String, FactoryAnnotatedClass>()

    @Throws(ProcessingException::class)
    fun add(toInsert: FactoryAnnotatedClass) {
        if (itemsMap.containsKey(toInsert.Id))
            throw ProcessingException(itemsMap[toInsert.Id]?.typeElement)

        itemsMap[toInsert.Id] = toInsert
    }

    @Throws(IOException::class)
    fun generateCode(elementUtils: Elements, filer: Filer){
        val superClassName = elementUtils.getTypeElement(qualifiedClassName)
        val factoryClassName = superClassName.simpleName.toString() + SUFFIX
        val qualifiedFactoryClassName = qualifiedClassName + SUFFIX
        val pkg = elementUtils.getPackageOf(superClassName)
        val packageName = if (pkg.isUnnamed) null else pkg.qualifiedName.toString()

        val method = MethodSpec.methodBuilder("create")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(String::class.java, "id")
            .returns(TypeName.get(superClassName.asType()))

        // check if id is null
        method.beginControlFlow("if (id == null)")
            .addStatement("throw new IllegalArgumentException(\$S)", "id is null!")
            .endControlFlow()

        // Generate items map


        // Generate items map
        for (item in itemsMap.values) {
            method.beginControlFlow("if (\$S.equals(id))", item.Id)
                .addStatement("return new \$L()", item.typeElement.qualifiedName.toString())
                .endControlFlow()
        }

        method.addStatement("throw new IllegalArgumentException(\$S + id)", "Unknown id = ")

        val typeSpec = TypeSpec.classBuilder(factoryClassName).addMethod(method.build()).build()

        // Write file

        // Write file
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer)
    }

}