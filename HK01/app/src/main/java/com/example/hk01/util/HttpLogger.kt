package  com.example.hk01.util

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    private var mMessage: StringBuffer = StringBuffer("")

    override fun log(message: String) {
        Log.d("http===", message)
        var message = message
        // 请求或者响应开始
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0)//如果不置为0，就会把之前请求的日志也会打印出来
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message))
        }
        mMessage?.append(message + "\n")

        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtil.i(mMessage.toString())

        }
    }
}