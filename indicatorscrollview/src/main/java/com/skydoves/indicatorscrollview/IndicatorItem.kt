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

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/** IndicatorItem is an item data for composing the [IndicatorView]. */
@Suppress("unused")
class IndicatorItem(val target: View) {

  var color: Int = Color.WHITE
  var icon: Drawable? = null
  var iconTopPadding: Int = 50
  var cornerRadius: Float = 120f
  var duration: Long = 1000L
  var expandedSize: Int = -1
  var indicatorAnimation: IndicatorAnimation = IndicatorAnimation.NORMAL

  /** Builder class for creating an instance of [IndicatorItem]. */
  @IndicatorItemDsl
  class Builder(private val target: View) {
    private val indicatorItem = IndicatorItem(target)

    /** sets the background color of the indicator item using value. */
    fun setItemColor(value: Int) = apply { this.indicatorItem.color = value }

    /** sets the background color of the item using resource. */
    fun setItemColorResource(@ColorRes value: Int) = apply {
      this.indicatorItem.color = ContextCompat.getColor(target.context, value)
    }

    /** sets the icon of the indicator item using drawable. */
    fun setItemIcon(value: Drawable?) = apply { this.indicatorItem.icon = value }

    /** sets the icon of the indicator item using resource. */
    fun setItemIconResource(@DrawableRes value: Int) = apply {
      this.indicatorItem.icon = ContextCompat.getDrawable(target.context, value)
    }

    /** setItemIconTopPadding(12)  */
    fun setItemIconTopPadding(value: Int) = apply { this.indicatorItem.iconTopPadding = value }

    /** sets the corner radius of the indicator item. */
    fun setItemCornerRadius(value: Float) = apply { this.indicatorItem.cornerRadius = value }

    /** sets the expanding and collapsing duration. */
    fun setItemDuration(value: Long) = apply { this.indicatorItem.duration = value }

    /** customizes the fully expanded height size. */
    fun setExpandedSize(value: Int) = apply { this.indicatorItem.expandedSize = value }

    /** customizes the indicator animation when expanding and collapsing. */
    fun setIndicatorAnimation(value: IndicatorAnimation) = apply { this.indicatorItem.indicatorAnimation = value }

    /** returns an instance of the [IndicatorView]. */
    fun build() = indicatorItem
  }
}
