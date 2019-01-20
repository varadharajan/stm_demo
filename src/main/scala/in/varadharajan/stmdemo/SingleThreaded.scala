package in.varadharajan.stmdemo

case class Account(id: Int, var balance: Double) {
  assert(balance >= 0, "Balance can't be negative")
}

sealed trait Status
case class Success(msg: String = "Done!") extends Status
case class Failure(msg: String) extends Status

object Account1 {
  def transfer(from: Account, to: Account, amount: Double): Status = {
    if(from.balance >= amount) {
      println(s"Transferring ${amount} from ${from.id} to ${to.id}")
      from.balance = from.balance - amount
      to.balance = to.balance + amount
      Success()
    }
    else Failure(s"Not enough balance in ${from.id} account")
  }
}

object SingleThreaded {
  def main(args: Array[String]) = {
    val acc1 = Account(1, 100)
    val acc2 = Account(2, 0)
    Account1.transfer(acc1, acc2, 50)
    println(acc2.balance)
  }
}
