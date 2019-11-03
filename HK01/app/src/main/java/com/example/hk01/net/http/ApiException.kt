package  com.example.hk01.net.http

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：服务端异常
 */
data class ApiException(val code: Int, override val message: String, val data: Any) : Exception() {
}