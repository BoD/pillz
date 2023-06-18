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

package org.jraf.android.pillz.data

import android.content.Context
import org.jraf.android.kprefs.Prefs
import org.jraf.android.pillz.util.logd
import java.util.concurrent.TimeUnit

private const val DAYS_BETWEEN_PILLS = 2L
private val ONE_DAYS_MS = TimeUnit.DAYS.toMillis(1)
private val TWO_DAYS_MS = TimeUnit.DAYS.toMillis(DAYS_BETWEEN_PILLS)

class Data(context: Context) {
    private val prefs = Prefs(context)

    private var lastPillsTakenDate: Long by prefs.Long(default = 0L)

    fun shouldTakePills(): Boolean {
        val now = nowAt0h()
        return now - lastPillsTakenDate >= TWO_DAYS_MS
    }

    fun tookPills() {
        logd("Clicked 'take pills'")
        lastPillsTakenDate = nowAt0h()
    }

    private fun nowAt0h(): Long {
        val now = System.currentTimeMillis()
        return now - now % ONE_DAYS_MS
    }
}
