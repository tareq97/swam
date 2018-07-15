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
package text

import unresolved._

sealed trait Command

sealed trait TestModule extends Command
case class ValidModule(m: Module) extends TestModule
case class BinaryModule(id: Option[String], s: String) extends TestModule
case class QuotedModule(id: Option[String], s: String) extends TestModule

case class Register(s: String, id: Option[String]) extends Command

sealed trait Action extends Command
case class Invoke(id: Option[String], s: String, inst: Expr) extends Action
case class Get(id: Option[String], name: String) extends Action

sealed trait Assertion extends Command
case class AssertReturn(a: Action, result: Expr) extends Assertion
case class AssertReturnCanonicalNaN(a: Action) extends Assertion
case class AssertReturnArithmeticNaN(a: Action) extends Assertion
case class AssertTrap(a: Action, failure: String) extends Assertion
case class AssertMalformed(m: TestModule, failure: String) extends Assertion
case class AssertInvalid(m: TestModule, failure: String) extends Assertion
case class AssertUnlinkable(m: TestModule, failure: String) extends Assertion
case class AssertModuleTrap(m: TestModule, failure: String) extends Assertion
case class AssertExhaustion(a: Action, failure: String) extends Assertion

sealed trait Meta extends Command
case class Script(name: Option[String], script: Seq[Command]) extends Meta
case class Input(name: Option[String], s: String) extends Meta
case class Output(name: Option[String], s: Option[String]) extends Meta
