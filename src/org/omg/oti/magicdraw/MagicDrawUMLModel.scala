package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import org.omg.oti._

trait MagicDrawUMLModel extends UMLModel[MagicDrawUML] with MagicDrawUMLPackage {
  override protected def e: Uml#Model
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}