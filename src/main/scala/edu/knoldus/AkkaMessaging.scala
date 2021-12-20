package edu.knoldus

import akka.actor._
import akka.event.{Logging, LoggingAdapter}

case object StartConversation

object AkkaMessaging extends App {

  class PongActor extends Actor {
    val log: LoggingAdapter = Logging(context.system, this)

    def receive: PartialFunction[Any,Unit] = {
      case message: String => log.info(message)
        sender ! "Pong_Actor"

    }
  }
  class PingActor extends Actor {
    val log: LoggingAdapter = Logging(context.system, this)
    val pongActor: ActorRef = context.actorOf(Props[PongActor], name = "pong_Actor")

    def receive: PartialFunction[Any,Unit] = {
      case StartConversation => pongActor ! "Ping_Actor"
      case message: String => log.info(message)
    }
  }
  val actor = ActorSystem("PingPong")
  val pingActor = actor.actorOf(Props[PingActor], name = "ping_Actor")
  pingActor ! StartConversation
}
