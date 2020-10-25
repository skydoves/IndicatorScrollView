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
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.FloatRange
import androidx.annotation.Px

/** IndicatorView is an indicator layout for reacting with [IndicatorScrollView] when the scroll is changed. */
@Suppress("unused")
class IndicatorView : FrameLayout, OnScrollChangedListener {

  private val indicatorItemsList = mutableListOf<IndicatorItem>()
  private val indicatorItemViewList = mutableListOf<IndicatorItemView>()

  @Px
  var indicatorItemPadding = dp2Px(6).toInt()

  @FloatRange(from = 0.0, to = 1.0)
  var expandingRatio = 0.2f

  @FloatRange(from = 0.0, to = 1.0)
  var expandingAllItemRatio = 0.9f

  constructor(context: Context) : super(context)

  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    getAttrs(attributeSet)
  }

  constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
    context,
    attributeSet, defStyle
  ) {
    getAttrs(attributeSet, defStyle)
  }

  private fun getAttrs(attributeSet: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.IndicatorView)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun getAttrs(attributeSet: AttributeSet, defStyleAttr: Int) {
    val typedArray =
      context.obtainStyledAttributes(attributeSet, R.styleable.IndicatorView, defStyleAttr, 0)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setTypeArray(a: TypedArray) {
    this.indicatorItemPadding =
      a.getDimensionPixelSize(R.styleable.IndicatorView_indicator_itemPadding, this.indicatorItemPadding)
    this.expandingRatio =
      a.getFloat(R.styleable.IndicatorView_indicator_expandingRatio, this.expandingRatio)
    this.expandingAllItemRatio =
      a.getFloat(R.styleable.IndicatorView_indicator_expandingAllItemRatio, this.expandingAllItemRatio)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    post { onChanged(0, 0, 0) }
  }

  /** add an [IndicatorItem] for composing [IndicatorView]. */
  fun addIndicatorItem(indicatorItem: IndicatorItem) {
    indicatorItem.target.post {
      val indicatorItemView = IndicatorItemView(context)
      addItem(indicatorItem, indicatorItemView).apply {
        this.indicatorItem = indicatorItem
        this.layoutParams = LayoutParams(getStandardSize(), getStandardSize())
        this.y = indicatorItem.target.y
      }
      addView(indicatorItemView)
      invalidate()
    }
  }

  /** add a list of [IndicatorItem] for composing [IndicatorView]. */
  fun addIndicatorItemList(indicatorItemList: List<IndicatorItem>) {
    indicatorItemList.forEach(::addIndicatorItem)
  }

  private fun addItem(
    indicatorItem: IndicatorItem,
    indicatorItemView: IndicatorItemView
  ): IndicatorItemView {
    this.indicatorItemsList.add(indicatorItem)
    this.indicatorItemViewList.add(indicatorItemView)
    return indicatorItemView
  }

  private fun getStandardSize() = width - paddingLeft - paddingRight

  operator fun plus(indicatorItem: IndicatorItem) = addIndicatorItem(indicatorItem)

  operator fun plus(indicatorItemList: List<IndicatorItem>) = addIndicatorItemList(indicatorItemList)

  override fun onChanged(x: Int, y: Int, measuredScrollViewHeight: Int) {
    if (y == 0 && expandingRatio != 0f) return
    post {
      for (index in 0 until this.indicatorItemsList.size) {
        var height = this.indicatorItemsList[index].expandedSize
        if (index == this.indicatorItemsList.size - 1 && height == -1) {
          height =
            (this.height - this.indicatorItemViewList[index].y - this.indicatorItemPadding).toInt()
        } else if (height == -1) {
          height =
            (this.indicatorItemViewList[index + 1].y - this.indicatorItemViewList[index].y - this.indicatorItemPadding).toInt()
        }
        if (this.indicatorItemViewList[index].y - y < context.displaySize().y * this.expandingRatio) {
          this.indicatorItemViewList[index].expand(getStandardSize(), height)
        } else if (((measuredScrollViewHeight * expandingAllItemRatio).toInt() > y)) {
          this.indicatorItemViewList[index].collapse(getStandardSize(), height)
        } else {
          this.indicatorItemViewList[index].expand(getStandardSize(), height)
        }
      }
    }
  }

  /** Builder class for creating an instance of [IndicatorView]. */
  @IndicatorViewDsl
  class Builder(context: Context) {
    private val indicatorView = IndicatorView(context)

    fun setIndicatorItemPadding(@Px value: Int) = apply { this.indicatorView.indicatorItemPadding = value }

    fun setExpandingRatio(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply { this.indicatorView.expandingRatio = value }

    fun setExpandingAllItemRatio(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply { this.indicatorView.expandingAllItemRatio = value }

    fun build() = this.indicatorView
  }
}
