package in.varadharajan.stmdemo

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.parallel.immutable.ParRange
import scala.util.Random

object Account2 {
  def transfer(from: Account, to: Account, amount: Double): Status = Account2.synchronized {
    if(from.balance >= amount) {
      println(s"Transferring ${amount} from ${from.id} to ${to.id}")
      from.balance = from.balance - amount
      to.balance = to.balance + amount
      Success()
    }
    else {
      println(s"Not enough balance in ${from.id} account")
      Failure(s"Not enough balance in ${from.id} account")
    }
  }
}

object MultiThreaded1 {
  def main(args: Array[String]) = {
    val acc1 = Account(1, 100000)
    val acc2 = Account(2, 0)

    val threads: ParRange = (0 to 10).par
    threads.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(10))

    threads.foreach(threadIdx => {
      val randInt = Random.nextInt(2)
      val from = if(randInt == 1) acc1 else acc2
      val to = if(randInt == 1) acc2 else acc1
      Account2.transfer(from, to, 1)
    })

    println(s"Account1 : ${acc1.balance}")
    println(s"Account2 : ${acc2.balance}")
  }
}

