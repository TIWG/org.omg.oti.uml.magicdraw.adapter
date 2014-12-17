package org.omg.oti.magicdraw

trait MagicDrawUML extends org.omg.oti.UML {
  
  override type Element = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
  override type Comment = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Comment
  override type NamedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement
  override type Relationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Relationship
  override type DirectedRelationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship
  
  override type ElementImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementImport
  override type PackageImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageImport
  override type PackageMerge = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageMerge
  override type Generalization = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Generalization
  override type ProfileApplication = com.nomagic.uml2.ext.magicdraw.mdprofiles.ProfileApplication
    
  override type TypedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.TypedElement
  override type Feature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Feature
  override type BehavioralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.BehavioralFeature
  override type StructuralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.StructuralFeature
  override type PackageableElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageableElement
  override type InstanceSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification
  override type Type = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type
  override type Parameter = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Parameter
  override type ValueSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification
  override type InstanceValue = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue
  override type Package = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
  override type Slot = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot
  
  override type Stereotype = com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype
  override type Image = com.nomagic.uml2.ext.magicdraw.mdprofiles.Image
  
}