/*
 * This file is a part of Gronkify, licensed under the MIT License.
 * Why would you look at the code???
 *
 * Copyright (c) 2021 Juuz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package juuxel.gronkify

import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.{Plugin, Project}

class Gronkify extends Plugin[Project] {
  override def apply(target: Project): Unit = {
    if (shouldPlay(target)) play(target)
  }

  private def shouldPlay(project: Project): Boolean = {
    val refresh = project.getGradle.getStartParameter.isRefreshDependencies || java.lang.Boolean.getBoolean("loom.refresh")
    refresh || !Option(project.findProperty("gronkify.requireRefreshDependencies"))
      .flatMap(_.toString.toBooleanOption)
      .getOrElse(false)
  }

  private def play(target: Project): Unit = {
    val provider = target.getGradle.getSharedServices
      .registerIfAbsent[MusicService, BuildServiceParameters.None]("gronkify", classOf[MusicService], _ => {})
    val service = provider.get() // let's run it!

    if (service.isPlaying) {
      target.getLogger.lifecycle(":playing \"Le Grand Chase\" by Kevin MacLeod")
    }
  }
}
