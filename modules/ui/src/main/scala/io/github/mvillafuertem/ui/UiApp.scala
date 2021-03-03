package io.github.mvillafuertem.ui
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document
import zio.{ExitCode, Task, URIO}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object UiApp {

  @JSImport("/src/main/resources/App.css", JSImport.Default)
  @js.native
  object AppCSS extends js.Object

  @JSImport("/src/main/resources/index.css", JSImport.Default)
  @js.native
  object IndexCSS extends js.Object

  @JSImport("/src/main/resources/logo.svg", JSImport.Default)
  @js.native
  object ReactLogo extends js.Object

  def main(args: Array[String]): Unit = {
    IndexCSS
    AppCSS
    <.div(^.className := "App")(
      <.header(^.className := "App-header")(
        <.img(^.src := ReactLogo.asInstanceOf[String], ^.className := "App-logo", ^.alt := "logo"),
        <.h1(^.className := "App-title")("Welcome to React (with Scala.js!)")
      ),
      <.p(^.className := "App-intro")(
        "To get started, edit ", <.code("App.scala"), " and save to reload."
      )
    ).renderIntoDOM(document.getElementById("root"))
  }
}
