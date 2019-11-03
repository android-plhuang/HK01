package  com.example.hk01.util

import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger

/**
 * 作者： hpl
 * 创建时间： 2018/9/7
 * email： shipotianhpl@163.com
 * 作用：
 */
open class LogUtil private constructor(val isLogEnable: Boolean) {
    init {
        Logger.init("LogHttpInfo")
                .hideThreadInfo()
                .logLevel(if (isLogEnable) LogLevel.FULL else LogLevel.NONE)
                .methodOffset(2)
    }

    companion object {

        fun d(message: String) {
            Logger.d(message)
        }

        open fun i(message: String) {
            Logger.i(message)
        }

        fun w(message: String, e: Throwable?) {
            val info = e?.toString() ?: "null"
            Logger.w("$message：$info")
        }

        fun e(message: String, e: Throwable) {
            Logger.e(e, message)
        }

        fun e(message: String) {
            Logger.e(message)
        }

        fun json(json: String) {
            Logger.json(json)
        }




    }

}