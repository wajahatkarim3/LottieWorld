#include <string>

extern "C" JNIEXPORT jstring
JNICALL
Java_com_wajahatkarim3_lottieworld_common_SecureKeys_baseUrl(JNIEnv *env, jobject object) {
std::string baseUrl = "https://firebasestorage.googleapis.com/v0/b/lottiefiles-test.appspot.com/o/";
return env->NewStringUTF(baseUrl.c_str());
}