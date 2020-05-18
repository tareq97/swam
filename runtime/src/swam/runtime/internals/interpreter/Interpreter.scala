/*
 * Copyright 2018 Lucas Satabin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package swam
package runtime
package internals
package interpreter

import instance._
import swam.runtime.coverage.Coverage
import trace._

import scala.util.control.NonFatal

import cats._
import cats.implicits._

/** Interpreter of low-level assembly. */
private[runtime] class Interpreter[F[_]](engine: Engine[F])(implicit F: MonadError[F, Throwable]) {

  private val conf = engine.conf
  private def makeFrame(instance: Instance[F]): Frame[F] = {
    val inner = new ThreadFrame[F](conf.stack, instance)
    engine.tracer match {
      case Some(tracer) => new TracingFrame[F](inner, tracer)
      case None         => inner
    }
  }

  def interpret(funcidx: Int, parameters: Vector[Long], instance: Instance[F]): F[Vector[Long]] = {
    // instantiate the top-level thread
    val thread = makeFrame(instance)
    // push the parameters in the stack
    thread.pushValues(parameters)
    // invoke the function
    invoke(thread, instance.funcs(funcidx)) match {
      case Continue     => run(thread)
      case Suspend(res) => res
      case Done(res)    => F.pure(res)
    }
  }

  def interpret(func: Function[F], parameters: Vector[Long], instance: Instance[F]): F[Vector[Long]] = {
    // instantiate the top-level thread
    val thread = makeFrame(instance)
    // push the parameters in the stack
    thread.pushValues(parameters)
    // invoke the function
    invoke(thread, func) match {
      case Continue     => run(thread)
      case Suspend(res) => res
      case Done(res)    => F.pure(res)
    }
  }

  def interpretInit(tpe: ValType, code: Array[AsmInst[F]], instance: Instance[F]): F[Vector[Long]] = {
    // instantiate the top-level thread
    val thread = makeFrame(instance)
    // invoke the function
    invoke(thread, new FunctionInstance(FuncType(Vector(), Vector(tpe)), Vector(), code, instance, None)) match {
      case Continue     => run(thread)
      case Suspend(res) => res
      case Done(res)    => F.pure(res)
    }
  }

<<<<<<< HEAD
  private def run(thread: Frame[F]): F[Option[Long]] = {
    var instCount:Int = 0
    val funcName: Option[String] = thread.functionName
    val modName : Option[String] = thread.moduleName

    println("Covered in Interpreter")
    def loop(): F[Option[Long]] = {
=======
  private def run(thread: Frame[F]): F[Vector[Long]] = {
    def loop(): F[Vector[Long]] = {
>>>>>>> upstream/master
      val inst = thread.fetch()
      instCount = instCount + 1
      println(s"Module name : ${thread.moduleName} ,  Function name : ${thread.functionName} , Instruction name : ${inst.getClass} , Instruction Count ${instCount}")
      inst.execute(thread) match {
        case Continue => loop()
        case Suspend(res) =>
          res.flatMap { res =>
            if (thread.isToplevel) {
              F.pure(res)
            } else {
              res.foreach(thread.pushValue(_))
              loop()
            }
          }
        case Done(res) =>
          if (thread.isToplevel) {

            F.pure(res)
          } else {
            res.foreach(thread.pushValue(_))
            loop()
          }
      }
    }

    try {
      val l = loop()
      println(s"Total count for function :::  $modName , $funcName ,$instCount")
      Coverage.interpretInstStore(modName, funcName, instCount)
      l
    } catch {
      case e: ArrayIndexOutOfBoundsException => F.raiseError(new StackOverflowException(thread, e))
      case e: TrapException                  => F.raiseError(e)
      case NonFatal(e)                       => F.raiseError(new TrapException(thread, "unexpected error during interpretation", e))
    }

  }

  private def invoke(thread: Frame[F], f: Function[F]): Continuation[F] =
    f match {
      case inst @ FunctionInstance(_, _, _, _, _) =>
        // parameters are on top of the stack
        thread.pushFrame(inst)
        Continue
      case _ =>
        // pop the parameters from the stack
        val rawparams = thread.popValues(f.tpe.params.size)
        // convert parameters according to the type defined for function parameters
        val params = f.tpe.params.zip(rawparams).map {
          case (tpe, v) => Value.fromRaw(tpe, v)
        }
        // invoke the host function with the parameters
        Suspend(f.invoke(params.toVector, thread.memoryOpt(0)).flatMap { res =>
          if (thread.isToplevel) {
            F.pure(res.map(Value.toRaw(_)))
          } else {
            res.foreach(v => thread.pushValue(Value.toRaw(v)))
            run(thread)
          }
        })
    }

}
