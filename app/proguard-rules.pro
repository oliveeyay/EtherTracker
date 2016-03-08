
# For development only, get rid of this options for real releases
#-dontobfuscate
#-dontoptimize

-target 1.7
-optimizationpasses 3
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepparameternames
-dontpreverify
-allowaccessmodification
-dontshrink
-verbose

# Dictionary
-obfuscationdictionary proguard-dictionary.txt
-classobfuscationdictionary proguard-dictionary.txt
-packageobfuscationdictionary proguard-dictionary.txt

# The -optimizations option disables some arithmetic simplifications that Dalvik 1.0 and 1.5 can't handle.
-optimizations !field/removal/writeonly,!code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable,!code/simplification/cast

# TO KEEP ANNOTATION
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature
-keep class javax.annotation.** { *; }
-keepattributes *Annotation*
# END ANNOTATION

#Keep necessary classes and method
-dontwarn android.**
-keep class android.** { *; }
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.** { *; }
-dontwarn com.robotium.**
-keep class com.robotium.** { *; }
-keep class org.apache..** { *; }
-keep class android.net.** { *; }
-keep class android.util.** { *; }
-keep class sun.misc.** { *; }
-keep class com.google.** { *; }
-keep class com.mixpanel.** { *; }
-keep class com.github.** { *; }
-keep class com.daimajia.** { *; }
#End Keep necessary classes and method

-keep public class * extends android.app.Activity
-keep public class * extends android.widget.RelativeLayout
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.ContentProvider
-keep class com.google.inject.Binder
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.content.BroadcastReceiver { *; }

-keepclassmembers class * {
	void finalizeReferent();
}

-keepclassmembers class * {
	<init>(...);
}

-keepclassmembers class * {
    void *(**On*Event);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}
# END ANDROID

#Fabric
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class io.fabric.** { *; }
-dontwarn io.fabric.**
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep public class * extends java.lang.Exception
#END Fabric

#Google Analytics
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}


-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

#End Google Analytics

# Needed by google-api-client to keep generic types and @Key annotations accessed via reflection
-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
#END Random stuff from libraries

#Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-dontwarn okio.**
-keep class okio.** { *; }
-dontwarn org.robovm.**
-keep class org.robovm.** { *; }
-dontwarn com.google.**
-keep class com.google.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn java.util.Optional
-keep class java.util.Optional { *; }

-dontwarn com.og.finance.ether.network.apis.**
-keep class com.og.finance.ether.network.apis.** { *; }
-dontwarn com.og.finance.ether.network.services.**
-keep class com.og.finance.ether.network.services.** { *; }

-keepattributes InnerClasses
#END Retrofit
