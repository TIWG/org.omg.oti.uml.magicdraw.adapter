package org.omg.oti.magicdraw

trait MagicDrawUML extends org.omg.oti.UML {
  
  override type Element = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
  override type Comment = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Comment
  override type Relationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Relationship
  override type DirectedRelationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship
  
  override type NamedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement
  override type Namespace = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Namespace
  
  override type ElementImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementImport
  override type PackageImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageImport
  override type PackageableElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageableElement
  
  override type Type = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type
  override type TypedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.TypedElement
  override type MultiplicityElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.MultiplicityElement
  
  override type Constraint = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint
  
  override type Dependency = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Dependency
  
  override type ValueSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification
  override type LiteralSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralSpecification
  override type LiteralNull = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralNull
  override type LiteralBoolean = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralBoolean
  override type LiteralInteger = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralInteger
  override type LiteralReal = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralReal
  override type LiteralString = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString
  override type LiteralUnlimitedNatural = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralUnlimitedNatural
  
  override type Expression = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Expression
  override type StringExpression = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.StringExpression
  override type OpaqueExpression = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.OpaqueExpression
  
  override type Interval = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.Interval
  
  override type Classifier = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier
  override type Generalization = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Generalization
  override type RedefinableElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.RedefinableElement
  
  override type Feature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Feature
  override type StructuralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.StructuralFeature
  override type BehavioralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.BehavioralFeature
  
  override type ConnectableElement = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.ConnectableElement
  override type Parameter = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Parameter
  
  override type Property = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property
  
  override type Operation = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation
  
  override type InstanceSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification
  override type Slot = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot
  override type InstanceValue = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue
  
  override type DataType = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DataType
  override type PrimitiveType = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PrimitiveType
  override type Enumeration = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration
  override type EnumerationLiteral = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral
  
  override type BehavioredClassifier = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.BehavioredClassifier
  
  override type StructuredClassifier = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.StructuredClassifier
  override type Connector = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.Connector
  override type ConnectorEnd = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.ConnectorEnd
  
  override type EncapsulatedClassifier = com.nomagic.uml2.ext.magicdraw.compositestructures.mdports.EncapsulatedClassifier
  override type Port = com.nomagic.uml2.ext.magicdraw.compositestructures.mdports.Port
  
  override type Class = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class
  
  override type Association = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association
  override type AssociationClass = com.nomagic.uml2.ext.magicdraw.classes.mdassociationclasses.AssociationClass
    
  override type Package = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package  
  override type PackageMerge = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageMerge
  override type Model = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model
  
  override type Profile = com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
  override type ProfileApplication = com.nomagic.uml2.ext.magicdraw.mdprofiles.ProfileApplication  
  override type Stereotype = com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype
  override type Extension = com.nomagic.uml2.ext.magicdraw.mdprofiles.Extension
  override type ExtensionEnd = com.nomagic.uml2.ext.magicdraw.mdprofiles.ExtensionEnd
  override type Image = com.nomagic.uml2.ext.magicdraw.mdprofiles.Image
  
  override type Actor = com.nomagic.uml2.ext.magicdraw.mdusecases.Actor
  override type UseCase = com.nomagic.uml2.ext.magicdraw.mdusecases.UseCase
  
  // MagicDraw-specific
  
  type Diagram = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Diagram
  type ElementValue = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementValue
}