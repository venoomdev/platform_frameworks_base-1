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
import android.annotation.SystemApi;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.DataClass;
import com.android.internal.util.Parcelling;

import java.util.Set;

/**
 * Request object sent in the {@link Intent} that's broadcast to the domain verification agent,
 * retrieved through {@link DomainVerificationManager#EXTRA_VERIFICATION_REQUEST}.
 * <p>
 * This contains the set of packages which have been invalidated and will require re-verification.
 * The exact domains can be retrieved with
 * {@link DomainVerificationManager#getDomainVerificationInfo(String)}
 *
 * @hide
 */
@SuppressWarnings("DefaultAnnotationParam")
@DataClass(genHiddenConstructor = true, genAidl = false, genEqualsHashCode = true)
@SystemApi
public final class DomainVerificationRequest implements Parcelable {

    /**
     * The package names of the apps that need to be verified. The receiver should call {@link
     * DomainVerificationManager#getDomainVerificationInfo(String)} with each of these values to get
     * the actual set of domains that need to be acted on.
     */
    @NonNull
    @DataClass.ParcelWith(Parcelling.BuiltIn.ForStringSet.class)
    private final Set<String> mPackageNames;

    private void parcelPackageNames(@NonNull Parcel dest, @SuppressWarnings("unused") int flags) {
        DomainVerificationUtils.writeHostSet(dest, mPackageNames);
    }

    private Set<String> unparcelPackageNames(@NonNull Parcel in) {
        return DomainVerificationUtils.readHostSet(in);
    }



    // Code below generated by codegen v1.0.22.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/core/java/android/content/pm/verify/domain
    // /DomainVerificationRequest.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /**
     * Creates a new DomainVerificationRequest.
     *
     * @param packageNames
     *   The package names of the apps that need to be verified. The receiver should call {@link
     *   DomainVerificationManager#getDomainVerificationInfo(String)} with each of these values to get
     *   the actual set of domains that need to be acted on.
     * @hide
     */
    @DataClass.Generated.Member
    public DomainVerificationRequest(
            @NonNull Set<String> packageNames) {
        this.mPackageNames = packageNames;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageNames);

        // onConstructed(); // You can define this method to get a callback
    }

    /**
     * The package names of the apps that need to be verified. The receiver should call {@link
     * DomainVerificationManager#getDomainVerificationInfo(String)} with each of these values to get
     * the actual set of domains that need to be acted on.
     */
    @DataClass.Generated.Member
    public @NonNull Set<String> getPackageNames() {
        return mPackageNames;
    }

    @Override
    @DataClass.Generated.Member
    public boolean equals(@android.annotation.Nullable Object o) {
        // You can override field equality logic by defining either of the methods like:
        // boolean fieldNameEquals(DomainVerificationRequest other) { ... }
        // boolean fieldNameEquals(FieldType otherValue) { ... }

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        DomainVerificationRequest that = (DomainVerificationRequest) o;
        //noinspection PointlessBooleanExpression
        return true
                && java.util.Objects.equals(mPackageNames, that.mPackageNames);
    }

    @Override
    @DataClass.Generated.Member
    public int hashCode() {
        // You can override field hashCode logic by defining methods like:
        // int fieldNameHashCode() { ... }

        int _hash = 1;
        _hash = 31 * _hash + java.util.Objects.hashCode(mPackageNames);
        return _hash;
    }

    @DataClass.Generated.Member
    static Parcelling<Set<String>> sParcellingForPackageNames =
            Parcelling.Cache.get(
                    Parcelling.BuiltIn.ForStringSet.class);
    static {
        if (sParcellingForPackageNames == null) {
            sParcellingForPackageNames = Parcelling.Cache.put(
                    new Parcelling.BuiltIn.ForStringSet());
        }
    }

    @Override
    @DataClass.Generated.Member
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        // You can override field parcelling by defining methods like:
        // void parcelFieldName(Parcel dest, int flags) { ... }

        parcelPackageNames(dest, flags);
    }

    @Override
    @DataClass.Generated.Member
    public int describeContents() { return 0; }

    /** @hide */
    @SuppressWarnings({"unchecked", "RedundantCast"})
    @DataClass.Generated.Member
    /* package-private */ DomainVerificationRequest(@NonNull Parcel in) {
        // You can override field unparcelling by defining methods like:
        // static FieldType unparcelFieldName(Parcel in) { ... }

        Set<String> packageNames = unparcelPackageNames(in);

        this.mPackageNames = packageNames;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageNames);

        // onConstructed(); // You can define this method to get a callback
    }

    @DataClass.Generated.Member
    public static final @NonNull Parcelable.Creator<DomainVerificationRequest> CREATOR
            = new Parcelable.Creator<DomainVerificationRequest>() {
        @Override
        public DomainVerificationRequest[] newArray(int size) {
            return new DomainVerificationRequest[size];
        }

        @Override
        public DomainVerificationRequest createFromParcel(@NonNull Parcel in) {
            return new DomainVerificationRequest(in);
        }
    };

    @DataClass.Generated(
            time = 1613169505495L,
            codegenVersion = "1.0.22",
            sourceFile = "frameworks/base/core/java/android/content/pm/verify/domain/DomainVerificationRequest.java",
            inputSignatures = "private final @android.annotation.NonNull @com.android.internal.util.DataClass.ParcelWith(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class) java.util.Set<java.lang.String> mPackageNames\nprivate  void parcelPackageNames(android.os.Parcel,int)\nprivate  java.util.Set<java.lang.String> unparcelPackageNames(android.os.Parcel)\nclass DomainVerificationRequest extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genHiddenConstructor=true, genAidl=false, genEqualsHashCode=true)")
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
