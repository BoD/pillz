/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2022-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.android.pillz.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.wear.compose.material.Colors

private val Orange700 = Color(0xFFF57C00)

val PillzColors: Colors = Colors(
    primary = Orange700,
)

val PillzTileColors: androidx.wear.protolayout.material.Colors = PillzColors.toTileColors()

private fun Colors.toTileColors() = androidx.wear.protolayout.material.Colors(
    /* primary = */ primary.toArgb(),
    /* onPrimary = */ onPrimary.toArgb(),
    /* surface = */ surface.toArgb(),
    /* onSurface = */ onSurface.toArgb()
)
