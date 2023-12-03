package com.alireza.composeNavigation

//
//@AutoService(Processor::class)
//@SupportedOptions("kapt.kotlin.generated")
//class DestinationAnnotationProcessor : AbstractProcessor(){
//
//    private val lock = Any()
//    private lateinit var typeUtils: Types
//    private lateinit var elementUtils: Elements
//    private lateinit var filer: Filer
//    private lateinit var messager: Messager
//    private val factoryClasses = linkedMapOf<String, FactoryGroupedClasses>()
//
//    override fun init(processingEnv: ProcessingEnvironment) {
//        synchronized(lock) {
//            super.init(processingEnv)
//            typeUtils = processingEnv.typeUtils
//            elementUtils = processingEnv.elementUtils
//            filer = processingEnv.filer
//            messager = processingEnv.messager
//        }
//    }
//
//    override fun getSupportedSourceVersion(): SourceVersion {
//        return SourceVersion.latestSupported()
//    }
//
//    override fun getSupportedAnnotationTypes(): MutableSet<String> {
//        return mutableSetOf<String>().apply {
//            add(DestinationScreen::class.java.canonicalName)
//        }
//    }
//
//    /**
//     * https://hannesdorfmann.com/annotation-processing/annotationprocessing101/
//     * */
//
//
//    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
//
////        try {
//        for (element in roundEnv.getElementsAnnotatedWith(DestinationScreen::class.java)) {
//            if (element.kind == ElementKind.METHOD) {
//                val executableElement = element as ExecutableElement
//                generateExtensionFunction(executableElement)
//            }
//        }
//            return true
//
//    }
//
//    @Throws(ProcessingException::class)
//    private fun isValidClass(annotatedClass: FactoryAnnotatedClass) {
//
//        // Cast to TypeElement, has more type specific methods
//        val classElement: TypeElement = annotatedClass.typeElement
//
//        if (!classElement.modifiers.contains(Modifier.PUBLIC)) {
//            throw ProcessingException(
//                classElement, "The class %s is not public.",
//                classElement.qualifiedName.toString()
//            )
//        }
//
//        // Check if it's an abstract class
//        if (classElement.modifiers.contains(Modifier.ABSTRACT)) {
//            throw ProcessingException(
//                classElement,
//                "The class %s is abstract. You can't annotate abstract classes with @%",
//                classElement.qualifiedName.toString(), DestinationScreen::class.java.simpleName
//            )
//        }
//
//        // Check inheritance: Class must be child class as specified in @Factory.type()
//        val superClassElement: TypeElement =
//            elementUtils.getTypeElement(annotatedClass.qualifiedFactoryGroupName)
//        if (superClassElement.kind == ElementKind.INTERFACE) {
//            // Check interface implemented
//            if (!classElement.interfaces.contains(superClassElement.asType())) {
//                throw ProcessingException(
//                    classElement,
//                    "The class %s annotated with @%s must implement the interface %s",
//                    classElement.qualifiedName.toString(), DestinationScreen::class.java.simpleName,
//                    annotatedClass.qualifiedFactoryGroupName
//                )
//            }
//        } else {
//            // Check subclassing
//            var currentClass = classElement
//            while (true) {
//                val superClassType: TypeMirror = currentClass.superclass
//                if (superClassType.kind == TypeKind.NONE) {
//                    // Basis class (java.lang.Object) reached, so exit
//                    throw ProcessingException(
//                        classElement,
//                        "The class %s annotated with @%s must inherit from %s",
//                        classElement.qualifiedName.toString(), DestinationScreen::class.java.simpleName,
//                        annotatedClass.qualifiedFactoryGroupName
//                    )
//                }
//
//                if (superClassType.toString() == annotatedClass.qualifiedFactoryGroupName) {
//                    // Required super class found
//                    break
//                }
//
//                // Moving up in inheritance tree
//                currentClass = typeUtils.asElement(superClassType) as TypeElement
//            }
//
//        }
//
//        // Check if an empty public constructor is given
//        classElement.enclosedElements.forEach { enclosed ->
//            if (enclosed.kind == ElementKind.CONSTRUCTOR) {
//                val constructorElement: ExecutableElement = enclosed as ExecutableElement
//                if (constructorElement.parameters.isEmpty() && constructorElement.modifiers.contains(
//                        Modifier.PUBLIC
//                    )
//                ) {
//                    // Found an empty constructor
//                    return
//                }
//            }
//        }
//
//        // No empty constructor found
//        throw ProcessingException(
//            classElement,
//            "The class %s must provide an public empty default constructor",
//            classElement.qualifiedName.toString()
//        )
//    }
//
//    private fun error(e: Element?, msg: String, vararg args: Any) {
//        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, *args), e)
//    }
//
//    @OptIn(DelicateKotlinPoetApi::class)
//    private fun generateExtensionFunction(executableElement: ExecutableElement) {
//        val annotation = executableElement.getAnnotation(DestinationScreen::class.java)
//        val functionName = executableElement.simpleName.toString()
//        val packageName = processingEnv.elementUtils.getPackageOf(executableElement).toString()
//
//        val parameters = executableElement.parameters.map {
//            ParameterSpec.builder(it.toString(),
//               it.asType().asTypeName()).build()
//        }
//
//        val builder = FunSpec.builder(functionName)
//            .receiver(ClassName.bestGuess("androidx.navigation.NavGraphBuilder")) // Replace with your actual package and class names
//            .addParameters(parameters)
//            .addCode(
//                """
//                composable("${annotation.route}") {
//                    ${functionName}(${parameters.joinToString { it.name }})
//                }
//                """.trimIndent()
//            )
//
//        val file = FileSpec.builder(packageName, "${functionName}Extensions")
//            .addFunction(builder.build())
//            .addImport("androidx.navigation.compose", "composable")
//            .build()
//
//        file.writeTo(processingEnv.filer)
//    }
//}