package  com.example.hk01.net.http

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：可以根据具体的数据结构来修改
 */


data class BaseBean<T>(val err_code: Int, val message: String, val return_state: String, val result_state: String, val data: T) {
    fun isSuccess(): Boolean {
        return err_code == 200
    }
}