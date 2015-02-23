package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProfile 
  extends UMLProfile[MagicDrawUML]
  with MagicDrawUMLPackage {

  import ops._
  override protected def e: Uml#Profile
  
  override def metamodelReference = e.getMetamodelReference.toSet[Uml#PackageImport]
  override def metaclassReference = e.getMetaclassReference.toSet[Uml#ElementImport]
  
}