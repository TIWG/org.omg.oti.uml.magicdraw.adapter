package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import org.omg.oti.UMLProfile

trait MagicDrawUMLProfile extends UMLProfile[MagicDrawUML] with MagicDrawUMLPackage {
  override protected def e: Uml#Profile
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def metamodelReferences = e.getMetamodelReference.toSet[Uml#PackageImport]
  override def metaclassReferences = e.getMetaclassReference.toSet[Uml#ElementImport]
  
}