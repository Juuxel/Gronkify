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

import org.gradle.api.services.{BuildService, BuildServiceParameters}

import javax.sound.sampled.{AudioSystem, Clip, FloatControl, LineEvent}

abstract class MusicService extends BuildService[BuildServiceParameters.None] with AutoCloseable {
  private var stopped = false
  private val clip = start()

  private def start(): Option[Clip] = try {
    val url = classOf[MusicService].getResource("/gronkify/bgm.wav")
    val stream = AudioSystem.getAudioInputStream(url)
    val clip = AudioSystem.getClip
    clip.open(stream)
    clip.addLineListener(event => {
      if (event.getType == LineEvent.Type.STOP) {
        clip.setFramePosition(0)
        if (!stopped) clip.start()
      }
    })
    clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl].setValue(-15)
    clip.start()
    Some(clip)
  } catch {
    case _: Exception => None
  }

  def isPlaying: Boolean = clip.isDefined

  override def close(): Unit = {
    stopped = true
    clip match {
      case Some(value) =>
        value.stop()
        value.close()
      case None =>
    }
  }
}
