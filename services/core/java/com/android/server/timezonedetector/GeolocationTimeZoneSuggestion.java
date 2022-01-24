/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.timezonedetector;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.ShellCommand;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * A time zone suggestion from a geolocation source.
 *
 * <p> Geolocation-based suggestions have the following properties:
 *
 * <ul>
 *     <li>{@code zoneIds}. When not {@code null}, {@code zoneIds} contains a list of suggested time
 *     zone IDs, e.g. ["America/Phoenix", "America/Denver"]. Usually there will be a single zoneId.
 *     When there are multiple, this indicates multiple answers are possible for the current
 *     location / accuracy, i.e. if there is a nearby time zone border. The detection logic
 *     receiving the suggestion is expected to use the first element in the absence of other
 *     information, but one of the others may be used if there is supporting evidence / preferences
 *     such as a device setting or corroborating signals from another source.
 *     <br />{@code zoneIds} can be empty if the current location has been determined to have no
 *     time zone. For example, oceans or disputed areas. This is considered a strong signal and the
 *     received need not look for time zone from other sources.
 *     <br />{@code zoneIds} can be {@code null} to indicate that the geolocation source has entered
 *     an "un-opinionated" state and any previous suggestion is being withdrawn. This indicates the
 *     source cannot provide a valid suggestion due to technical limitations. For example, a
 *     geolocation source may become un-opinionated if the device's location is no longer known with
 *     sufficient accuracy, or if the location is known but no time zone can be determined because
 *     no time zone mapping information is available.</li>
 *     <li>{@code debugInfo} contains debugging metadata associated with the suggestion. This is
 *     used to record why the suggestion exists and how it was obtained. This information exists
 *     only to aid in debugging and therefore is used by {@link #toString()}, but it is not for use
 *     in detection logic and is not considered in {@link #hashCode()} or {@link #equals(Object)}.
 *     </li>
 * </ul>
 *
 * @hide
 */
public final class GeolocationTimeZoneSuggestion {

    @Nullable private final List<String> mZoneIds;
    @Nullable private ArrayList<String> mDebugInfo;

    public GeolocationTimeZoneSuggestion(@Nullable List<String> zoneIds) {
        if (zoneIds == null) {
            // Unopinionated
            mZoneIds = null;
        } else {
            mZoneIds = Collections.unmodifiableList(new ArrayList<>(zoneIds));
        }
    }

    /**
     * Returns the zone Ids being suggested. See {@link GeolocationTimeZoneSuggestion} for details.
     */
    @Nullable
    public List<String> getZoneIds() {
        return mZoneIds;
    }

    /** Returns debug information. See {@link GeolocationTimeZoneSuggestion} for details. */
    @NonNull
    public List<String> getDebugInfo() {
        return mDebugInfo == null
                ? Collections.emptyList() : Collections.unmodifiableList(mDebugInfo);
    }

    /**
     * Associates information with the instance that can be useful for debugging / logging. The
     * information is present in {@link #toString()} but is not considered for
     * {@link #equals(Object)} and {@link #hashCode()}.
     */
    public void addDebugInfo(String... debugInfos) {
        if (mDebugInfo == null) {
            mDebugInfo = new ArrayList<>();
        }
        mDebugInfo.addAll(Arrays.asList(debugInfos));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeolocationTimeZoneSuggestion
                that = (GeolocationTimeZoneSuggestion) o;
        return Objects.equals(mZoneIds, that.mZoneIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mZoneIds);
    }

    @Override
    public String toString() {
        return "GeolocationTimeZoneSuggestion{"
                + "mZoneIds=" + mZoneIds
                + ", mDebugInfo=" + mDebugInfo
                + '}';
    }

    /** @hide */
    public static GeolocationTimeZoneSuggestion parseCommandLineArg(@NonNull ShellCommand cmd) {
        String zoneIdsString = null;
        String opt;
        while ((opt = cmd.getNextArg()) != null) {
            switch (opt) {
                case "--zone_ids": {
                    zoneIdsString  = cmd.getNextArgRequired();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown option: " + opt);
                }
            }
        }
        List<String> zoneIds = parseZoneIdsArg(zoneIdsString);
        GeolocationTimeZoneSuggestion suggestion = new GeolocationTimeZoneSuggestion(zoneIds);
        suggestion.addDebugInfo("Command line injection");
        return suggestion;
    }

    private static List<String> parseZoneIdsArg(String zoneIdsString) {
        if ("UNCERTAIN".equals(zoneIdsString)) {
            return null;
        } else if ("EMPTY".equals(zoneIdsString)) {
            return Collections.emptyList();
        } else {
            ArrayList<String> zoneIds = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(zoneIdsString, ",");
            while (tokenizer.hasMoreTokens()) {
                zoneIds.add(tokenizer.nextToken());
            }
            return zoneIds;
        }
    }

    /** @hide */
    public static void printCommandLineOpts(@NonNull PrintWriter pw) {
        pw.println("Geolocation suggestion options:");
        pw.println("  --zone_ids {UNCERTAIN|EMPTY|<Olson ID>+}");
        pw.println();
        pw.println("See " + GeolocationTimeZoneSuggestion.class.getName()
                + " for more information");
    }
}
