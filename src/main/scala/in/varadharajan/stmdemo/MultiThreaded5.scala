package in.varadharajan.stmdemo

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.parallel.immutable.ParRange
import scala.concurrent.stm._
import scala.util.Random

object Account6 {
  def transfer(from: AccountSTM, to: AccountSTM, amount: Double): Status = atomic { implicit txn =>
    if(from.balance() >= amount) {
      println(s"Transferring ${amount} from ${from.id} to ${to.id}")
      from.balance() = from.balance() - amount
      to.balance() = to.balance() + amount
      Success()
    }
    else {
      println(s"Not enough balance in ${from.id} account. Retrying..")
      retry
    }
  }
}

object MultiThreaded5 {
  def main(args: Array[String]) = {
    val acc1 = AccountSTM(1, Ref(100000))
    val acc2 = AccountSTM(2, Ref(0))

    val threads: ParRange = (0 to 10).par
    threads.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(10))

    threads.foreach(threadIdx => {
      val randInt = Random.nextInt(2)
      val from = if(randInt == 1) acc1 else acc2
      val to = if(randInt == 1) acc2 else acc1
      Account6.transfer(from, to, 1)
    })

    println(s"Account1 : ${acc1.balance.single()}")
    println(s"Account2 : ${acc2.balance.single()}")
  }
}

