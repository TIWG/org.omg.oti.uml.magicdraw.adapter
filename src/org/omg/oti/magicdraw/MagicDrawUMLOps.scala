package org.omg.oti.magicdraw

import scala.language.postfixOps
import scala.reflect.runtime.universe._
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import org.omg.oti._
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import org.omg.oti.canonicalXMI.BuiltInDocument
import java.net.URI
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper 

trait MagicDrawUMLOps extends EarlyInit[MagicDrawUMLOps] with UMLOps[MagicDrawUML] {

  val project: Project

  import scala.reflect._

  val MD_ELEMENT: TypeTag[MagicDrawUML#Element] = typeTag[MagicDrawUML#Element]
  override implicit val ELEMENT = MD_ELEMENT

  val MD_COMMENT: TypeTag[MagicDrawUML#Comment] = typeTag[MagicDrawUML#Comment]
  override implicit val COMMENT = MD_COMMENT

  val MD_RELATIONSHIP: TypeTag[MagicDrawUML#Relationship] = typeTag[MagicDrawUML#Relationship]
  override implicit val RELATIONSHIP = MD_RELATIONSHIP

  val MD_DIRECTED_RELATIONSHIP: TypeTag[MagicDrawUML#DirectedRelationship] = typeTag[MagicDrawUML#DirectedRelationship]
  override implicit val DIRECTED_RELATIONSHIP = MD_DIRECTED_RELATIONSHIP

  val MD_NAMED_ELEMENT: TypeTag[MagicDrawUML#NamedElement] = typeTag[MagicDrawUML#NamedElement]
  override implicit val NAMED_ELEMENT = MD_NAMED_ELEMENT

  val MD_NAMESPACE: TypeTag[MagicDrawUML#Namespace] = typeTag[MagicDrawUML#Namespace]
  override implicit val NAMESPACE = MD_NAMESPACE

  val MD_ELEMENT_IMPORT: TypeTag[MagicDrawUML#ElementImport] = typeTag[MagicDrawUML#ElementImport]
  override implicit val ELEMENT_IMPORT = MD_ELEMENT_IMPORT

  val MD_PACKAGE_IMPORT: TypeTag[MagicDrawUML#PackageImport] = typeTag[MagicDrawUML#PackageImport]
  override implicit val PACKAGE_IMPORT = MD_PACKAGE_IMPORT

  val MD_PACKAGEABLE_ELEMENT: TypeTag[MagicDrawUML#PackageableElement] = typeTag[MagicDrawUML#PackageableElement]
  override implicit val PACKAGEABLE_ELEMENT = MD_PACKAGEABLE_ELEMENT

  val MD_TYPE: TypeTag[MagicDrawUML#Type] = typeTag[MagicDrawUML#Type]
  override implicit val TYPE = MD_TYPE

  val MD_TYPED_ELEMENT: TypeTag[MagicDrawUML#TypedElement] = typeTag[MagicDrawUML#TypedElement]
  override implicit val TYPED_ELEMENT = MD_TYPED_ELEMENT

  val MD_MULTIPLICITY_ELEMENT: TypeTag[MagicDrawUML#MultiplicityElement] = typeTag[MagicDrawUML#MultiplicityElement]
  override implicit val MULTIPLICITY_ELEMENT = MD_MULTIPLICITY_ELEMENT

  val MD_CONSTRAINT: TypeTag[MagicDrawUML#Constraint] = typeTag[MagicDrawUML#Constraint]
  override implicit val CONSTRAINT = MD_CONSTRAINT

  val MD_DEPENDENCY: TypeTag[MagicDrawUML#Dependency] = typeTag[MagicDrawUML#Dependency]
  override implicit val DEPENDENCY = MD_DEPENDENCY

  val MD_VALUE_SPECIFICATION: TypeTag[MagicDrawUML#ValueSpecification] = typeTag[MagicDrawUML#ValueSpecification]
  override implicit val VALUE_SPECIFICATION = MD_VALUE_SPECIFICATION

  val MD_LITERAL_SPECIFICATION: TypeTag[MagicDrawUML#LiteralSpecification] = typeTag[MagicDrawUML#LiteralSpecification]
  override implicit val LITERAL_SPECIFICATION = MD_LITERAL_SPECIFICATION

  val MD_LITERAL_NULL: TypeTag[MagicDrawUML#LiteralNull] = typeTag[MagicDrawUML#LiteralNull]
  override implicit val LITERAL_NULL = MD_LITERAL_NULL

  val MD_LITERAL_BOOLEAN: TypeTag[MagicDrawUML#LiteralBoolean] = typeTag[MagicDrawUML#LiteralBoolean]
  override implicit val LITERAL_BOOLEAN = MD_LITERAL_BOOLEAN

  val MD_LITERAL_INTEGER: TypeTag[MagicDrawUML#LiteralInteger] = typeTag[MagicDrawUML#LiteralInteger]
  override implicit val LITERAL_INTEGER = MD_LITERAL_INTEGER

  val MD_LITERAL_REAL: TypeTag[MagicDrawUML#LiteralReal] = typeTag[MagicDrawUML#LiteralReal]
  override implicit val LITERAL_REAL = MD_LITERAL_REAL

  val MD_LITERAL_STRING: TypeTag[MagicDrawUML#LiteralString] = typeTag[MagicDrawUML#LiteralString]
  override implicit val LITERAL_STRING = MD_LITERAL_STRING

  val MD_LITERAL_UNLIMITED_NATURAL: TypeTag[MagicDrawUML#LiteralUnlimitedNatural] = typeTag[MagicDrawUML#LiteralUnlimitedNatural]
  override implicit val LITERAL_UNLIMITED_NATURAL = MD_LITERAL_UNLIMITED_NATURAL

  val MD_EXPRESSION: TypeTag[MagicDrawUML#Expression] = typeTag[MagicDrawUML#Expression]
  override implicit val EXPRESSION = MD_EXPRESSION

  val MD_STRING_EXPRESSION: TypeTag[MagicDrawUML#StringExpression] = typeTag[MagicDrawUML#StringExpression]
  override implicit val STRING_EXPRESSION = MD_STRING_EXPRESSION

  val MD_OPAQUE_EXPRESSION: TypeTag[MagicDrawUML#OpaqueExpression] = typeTag[MagicDrawUML#OpaqueExpression]
  override implicit val OPAQUE_EXPRESSION = MD_OPAQUE_EXPRESSION

  val MD_INTERVAL: TypeTag[MagicDrawUML#Interval] = typeTag[MagicDrawUML#Interval]
  override implicit val INTERVAL = MD_INTERVAL

  val MD_CLASSIFIER: TypeTag[MagicDrawUML#Classifier] = typeTag[MagicDrawUML#Classifier]
  override implicit val CLASSIFIER = MD_CLASSIFIER

  val MD_GENERALIZATION: TypeTag[MagicDrawUML#Generalization] = typeTag[MagicDrawUML#Generalization]
  override implicit val GENERALIZATION = MD_GENERALIZATION

  val MD_REDEFINABLE_ELEMENT: TypeTag[MagicDrawUML#RedefinableElement] = typeTag[MagicDrawUML#RedefinableElement]
  override implicit val REDEFINABLE_ELEMENT = MD_REDEFINABLE_ELEMENT

  val MD_FEATURE: TypeTag[MagicDrawUML#Feature] = typeTag[MagicDrawUML#Feature]
  override implicit val FEATURE = MD_FEATURE

  val MD_STRUCTURAL_FEATURE: TypeTag[MagicDrawUML#StructuralFeature] = typeTag[MagicDrawUML#StructuralFeature]
  override implicit val STRUCTURAL_FEATURE = MD_STRUCTURAL_FEATURE

  val MD_BEHAVIORAL_FEATURE: TypeTag[MagicDrawUML#BehavioralFeature] = typeTag[MagicDrawUML#BehavioralFeature]
  override implicit val BEHAVIORAL_FEATURE = MD_BEHAVIORAL_FEATURE

  val MD_CONNECTABLE_ELEMENT: TypeTag[MagicDrawUML#ConnectableElement] = typeTag[MagicDrawUML#ConnectableElement]
  override implicit val CONNECTABLE_ELEMENT = MD_CONNECTABLE_ELEMENT

  val MD_PARAMETER: TypeTag[MagicDrawUML#Parameter] = typeTag[MagicDrawUML#Parameter]
  override implicit val PARAMETER = MD_PARAMETER

  val MD_PROPERTY: TypeTag[MagicDrawUML#Property] = typeTag[MagicDrawUML#Property]
  override implicit val PROPERTY = MD_PROPERTY

  val MD_OPERATION: TypeTag[MagicDrawUML#Operation] = typeTag[MagicDrawUML#Operation]
  override implicit val OPERATION = MD_OPERATION

  val MD_INSTANCE_SPECIFICATION: TypeTag[MagicDrawUML#InstanceSpecification] = typeTag[MagicDrawUML#InstanceSpecification]
  override implicit val INSTANCE_SPECIFICATION = MD_INSTANCE_SPECIFICATION

  val MD_SLOT: TypeTag[MagicDrawUML#Slot] = typeTag[MagicDrawUML#Slot]
  override implicit val SLOT = MD_SLOT

  val MD_INSTANCE_VALUE: TypeTag[MagicDrawUML#InstanceValue] = typeTag[MagicDrawUML#InstanceValue]
  override implicit val INSTANCE_VALUE = MD_INSTANCE_VALUE

  val MD_DATA_TYPE: TypeTag[MagicDrawUML#DataType] = typeTag[MagicDrawUML#DataType]
  override implicit val DATA_TYPE = MD_DATA_TYPE

  val MD_PRIMITIVE_TYPE: TypeTag[MagicDrawUML#PrimitiveType] = typeTag[MagicDrawUML#PrimitiveType]
  override implicit val PRIMITIVE_TYPE = MD_PRIMITIVE_TYPE

  val MD_ENUMERATION: TypeTag[MagicDrawUML#Enumeration] = typeTag[MagicDrawUML#Enumeration]
  override implicit val ENUMERATION = MD_ENUMERATION

  val MD_ENUMERATION_LITERAL: TypeTag[MagicDrawUML#EnumerationLiteral] = typeTag[MagicDrawUML#EnumerationLiteral]
  override implicit val ENUMERATION_LITERAL = MD_ENUMERATION_LITERAL

  val MD_BEHAVIORED_CLASSIFIER: TypeTag[MagicDrawUML#BehavioredClassifier] = typeTag[MagicDrawUML#BehavioredClassifier]
  override implicit val BEHAVIORED_CLASSIFIER = MD_BEHAVIORED_CLASSIFIER

  val MD_STRUCTURED_CLASSIFIER: TypeTag[MagicDrawUML#StructuredClassifier] = typeTag[MagicDrawUML#StructuredClassifier]
  override implicit val STRUCTURED_CLASSIFIER = MD_STRUCTURED_CLASSIFIER

  val MD_CONNECTOR: TypeTag[MagicDrawUML#Connector] = typeTag[MagicDrawUML#Connector]
  override implicit val CONNECTOR = MD_CONNECTOR

  val MD_CONNECTOR_END: TypeTag[MagicDrawUML#ConnectorEnd] = typeTag[MagicDrawUML#ConnectorEnd]
  override implicit val CONNECTOR_END = MD_CONNECTOR_END

  val MD_ENCAPSULATED_CLASSIFIER: TypeTag[MagicDrawUML#EncapsulatedClassifier] = typeTag[MagicDrawUML#EncapsulatedClassifier]
  override implicit val ENCAPSULATED_CLASSIFIER = MD_ENCAPSULATED_CLASSIFIER

  val MD_PORT: TypeTag[MagicDrawUML#Port] = typeTag[MagicDrawUML#Port]
  override implicit val PORT = MD_PORT

  val MD_CLASS: TypeTag[MagicDrawUML#Class] = typeTag[MagicDrawUML#Class]
  override implicit val CLASS = MD_CLASS

  val MD_ASSOCIATION: TypeTag[MagicDrawUML#Association] = typeTag[MagicDrawUML#Association]
  override implicit val ASSOCIATION = MD_ASSOCIATION

  val MD_ASSOCIATION_CLASS: TypeTag[MagicDrawUML#AssociationClass] = typeTag[MagicDrawUML#AssociationClass]
  override implicit val ASSOCIATION_CLASS = MD_ASSOCIATION_CLASS

  val MD_PACKAGE: TypeTag[MagicDrawUML#Package] = typeTag[MagicDrawUML#Package]
  override implicit val PACKAGE = MD_PACKAGE

  val MD_PACKAGE_MERGE: TypeTag[MagicDrawUML#PackageMerge] = typeTag[MagicDrawUML#PackageMerge]
  override implicit val PACKAGE_MERGE = MD_PACKAGE_MERGE

  val MD_MODEL: TypeTag[MagicDrawUML#Model] = typeTag[MagicDrawUML#Model]  
  override implicit val MODEL = MD_MODEL
  
  val MD_PROFILE: TypeTag[MagicDrawUML#Profile] = typeTag[MagicDrawUML#Profile]
  override implicit val PROFILE = MD_PROFILE

  val MD_PROFILE_APPLICATION: TypeTag[MagicDrawUML#ProfileApplication] = typeTag[MagicDrawUML#ProfileApplication]
  override implicit val PROFILE_APPLICATION = MD_PROFILE_APPLICATION

  val MD_STEREOTYPE: TypeTag[MagicDrawUML#Stereotype] = typeTag[MagicDrawUML#Stereotype]
  override implicit val STEREOTYPE = MD_STEREOTYPE

  val MD_EXTENSION: TypeTag[MagicDrawUML#Extension] = typeTag[MagicDrawUML#Extension]
  override implicit val EXTENSION = MD_EXTENSION

  val MD_EXTENSION_END: TypeTag[MagicDrawUML#ExtensionEnd] = typeTag[MagicDrawUML#ExtensionEnd]
  override implicit val EXTENSION_END = MD_EXTENSION_END

  val MD_IMAGE: TypeTag[MagicDrawUML#Image] = typeTag[MagicDrawUML#Image]
  override implicit val IMAGE = MD_IMAGE
  
  val MD_ACTOR: TypeTag[MagicDrawUML#Actor] = typeTag[MagicDrawUML#Actor]
  override implicit val ACTOR = MD_ACTOR

  val MD_USECASE: TypeTag[MagicDrawUML#UseCase] = typeTag[MagicDrawUML#UseCase]
  override implicit val USECASE = MD_USECASE

  // MagicDraw-specific

  implicit val DIAGRAM: TypeTag[MagicDrawUML#Diagram] = typeTag[MagicDrawUML#Diagram]

  implicit val ELEMENT_VALUE: TypeTag[MagicDrawUML#ElementValue] = typeTag[MagicDrawUML#ElementValue]

  override val OTI_SPECIFICATION_ROOT_S = 
    StereotypesHelper.getProfile( project, "OTI" ) match {
      case null => None
      case pf => Option.apply(StereotypesHelper.getStereotype( project, "SpecificationRoot", pf ))
    }
   
  override val OTI_SPECIFICATION_ROOT_packageURI = OTI_SPECIFICATION_ROOT_S match {
    case None => None
    case Some( s ) => Option.apply(StereotypesHelper.getPropertyByName(s, "packageURI"))
  }

  override val OTI_ID_S =    
    StereotypesHelper.getProfile( project, "OTI" ) match {
      case null => None
      case pf => Option.apply(StereotypesHelper.getStereotype( project, "OTI", pf ))
    }
  
  override val OTI_ID_uuid = OTI_ID_S match {
    case None => None
    case Some( s ) => Option.apply(StereotypesHelper.getPropertyByName(s, "uuid"))
  }
    
  override val SLOT_VALUE = com.nomagic.uml2.ext.magicdraw.metadata.UMLPackage.eINSTANCE.getSlot_Value

  val MD_OTI_ValidationSuite = MagicDrawValidationDataResults.lookupValidationSuite( project, "*::MagicDrawOTIValidation")
  
  val MD_OTI_ValidationConstraint_NotOTISpecificationRoot = MD_OTI_ValidationSuite match {
    case None => None
    case Some( vInfo ) => MagicDrawValidationDataResults.lookupValidationConstraint( vInfo, "*::NotOTISpecificationRoot" )
  }
  
  // val stdProfile = ModelHelper.getUMLStandardProfile(project)
  lazy val MDBuiltInPrimitiveTypes = {
    
    val mdPrimitiveTypesPkg = 
      umlPackage( project.getElementByID("_12_0EAPbeta_be00301_1157529392394_202602_1").asInstanceOf[MagicDrawUML#Package] )
 
    val mdPrimitiveTypesExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdPrimitiveTypesPkg) ++ mdPrimitiveTypesPkg.ownedTypes.toSet
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/PrimitiveTypes/20100901"),
        nsPrefix="PrimitiveTypes",
        scope=mdPrimitiveTypesPkg,
        builtInURI=new URI("http://www.omg.org/spec/PrimitiveTypes/20100901"),
        builtInExtent=mdPrimitiveTypesExtent )( this )
  }
  
  lazy val MDBuiltInUML = {
    
    val mdUMLPkg = 
      umlPackage( project.getElementByID("_9_0_be00301_1108053761194_467635_11463").asInstanceOf[MagicDrawUML#Package] )
 
    val mdUMLExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdUMLPkg) ++ mdUMLPkg.ownedTypes.selectByKindOf { case mc: UMLClass[MagicDrawUML] => mc }
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/UML/20131001"),
        nsPrefix="uml",
        scope=mdUMLPkg,
        builtInURI=new URI("http://www.omg.org/spec/UML/20131001"),
        builtInExtent=mdUMLExtent )( this )
  }
  
  lazy val MDBuiltInStandardProfile = {
    
    val mdStandardProfile = 
      umlProfile( project.getElementByID("_9_0_be00301_1108050582343_527400_10847").asInstanceOf[MagicDrawUML#Profile] )
 
    val mdStandardProfileClassifiers = mdStandardProfile.ownedTypes.selectByKindOf { case cls: UMLClassifier[MagicDrawUML] => cls }
    val mdStandardProfileFeatures = mdStandardProfileClassifiers flatMap (_.features)
    val mdStandardProfileExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdStandardProfile) ++ mdStandardProfileClassifiers ++ mdStandardProfileFeatures
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/UML/20131001/StandardProfile"),
        nsPrefix="StandardProfile",
        scope=mdStandardProfile,
        builtInURI=new URI("http://www.omg.org/spec/UML/20131001/StandardProfile"),
        builtInExtent=mdStandardProfileExtent )( this )
  }
}