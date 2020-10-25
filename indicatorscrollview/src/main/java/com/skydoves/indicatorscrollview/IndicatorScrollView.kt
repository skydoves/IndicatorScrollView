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

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

/**
 * IndicatorScrollView is a scrollView for reacting with [IndicatorView] when scroll is changed.
 * Extends [NestedScrollView].
 */
@Suppress("UNUSED_PARAMETER")
class IndicatorScrollView : NestedScrollView, NestedScrollView.OnScrollChangeListener {

  private var onUserScrollChangeListener: OnScrollChangeListener? = null
  private var onScrollChangedListener: OnScrollChangedListener? = null

  constructor(context: Context) : super(context)
  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
  constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context)

  override fun onFinishInflate() {
    super.onFinishInflate()
    super.setOnScrollChangeListener(this)
  }

  override fun setOnScrollChangeListener(onScrollChangeListener: OnScrollChangeListener?) {
    super.setOnScrollChangeListener(this)
    if (onScrollChangeListener != this) {
      this.onUserScrollChangeListener = onScrollChangeListener
    }
  }

  override fun onScrollChange(
    nestedScrollView: NestedScrollView?,
    scrollX: Int,
    scrollY: Int,
    oldScrollX: Int,
    oldScrollY: Int
  ) {
    this.onUserScrollChangeListener?.onScrollChange(
      nestedScrollView, scrollX, scrollY, oldScrollX,
      oldScrollY
    )
    nestedScrollView?.let {
      if (it.childCount > 0) {
        this.onScrollChangedListener?.onChanged(
          scrollX, scrollY,
          abs(it.measuredHeight - it.getChildAt(0).measuredHeight)
        )
      }
    }
  }

  /** binds a [IndicatorView] to [IndicatorScrollView]. */
  fun bindIndicatorView(indicatorView: IndicatorView) {
    this.onScrollChangedListener = indicatorView
  }
}
