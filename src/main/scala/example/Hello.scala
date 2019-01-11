package example

import scalaj.http._
import java.net.URLEncoder
import java.util.Base64
import java.nio.charset.StandardCharsets

object Hello {
  def main(args: Array[String]): Unit = {
    val options = nextOption(Map(), args.toList)
    parseOptions(options) match {
      case Right(errMsg) =>
        println(errMsg)
      case Left((consumerKey, consumerKeySecret)) =>
        println(encodeCreds(consumerKey, consumerKeySecret))
    }
    //  val response: HttpResponse[String] = Http("https://api.twitter.com/1.1/search/tweets.json").
    //    param("q", "nasa").
    //    asString
    //  println(response.body)
  }

  def nextOption(opts : Map[Symbol, String], args: List[String]) : Map[Symbol, String] = {
    args match {
      case "--consumer-key" :: value :: tail =>
        nextOption(opts ++ Map('consumerKey -> value), tail)
      case "--consumer-key-secret" :: value :: tail =>
        nextOption(opts ++ Map('consumerKeySecret -> value), tail)
      case _ => opts
    }
  }

  def parseOptions(opts : Map[Symbol, String]) : Either[Tuple2[String, String], String] = {
    opts get 'consumerKey match {
      case None =>
        Right("consumer key is not provided")
      case Some(consumerKey) =>
        opts get 'consumerKeySecret match {
          case None =>
            Right("consumer key secret is not provided")
          case Some(consumerKeySecret) =>
            Left(Tuple2(consumerKey, consumerKeySecret))
        }
    }
  }

  def encodeCreds(consumerKey : String, consumerKeySecret : String) : String = {
    val encoding : String = "UTF-8"
    val encodedKey : String = URLEncoder.encode(consumerKey, encoding)
    val encodedKeySecret : String = URLEncoder.encode(consumerKeySecret, encoding)
    val bytes : Array[Byte] = s"$encodedKey:$encodedKeySecret".getBytes(StandardCharsets.UTF_8)
    Base64.getEncoder.encodeToString(bytes)
  }
}
