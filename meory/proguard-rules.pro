# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 保留公共接口类及其公共方法
-keep public class com.io.meory.manager.MemoryBackManager {
    public static com.io.meory.manager.MemoryBackManager getInstance();
    public void init(android.content.Context,java.lang.Boolean);
}

# 防止混淆类名和方法名
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable