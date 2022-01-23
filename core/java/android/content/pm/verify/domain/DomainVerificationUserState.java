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

package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.ArrayMap;

import com.android.internal.util.DataClass;
import com.android.internal.util.Parcelling;

import java.util.Map;
import java.util.UUID;

/**
 * Contains the user selection state for a package. This means all web HTTP(S) domains declared by a
 * package in its manifest, whether or not they were marked for auto verification.
 * <p>
 * Applications should use {@link #getHostToStateMap()} if necessary to
 * check if/how they are verified for a domain, which is required starting from platform
 * {@link android.os.Build.VERSION_CODES#S} in order to open {@link Intent}s which declare
 * {@link Intent#CATEGORY_BROWSABLE} or no category and also match against
 * {@link Intent#CATEGORY_DEFAULT} {@link android.content.IntentFilter}s, either through an
 * explicit declaration of {@link Intent#CATEGORY_DEFAULT} or through the use of
 * {@link android.content.pm.PackageManager#MATCH_DEFAULT_ONLY}, which is usually added for the
 * caller when using {@link Context#startActivity(Intent)} and similar.
 * <p>
 * By default, all apps are allowed to automatically open links for the above case for domains that
 * they've successfully verified against. This is reflected by {@link #isLinkHandlingAllowed()}.
 * The user can decide to disable this, disallowing the application from opening all links. Note
 * that the toggle affects <b>all</b> links and is not based on the verification state of the
 * domains.
 * <p>
 * Assuming the toggle is enabled, the user can also select additional unverified domains to grant
 * to the application to open, which is reflected in {@link #getHostToStateMap()}. But only a single
 * application can be approved for a domain unless the applications are both approved. If another
 * application is approved, the user will not be allowed to enable the domain.
 */
@SuppressWarnings("DefaultAnnotationParam")
@DataClass(genAidl = true, genHiddenConstructor = true, genParcelable = true, genToString = true,
        genEqualsHashCode = true, genHiddenConstDefs = true)
public final class DomainVerificationUserState implements Parcelable {

    /**
     * The domain is unverified and unselected, and the application is unable to open web links
     * that resolve to the domain.
     */
    public static final int DOMAIN_STATE_NONE = 0;

    /**
     * The domain has been selected by the user. This may be reset to {@link #DOMAIN_STATE_NONE} if
     * another application is selected or verified for the same domain.
     */
    public static final int DOMAIN_STATE_SELECTED = 1;

    /**
     * The domain has been previously verified by the domain verification agent.
     */
    public static final int DOMAIN_STATE_VERIFIED = 2;

    /**
     * @see DomainVerificationInfo#getIdentifier
     */
    @NonNull
    @DataClass.ParcelWith(Parcelling.BuiltIn.ForUUID.class)
    private final UUID mIdentifier;

    /**
     * The package name that this data corresponds to.
     */
    @NonNull
    private final String mPackageName;

    /**
     * The user that this data corresponds to.
     */
    @NonNull
    private final UserHandle mUser;

    /**
     * Whether or not this package is allowed to open links.
     */
    @NonNull
    private final boolean mLinkHandlingAllowed;

    /**
     * Mapping of domain host to state, as defined by {@link DomainState}.
     */
    @NonNull
    private final Map<String, Integer> mHostToStateMap;

    private void parcelHostToStateMap(Parcel dest, @SuppressWarnings("unused") int flags) {
        DomainVerificationUtils.writeHostMap(dest, mHostToStateMap);
    }

    @NonNull
    private Map<String, Integer> unparcelHostToStateMap(Parcel in) {
        return DomainVerificationUtils.readHostMap(in, new ArrayMap<>(),
                DomainVerificationUserState.class.getClassLoader());
    }

    /**
     * @see DomainVerificationInfo#getIdentifier
     * @hide
     */
    @SystemApi
    public @NonNull UUID getIdentifier() {
        return mIdentifier;
    }



    // Code below generated by codegen v1.0.22.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/core/java/android/content/pm/verify/domain/DomainVerificationUserState.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /** @hide */
    @android.annotation.IntDef(prefix = "DOMAIN_STATE_", value = {
        DOMAIN_STATE_NONE,
        DOMAIN_STATE_SELECTED,
        DOMAIN_STATE_VERIFIED
    })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @DataClass.Generated.Member
    public @interface DomainState {}

    /** @hide */
    @DataClass.Generated.Member
    public static String domainStateToString(@DomainState int value) {
        switch (value) {
            case DOMAIN_STATE_NONE:
                    return "DOMAIN_STATE_NONE";
            case DOMAIN_STATE_SELECTED:
                    return "DOMAIN_STATE_SELECTED";
            case DOMAIN_STATE_VERIFIED:
                    return "DOMAIN_STATE_VERIFIED";
            default: return Integer.toHexString(value);
        }
    }

    /**
     * Creates a new DomainVerificationUserState.
     *
     * @param packageName
     *   The package name that this data corresponds to.
     * @param user
     *   The user that this data corresponds to.
     * @param linkHandlingAllowed
     *   Whether or not this package is allowed to open links.
     * @param hostToStateMap
     *   Mapping of domain host to state, as defined by {@link DomainState}.
     * @hide
     */
    @DataClass.Generated.Member
    public DomainVerificationUserState(
            @NonNull UUID identifier,
            @NonNull String packageName,
            @NonNull UserHandle user,
            @NonNull boolean linkHandlingAllowed,
            @NonNull Map<String,Integer> hostToStateMap) {
        this.mIdentifier = identifier;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mIdentifier);
        this.mPackageName = packageName;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageName);
        this.mUser = user;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mUser);
        this.mLinkHandlingAllowed = linkHandlingAllowed;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mLinkHandlingAllowed);
        this.mHostToStateMap = hostToStateMap;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mHostToStateMap);

        // onConstructed(); // You can define this method to get a callback
    }

    /**
     * The package name that this data corresponds to.
     */
    @DataClass.Generated.Member
    public @NonNull String getPackageName() {
        return mPackageName;
    }

    /**
     * The user that this data corresponds to.
     */
    @DataClass.Generated.Member
    public @NonNull UserHandle getUser() {
        return mUser;
    }

    /**
     * Whether or not this package is allowed to open links.
     */
    @DataClass.Generated.Member
    public @NonNull boolean isLinkHandlingAllowed() {
        return mLinkHandlingAllowed;
    }

    /**
     * Mapping of domain host to state, as defined by {@link DomainState}.
     */
    @DataClass.Generated.Member
    public @NonNull Map<String,Integer> getHostToStateMap() {
        return mHostToStateMap;
    }

    @Override
    @DataClass.Generated.Member
    public String toString() {
        // You can override field toString logic by defining methods like:
        // String fieldNameToString() { ... }

        return "DomainVerificationUserState { " +
                "identifier = " + mIdentifier + ", " +
                "packageName = " + mPackageName + ", " +
                "user = " + mUser + ", " +
                "linkHandlingAllowed = " + mLinkHandlingAllowed + ", " +
                "hostToStateMap = " + mHostToStateMap +
        " }";
    }

    @Override
    @DataClass.Generated.Member
    public boolean equals(@Nullable Object o) {
        // You can override field equality logic by defining either of the methods like:
        // boolean fieldNameEquals(DomainVerificationUserState other) { ... }
        // boolean fieldNameEquals(FieldType otherValue) { ... }

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        DomainVerificationUserState that = (DomainVerificationUserState) o;
        //noinspection PointlessBooleanExpression
        return true
                && java.util.Objects.equals(mIdentifier, that.mIdentifier)
                && java.util.Objects.equals(mPackageName, that.mPackageName)
                && java.util.Objects.equals(mUser, that.mUser)
                && mLinkHandlingAllowed == that.mLinkHandlingAllowed
                && java.util.Objects.equals(mHostToStateMap, that.mHostToStateMap);
    }

    @Override
    @DataClass.Generated.Member
    public int hashCode() {
        // You can override field hashCode logic by defining methods like:
        // int fieldNameHashCode() { ... }

        int _hash = 1;
        _hash = 31 * _hash + java.util.Objects.hashCode(mIdentifier);
        _hash = 31 * _hash + java.util.Objects.hashCode(mPackageName);
        _hash = 31 * _hash + java.util.Objects.hashCode(mUser);
        _hash = 31 * _hash + Boolean.hashCode(mLinkHandlingAllowed);
        _hash = 31 * _hash + java.util.Objects.hashCode(mHostToStateMap);
        return _hash;
    }

    @DataClass.Generated.Member
    static Parcelling<UUID> sParcellingForIdentifier =
            Parcelling.Cache.get(
                    Parcelling.BuiltIn.ForUUID.class);
    static {
        if (sParcellingForIdentifier == null) {
            sParcellingForIdentifier = Parcelling.Cache.put(
                    new Parcelling.BuiltIn.ForUUID());
        }
    }

    @Override
    @DataClass.Generated.Member
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        // You can override field parcelling by defining methods like:
        // void parcelFieldName(Parcel dest, int flags) { ... }

        byte flg = 0;
        if (mLinkHandlingAllowed) flg |= 0x8;
        dest.writeByte(flg);
        sParcellingForIdentifier.parcel(mIdentifier, dest, flags);
        dest.writeString(mPackageName);
        dest.writeTypedObject(mUser, flags);
        parcelHostToStateMap(dest, flags);
    }

    @Override
    @DataClass.Generated.Member
    public int describeContents() { return 0; }

    /** @hide */
    @SuppressWarnings({"unchecked", "RedundantCast"})
    @DataClass.Generated.Member
    /* package-private */ DomainVerificationUserState(@NonNull Parcel in) {
        // You can override field unparcelling by defining methods like:
        // static FieldType unparcelFieldName(Parcel in) { ... }

        byte flg = in.readByte();
        boolean linkHandlingAllowed = (flg & 0x8) != 0;
        UUID identifier = sParcellingForIdentifier.unparcel(in);
        String packageName = in.readString();
        UserHandle user = (UserHandle) in.readTypedObject(UserHandle.CREATOR);
        Map<String,Integer> hostToStateMap = unparcelHostToStateMap(in);

        this.mIdentifier = identifier;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mIdentifier);
        this.mPackageName = packageName;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageName);
        this.mUser = user;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mUser);
        this.mLinkHandlingAllowed = linkHandlingAllowed;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mLinkHandlingAllowed);
        this.mHostToStateMap = hostToStateMap;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mHostToStateMap);

        // onConstructed(); // You can define this method to get a callback
    }

    @DataClass.Generated.Member
    public static final @NonNull Parcelable.Creator<DomainVerificationUserState> CREATOR
            = new Parcelable.Creator<DomainVerificationUserState>() {
        @Override
        public DomainVerificationUserState[] newArray(int size) {
            return new DomainVerificationUserState[size];
        }

        @Override
        public DomainVerificationUserState createFromParcel(@NonNull Parcel in) {
            return new DomainVerificationUserState(in);
        }
    };

    @DataClass.Generated(
            time = 1614721840152L,
            codegenVersion = "1.0.22",
            sourceFile = "frameworks/base/core/java/android/content/pm/verify/domain/DomainVerificationUserState.java",
            inputSignatures = "public static final  int DOMAIN_STATE_NONE\npublic static final  int DOMAIN_STATE_SELECTED\npublic static final  int DOMAIN_STATE_VERIFIED\nprivate final @android.annotation.NonNull @com.android.internal.util.DataClass.ParcelWith(com.android.internal.util.Parcelling.BuiltIn.ForUUID.class) java.util.UUID mIdentifier\nprivate final @android.annotation.NonNull java.lang.String mPackageName\nprivate final @android.annotation.NonNull android.os.UserHandle mUser\nprivate final @android.annotation.NonNull boolean mLinkHandlingAllowed\nprivate final @android.annotation.NonNull java.util.Map<java.lang.String,java.lang.Integer> mHostToStateMap\nprivate  void parcelHostToStateMap(android.os.Parcel,int)\nprivate @android.annotation.NonNull java.util.Map<java.lang.String,java.lang.Integer> unparcelHostToStateMap(android.os.Parcel)\npublic @android.annotation.SystemApi @android.annotation.NonNull java.util.UUID getIdentifier()\nclass DomainVerificationUserState extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genAidl=true, genHiddenConstructor=true, genParcelable=true, genToString=true, genEqualsHashCode=true, genHiddenConstDefs=true)")
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
