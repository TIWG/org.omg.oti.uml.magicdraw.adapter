package org.omg.oti.magicdraw

import scala.language.implicitConversions
import scala.reflect.runtime.universe
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Project

import org.omg.oti.UMLElement
import org.omg.oti.UMLNamedElement
import org.omg.oti.UMLStereotype
import org.omg.oti.UMLUtil

case class MagicDrawUMLUtil( val project: Project )
  extends MagicDrawUMLOps with UMLUtil[MagicDrawUML] { self =>

  type Uml = MagicDrawUML

  import self._

  override def cacheLookupOrUpdate( md: Uml#Element ) = md match {
    case null => null
    
    case _e: Uml#AssociationClass => cache.getOrElseUpdate(_e, new MagicDrawUMLAssociationClass() { override val e = _e; override val ops = self })
    case _e: Uml#Stereotype => cache.getOrElseUpdate(_e, new MagicDrawUMLStereotype() { override val e = _e; override val ops = self })
    case _e: Uml#Class => cache.getOrElseUpdate(_e, new MagicDrawUMLClass() { override val e = _e; override val ops = self })
    
    case _e: Uml#Extension => cache.getOrElseUpdate(_e, new MagicDrawUMLExtension() { override val e = _e; override val ops = self })        
    case _e: Uml#Association => cache.getOrElseUpdate(_e, new MagicDrawUMLAssociation() { override val e = _e; override val ops = self })
    
    case _e: Uml#Enumeration => cache.getOrElseUpdate(_e, new MagicDrawUMLEnumeration() { override val e = _e; override val ops = self })
    case _e: Uml#PrimitiveType => cache.getOrElseUpdate(_e, new MagicDrawUMLPrimitiveType() { override val e = _e; override val ops = self })
    case _e: Uml#DataType => cache.getOrElseUpdate(_e, new MagicDrawUMLDataType() { override val e = _e; override val ops = self })    
    
    case _e: Uml#Port => cache.getOrElseUpdate(_e, new MagicDrawUMLPort() { override val e = _e; override val ops = self })                
    case _e: Uml#ExtensionEnd => cache.getOrElseUpdate(_e, new MagicDrawUMLExtensionEnd() { override val e = _e; override val ops = self })        
    case _e: Uml#Property => cache.getOrElseUpdate(_e, new MagicDrawUMLProperty() { override val e = _e; override val ops = self })             
    
    case _e: Uml#Parameter => cache.getOrElseUpdate(_e, new MagicDrawUMLParameter() { override val e = _e; override val ops = self })        
    
    case _e: Uml#Operation => cache.getOrElseUpdate(_e, new MagicDrawUMLOperation() { override val e = _e; override val ops = self })        
            
    case _e: Uml#Comment => cache.getOrElseUpdate(_e, new MagicDrawUMLComment() { override val e = _e; override val ops = self })
        
    case _e: Uml#Connector=> cache.getOrElseUpdate(_e, new MagicDrawUMLConnector() { override val e = _e; override val ops = self })        
    case _e: Uml#ConnectorEnd => cache.getOrElseUpdate(_e, new MagicDrawUMLConnectorEnd() { override val e = _e; override val ops = self })   
    
    case _e: Uml#ElementImport => cache.getOrElseUpdate(_e, new MagicDrawUMLElementImport() { override val e = _e; override val ops = self })   
    case _e: Uml#PackageImport => cache.getOrElseUpdate(_e, new MagicDrawUMLPackageImport() { override val e = _e; override val ops = self })   
    case _e: Uml#PackageMerge => cache.getOrElseUpdate(_e, new MagicDrawUMLPackageMerge() { override val e = _e; override val ops = self })   
    case _e: Uml#ProfileApplication => cache.getOrElseUpdate(_e, new MagicDrawUMLProfileApplication() { override val e = _e; override val ops = self })
    
    case _e: Uml#Profile => cache.getOrElseUpdate(_e, new MagicDrawUMLProfile() { override val e = _e; override val ops = self })
    case _e: Uml#Model => cache.getOrElseUpdate(_e, new MagicDrawUMLModel() { override val e = _e; override val ops = self })
    case _e: Uml#Package => cache.getOrElseUpdate(_e, new MagicDrawUMLPackage() { override val e = _e; override val ops = self })    
    
    case _e: Uml#Slot => cache.getOrElseUpdate(_e, new MagicDrawUMLSlot() { override val e = _e; override val ops = self })    
    
    // MagicDraw-specific
    case _e: Uml#ElementValue => cache.getOrElseUpdate(_e, new MagicDrawUMLElementValue() { override val e = _e; override val ops = self })    
    case _e: Uml#InstanceValue => cache.getOrElseUpdate(_e, new MagicDrawUMLInstanceValue() { override val e = _e; override val ops = self })    
    case _e: Uml#EnumerationLiteral => cache.getOrElseUpdate(_e, new MagicDrawUMLEnumerationLiteral() { override val e = _e; override val ops = self })    
    case _e: Uml#InstanceSpecification => cache.getOrElseUpdate(_e, new MagicDrawUMLInstanceSpecification() { override val e = _e; override val ops = self })    

    case _e: Uml#Generalization => cache.getOrElseUpdate(_e, new MagicDrawUMLGeneralization() { override val e = _e; override val ops = self })    
    
    case _e: Uml#Constraint => cache.getOrElseUpdate(_e, new MagicDrawUMLConstraint() { override val e = _e; override val ops = self })    

    case _e: Uml#Dependency => cache.getOrElseUpdate(_e, new MagicDrawUMLDependency() { override val e = _e; override val ops = self })    

    case _e: Uml#LiteralNull => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralNull() { override val e = _e; override val ops = self })    
    case _e: Uml#LiteralBoolean => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralBoolean() { override val e = _e; override val ops = self })    
    case _e: Uml#LiteralInteger => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralInteger() { override val e = _e; override val ops = self })    
    case _e: Uml#LiteralReal => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralReal() { override val e = _e; override val ops = self })    
    case _e: Uml#LiteralString => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralString() { override val e = _e; override val ops = self })    
    case _e: Uml#LiteralUnlimitedNatural => cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralUnlimitedNatural() { override val e = _e; override val ops = self })    

    case _e: Uml#StringExpression => cache.getOrElseUpdate(_e, new MagicDrawUMLStringExpression() { override val e = _e; override val ops = self })    
    case _e: Uml#Expression => cache.getOrElseUpdate(_e, new MagicDrawUMLExpression() { override val e = _e; override val ops = self })    
    case _e: Uml#OpaqueExpression => cache.getOrElseUpdate(_e, new MagicDrawUMLOpaqueExpression() { override val e = _e; override val ops = self })    
        
    case _e: Uml#Interval => cache.getOrElseUpdate(_e, new MagicDrawUMLInterval() { override val e = _e; override val ops = self })    

    case _e: Uml#Image => cache.getOrElseUpdate(_e, new MagicDrawUMLImage() { override val e = _e; override val ops = self })    

    case _e: Uml#Actor => cache.getOrElseUpdate(_e, new MagicDrawUMLActor() { override val e = _e; override val ops = self })    
    case _e: Uml#UseCase => cache.getOrElseUpdate(_e, new MagicDrawUMLUseCase() { override val e = _e; override val ops = self })    

    // MagicDraw-specific    
    case _e: Uml#Diagram => cache.getOrElseUpdate(_e, new MagicDrawUMLDiagram() { override val e = _e; override val ops = self })    
    
    case _e => throw new MatchError(s" No case for ${_e.getHumanType}: ${_e.getID}")
    
  }
  
  implicit def umlActor( _e: Uml#Actor ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActor]
  implicit def umlAssociation( _e: Uml#Association ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAssociation]
  implicit def umlAssociationClass( _e: Uml#AssociationClass ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAssociationClass]
  implicit def umlBehavioralFeature( _e: Uml#BehavioralFeature ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehavioralFeature]
  implicit def umlBehavioredClassifier( _e: Uml#BehavioredClassifier ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehavioredClassifier]
  implicit def umlClass( _e: Uml#Class ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClass]
  implicit def umlClassifier( _e: Uml#Classifier ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClassifier]
  implicit def umlComment( _e: Uml#Comment ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLComment]
  implicit def umlConnectableElement( _e: Uml#ConnectableElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectableElement]
  implicit def umlConnector( _e: Uml#Connector ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnector]
  implicit def umlConnectorEnd( _e: Uml#ConnectorEnd ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectorEnd]
  implicit def umlConstraint( _e: Uml#Constraint ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConstraint]
  implicit def umlDataType( _e: Uml#DataType ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDataType]
  implicit def umlDependency( _e: Uml#Dependency ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDependency]
  implicit def umlDirectedRelationship( _e: Uml#DirectedRelationship ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDirectedRelationship]
  implicit def umlElement( _e: Uml#Element ) = cacheLookupOrUpdate(_e)
  implicit def umlElementImport( _e: Uml#ElementImport ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLElementImport]
  implicit def umlEncapsulatedClassifier( _e: Uml#EncapsulatedClassifier ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEncapsulatedClassifier]
  implicit def umlEnumeration( _e: Uml#Enumeration ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEnumeration]
  implicit def umlEnumerationLiteral( _e: Uml#EnumerationLiteral ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEnumerationLiteral]
  implicit def umlExpression( _e: Uml#Expression ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExpression]
  implicit def umlExtension( _e: Uml#Extension ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtension]
  implicit def umlExtensionEnd( _e: Uml#ExtensionEnd ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtensionEnd]
  implicit def umlFeature( _e: Uml#Feature ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFeature]
  implicit def umlGeneralization( _e: Uml#Generalization ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLGeneralization]
  implicit def umlImage( _e: Uml#Image ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLImage]
  implicit def umlInstanceSpecification( _e: Uml#InstanceSpecification ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInstanceSpecification]
  implicit def umlInstanceValue( _e: Uml#InstanceValue ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInstanceValue]
  implicit def umlInterval( _e: Uml#Interval ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInterval]
  implicit def umlLiteralBoolean( _e: Uml#LiteralBoolean) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralBoolean]
  implicit def umlLiteralInteger( _e: Uml#LiteralInteger) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralInteger]
  implicit def umlLiteralNull( _e: Uml#LiteralNull) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralNull]
  implicit def umlLiteralReal( _e: Uml#LiteralReal) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralReal]
  implicit def umlLiteralSpecification( _e: Uml#LiteralSpecification) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralSpecification]
  implicit def umlLiteralString( _e: Uml#LiteralString) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralString]
  implicit def umlLiteralUnlimitedNatural( _e: Uml#LiteralUnlimitedNatural) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralUnlimitedNatural]
  implicit def umlModel( _e: Uml#Model ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLModel]
  implicit def umlMultiplicityElement( _e: Uml#MultiplicityElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMultiplicityElement]
  implicit def umlNamedElement( _e: Uml#NamedElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLNamedElement]
  implicit def umlNamespace( _e: Uml#Namespace ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLNamespace]
  implicit def umlOpaqueExpression( _e: Uml#OpaqueExpression ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOpaqueExpression]
  implicit def umlOperation( _e: Uml#Operation ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOperation]
  implicit def umlPackage( _e: Uml#Package ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackage]
  implicit def umlPackageImport( _e: Uml#PackageImport ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageImport]
  implicit def umlPackageMerge( _e: Uml#PackageMerge ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageMerge]
  implicit def umlPackageableElement( _e: Uml#PackageableElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageableElement]
  implicit def umlParameter( _e: Uml#Parameter ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLParameter]
  implicit def umlPort( _e: Uml#Port ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPort]
  implicit def umlPrimitiveType( _e: Uml#PrimitiveType ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPrimitiveType]
  implicit def umlProfile( _e: Uml#Profile ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProfile]
  implicit def umlProfileApplication( _e: Uml#ProfileApplication ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProfileApplication]
  implicit def umlProperty( _e: Uml#Property ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProperty]
  implicit def umlRedefinableElement( _e: Uml#RedefinableElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRedefinableElement]
  implicit def umlRelationship( _e: Uml#Relationship ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRelationship]
  implicit def umlSlot( _e: Uml#Slot ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSlot]
  implicit def umlStereotype( _e: Uml#Stereotype ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStereotype]
  implicit def umlStringExpression( _e: Uml#StringExpression ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStringExpression]
  implicit def umlStructuralFeature( _e: Uml#StructuralFeature ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuralFeature]
  implicit def umlStructuredClassifier( _e: Uml#StructuredClassifier ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuredClassifier]
  implicit def umlType( _e: Uml#Type ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLType]
  implicit def umlTypedElement( _e: Uml#TypedElement ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTypedElement]
  implicit def umlUseCase( _e: Uml#UseCase ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLUseCase]
  implicit def umlValueSpecification( _e: Uml#ValueSpecification ) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLValueSpecification]

  // MagicDraw-specific
  
  implicit def umlDiagram( _e: Uml#Diagram) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDiagram]
  implicit def umlElementValue( _e: Uml#ElementValue) = cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLElementValue]

  implicit def umlMagicDrawUMLElement( _e: UMLElement[Uml] ) = _e match {
    case mdE: MagicDrawUMLElement => mdE
  }
  
  implicit def umlMagicDrawUMLStereotype( _e: UMLStereotype[Uml] ) = _e match {
    case mdE: MagicDrawUMLStereotype=> mdE
  }
  
  val element2id = scala.collection.mutable.HashMap[UMLElement[Uml], Try[String]]()

  val MD_crule0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, is: MagicDrawUMLInstanceSpecification ) if ( is.isMagicDrawUMLAppliedStereotypeInstance ) =>
      Success( ownerID + "_" + xmlSafeID( cf.getName ) + ".appliedStereotypeInstance" )
  }

  val MD_crule1a0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, ev: MagicDrawUMLElementValue ) =>
      ev.element match {
        case None => Failure( illegalElementException( "ElementValue without Element is not supported", ev ) )
        case Some( nev: UMLNamedElement[Uml] ) =>
          nev.name match {
            case None      => Failure( illegalElementException( "ElementValue must refer to a named NamedElement", ev ) )
            case Some( n ) => Success( ownerID + "_" + xmlSafeID( cf.getName ) + "." + n )
          }
        case Some( ev: UMLElement[Uml] ) =>
          Failure( illegalElementException( "ElementValue refers to an Element that is not a NamedElement!", ev ) )
      }
  }

  protected val elementRules = List( rule0 )
  protected val containmentRules = List( MD_crule0, crule1, MD_crule1a0, crule1a, crule1b, crule2, crule3, crule4, crule5, crule6 )

}