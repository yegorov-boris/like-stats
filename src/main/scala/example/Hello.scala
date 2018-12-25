package example

import scalaj.http._
import java.net.URLEncoder
import java.util.Base64
import java.nio.charset.StandardCharsets

object Hello extends App {
  val encoding : String = "UTF-8"
  val consumerKey : String = URLEncoder.encode("foo", encoding)
  val consumerKeySecret : String = URLEncoder.encode("bar", encoding)
  val bytes : Array[Byte] = s"$consumerKey:$consumerKeySecret".getBytes(StandardCharsets.UTF_8)
  val encodedCreds : String = Base64.getEncoder.encodeToString(bytes)
  println(encodedCreds)
//  val response: HttpResponse[String] = Http("https://api.twitter.com/1.1/search/tweets.json").
//    param("q", "nasa").
//    asString
//  println(response.body)
}
