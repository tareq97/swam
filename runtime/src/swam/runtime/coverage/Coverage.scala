//import scala.collection.immutable.HashMap
//import scala.collection.mutable
package swam
package runtime
package coverage

object Coverage{

  var totalInst : Int = 0
  var InstrucionCov_C: Map[(Option[String], Option[String], java.lang.String, GlobalIdx), GlobalIdx] = Map()
  //var InstrucionCov_I: Map[(Option[String], Option[String], Name, GlobalIdx), GlobalIdx] = Map()
  var functionCov_C: Map[(Option[String],Option[String]),GlobalIdx] = Map()
  var functionCov_I: Map[(Option[String],Option[String]),GlobalIdx] = Map()
  //still working mapping of instructions per function after compilation of module
  def apply (moduleName:Option[String],functionName:Option[String],instructionName:java.lang.String,instructionNo:GlobalIdx): Map[(Option[String],Option[String],java.lang.String,Int),Int]= {
    //println("This is here 1\n")
    InstrucionCov_C += (moduleName, functionName, instructionName, instructionNo) -> 0
    InstrucionCov_C
  }

  //no of instructions per function after compilation of module
  def apply(moduleName:Option[String],functionName:Option[String],instructionNo:Int): Map[(Option[String],Option[String]),Int] ={
    functionCov_C += (moduleName, functionName) -> instructionNo
    functionCov_C
  }

  //no of instructions executed per function after calling a function in interpreter
  def interpretInstStore(moduleName:Option[String],functionName:Option[String],instructionNo:Int) = {
    functionCov_I += (moduleName, functionName) -> instructionNo
    functionCov_I
  }

  //Total Instruction compiled in a module
  /*def totalInst(total:Int) : Int = {
    totalInst = total
    totalInst
  }*/

  private def checkFunctionCov() = {
    for ((k,v) <- functionCov_C) {
      val keys = k
      //println(s"${keys._1} : ${keys._2} : ${v}")
      val executedInst: Float = functionCov_I.get(keys) match {
        case Some(z) => {z}
        case None => 0
      }
      //println(executedInst)
      //println(v)
      val cov: Float = (executedInst/v)
      println(s"Coverage for Module Name : ${keys._1 match {
        case Some(value) => value
        case None => s"No Module name"
      }} , Function Name: ${keys._2 match {
        case Some(value) => value
        case None => s"No Function name"
      }} , Covered Inst : ${
        if(cov*100 > 100)
          100
        else cov*100
      }/100")
    }
  }

  def displayCoverage = {
    /*for((k,v) <- functionCov_I){
      println(s"${k._1} :::::::: ${k._2}  :::::: ${v}" )
    }*/
    checkFunctionCov()
  }
}