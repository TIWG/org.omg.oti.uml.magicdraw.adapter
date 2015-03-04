package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDurationConstraint 
  extends UMLDurationConstraint[MagicDrawUML]
  with MagicDrawUMLIntervalConstraint {

  override protected def e: Uml#DurationConstraint
  import ops._

  override def specification: Option[UMLDurationInterval[Uml]] =
    Option.apply( e.getSpecification )
    
  override def firstEvent =
    (e.isFirstEvent map ((b) => if (b) true else false)).toSet[Boolean]
    
}
