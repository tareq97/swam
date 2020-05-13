import swam._
import text._
import runtime._
import formats.DefaultFormatters._
import cats.effect._
import java.nio.file.Paths

//val tcompiler = Compiler[IO]

//val engine = Engine[IO]

implicit val cs = IO.contextShift(scala.concurrent.ExecutionContext.global)

def instantiate(p: String): Instance[IO] =
  Blocker[IO].use { blocker =>
    for {
      engine <- Engine[IO](blocker)
      tcompiler <- Compiler[IO](blocker)
      m <- engine.compile(tcompiler.stream(Paths.get(p), true, blocker))
      i <- m.instantiate
    } yield i
  }.unsafeRunSync()

def time[T](t: => T): T = {
  val start = System.currentTimeMillis
  val res = t
  val end = System.currentTimeMillis
  println(s"Time: ${end - start}ms")
  res
}

val i = instantiate("fibo.wat")

val naive = i.exports.typed.function1[Long, Long]("naive").unsafeRunSync()
val clever = i.exports.typed.function1[Long, Long]("clever").unsafeRunSync()

