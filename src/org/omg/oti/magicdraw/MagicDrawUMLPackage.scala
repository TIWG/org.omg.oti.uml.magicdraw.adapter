package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPackage 
  extends UMLPackage[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLNamespace
  with MagicDrawUMLTemplateableElement {

  import ops._
  override protected def e: Uml#Package

  def getMagicDrawPackage = e
  
  def URI = e.getURI match {
    case null => None
    case ""   => None
    case uri  => Some( uri )
  }
  
  def importedPackage_packageImport: Set[UMLPackageImport[Uml]] = 
    e.get_packageImportOfImportedPackage.toSet[Uml#PackageImport]
  
  def mergedPackage_packageMerge: Set[UMLPackageMerge[Uml]] = 
    e.get_packageMergeOfMergedPackage.toSet[Uml#PackageMerge]
  
}