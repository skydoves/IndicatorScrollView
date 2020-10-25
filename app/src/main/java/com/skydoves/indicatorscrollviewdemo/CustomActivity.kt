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

package com.skydoves.indicatorscrollviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.indicatorscrollview.IndicatorAnimation
import com.skydoves.indicatorscrollview.IndicatorItem
import com.skydoves.indicatorscrollview.indicatorItem
import kotlinx.android.synthetic.main.activity_custom.*

class CustomActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_custom)

    indicatorScrollView.bindIndicatorView(indicatorView)

    /** add an indicator item using [indicatorItem] kotlin dsl with plus operator. */
    indicatorView + indicatorItem(section1) {
      setItemColorResource(R.color.colorPrimary)
      setItemIconResource(R.drawable.ic_heart)
      setIndicatorAnimation(IndicatorAnimation.BOUNCE)
      setItemCornerRadius(30f)
    }

    /** add an indicator item using [IndicatorItem.Builder] with plus operator. */
    indicatorView + IndicatorItem.Builder(section2)
      .setItemColorResource(R.color.md_orange_100)
      .setItemIconResource(R.drawable.ic_home)
      .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
      .setItemCornerRadius(30f)
      .build()

    /** add an indicator item using an addIndicatorItem method. */
    indicatorView.addIndicatorItem(
      IndicatorItem.Builder(section3)
        .setItemColorResource(R.color.md_yellow_200)
        .setItemIconResource(R.drawable.ic_assignment)
        .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
        .setItemCornerRadius(30f)
        .build()
    )

    indicatorView + IndicatorItem.Builder(section4)
      .setItemColorResource(R.color.md_green_200)
      .setItemIconResource(R.drawable.ic_bookmark)
      .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
      .setItemCornerRadius(30f)
      .build()

    indicatorView + IndicatorItem.Builder(section5)
      .setItemColorResource(R.color.md_blue_200)
      .setItemIconResource(R.drawable.ic_date)
      .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
      .setItemCornerRadius(30f)
      .build()

    indicatorView + IndicatorItem.Builder(section6)
      .setItemColorResource(R.color.md_purple_100)
      .setItemIconResource(R.drawable.ic_wifi)
      .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
      .setItemCornerRadius(30f).build()
  }
}
