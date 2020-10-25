/*
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.indicatorscrollview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView

/** IndicatorItemView is am internal class for drawing [IndicatorItem] on the [IndicatorView].  */
internal class IndicatorItemView(
  context: Context,
  attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

  var isExpanding: Boolean = false
    private set

  var isExpanded: Boolean = false
    private set

  var indicatorItem: IndicatorItem? = null
    set(value) {
      field = value
      updateIndicatorItemView()
    }

  private val icon: ImageView = ImageView(context)

  init {
    val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    layoutParams.gravity = Gravity.CENTER
    this.layoutParams = layoutParams
    this.icon.layoutParams = layoutParams
    addView(this.icon)
    invalidate()
  }

  private fun updateIndicatorItemView() {
    this.indicatorItem?.let {
      icon.setImageDrawable(it.icon)
      background = GradientDrawable().apply {
        cornerRadius = it.cornerRadius
        setColor(it.color)
      }
    }
  }

  internal fun expand(minHeight: Int, to: Int) {
    this.indicatorItem?.let { item ->
      if (!isExpanded && !isExpanding) {
        this.isExpanded = true
        this.isExpanding = true
        ValueAnimator.ofFloat(0f, 1f).apply {
          duration = item.duration
          applyInterpolator(item.indicatorAnimation)
          doAfterFinishLift { isExpanding = false }
          addUpdateListener {
            val value = it.animatedValue as Float
            applyLayoutParams {
              var target = minHeight + (to * value).toInt()
              if (target >= to) target = to
              height = target
            }
          }
          start()
        }
      }
    }
  }

  internal fun collapse(minHeight: Int, from: Int) {
    this.indicatorItem?.let { item ->
      if (isExpanded && !isExpanding) {
        this.isExpanded = false
        this.isExpanding = true
        ValueAnimator.ofFloat(1f, 0f).apply {
          duration = item.duration
          applyInterpolator(item.indicatorAnimation)
          doAfterFinishLift { isExpanding = false }
          addUpdateListener {
            val value = it.animatedValue as Float
            applyLayoutParams {
              var target = (from * value).toInt()
              if (target <= minHeight) target = minHeight
              height = target
            }
          }
          start()
        }
      }
    }
  }
}
