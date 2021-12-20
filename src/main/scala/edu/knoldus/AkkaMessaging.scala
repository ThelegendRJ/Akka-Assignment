package edu.knoldus

import akka.actor._
import akka.event.{Logging, LoggingAdapter}

case object StartConversation

object AkkaMessaging extends App {
  val actorSystem = ActorSystem("PingPongSystem")
  class PongActor extends Actor {
    val log: LoggingAdapter = Logging(context.system, this)

    def receive: PartialFunction[Any,Unit] = {
      case message: String => log.info(message)
        sender ! "PongActor"

    }
  }
  class PingActor extends Actor {
    val log: LoggingAdapter = Logging(context.system, this)
    val pongActor: ActorRef = context.actorOf(Props[PongActor], name = "pongActor")

    def receive: PartialFunction[Any,Unit] = {
      case StartConversation => pongActor ! "PingActor"
      case message: String => log.info(message)
    }
  }

  val pingActor = actorSystem.actorOf(Props[PingActor], name = "pingActor")
  pingActor ! StartConversation
}
