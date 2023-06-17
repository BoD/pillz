/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2023-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jraf.android.pillz.ui.main

import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.TimelineBuilders.Timeline
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.ButtonColors
import androidx.wear.protolayout.material.ButtonDefaults
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import com.google.common.util.concurrent.ListenableFuture
import org.jraf.android.pillz.R
import org.jraf.android.pillz.data.tookPills
import org.jraf.android.pillz.ui.theme.PillzTileColors

private const val BUTTON_CLICKED_ID: String = "button_clicked"

class MainTileService : TileService() {
    override fun onTileResourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ListenableFuture<ResourceBuilders.Resources> {
        return CallbackToFutureAdapter.getFuture { callback ->
            callback.set(
                ResourceBuilders.Resources.Builder()
                    .setVersion("0")
                    .map(R.drawable.ic_pill)
                    .map(R.drawable.ic_pill_off)
                    .build()
            )
        }
    }

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> {
        if (requestParams.currentState.lastClickableId == BUTTON_CLICKED_ID) {
            // Button has been clicked: we flip the state
            tookPills = !tookPills
        }
        return CallbackToFutureAdapter.getFuture { callback ->
            callback.set(
                TileBuilders.Tile.Builder()
                    .setResourcesVersion("0")
                    .setTileTimeline(
                        Timeline.Builder()
                            .addTimelineEntry(
                                TimelineBuilders.TimelineEntry.Builder()
                                    .setLayout(
                                        LayoutElementBuilders.Layout.Builder()
                                            .setRoot(buildLayout())
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
        }
    }

    private fun buildLayout(): LayoutElementBuilders.LayoutElement {
        val clickable = ModifiersBuilders.Clickable.Builder()
            .setId(BUTTON_CLICKED_ID)
            .setOnClick(ActionBuilders.LoadAction.Builder().build())
            .build()
        return LayoutElementBuilders.Box.Builder()
            .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
            .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
            .setWidth(DimensionBuilders.expand())
            .setHeight(DimensionBuilders.expand())
            .addContent(
                Button.Builder(this@MainTileService, clickable)
                    .setSize(ButtonDefaults.EXTRA_LARGE_SIZE)

                    // Uncomment that part... now it works
//                    .setButtonColors(
//                        if (tookPills) {
//                            ButtonColors.primaryButtonColors(PillzTileColors)
//                        } else {
//                            ButtonColors.secondaryButtonColors(PillzTileColors)
//                        }
//                    )

                    .setIconContent(
                        if (tookPills) {
                            R.drawable.ic_pill_off
                        } else {
                            R.drawable.ic_pill
                        }
                    )
                    .build()
            )
            .build()
    }
}

private fun Button.Builder.setIconContent(resId: Int) = setIconContent(resId.toString())

private fun ResourceBuilders.Resources.Builder.map(resId: Int): ResourceBuilders.Resources.Builder {
    return addIdToImageMapping(
        resId.toString(),
        ResourceBuilders.ImageResource.Builder()
            .setAndroidResourceByResId(
                ResourceBuilders.AndroidImageResourceByResId.Builder()
                    .setResourceId(resId)
                    .build()
            )
            .build()
    )
}

