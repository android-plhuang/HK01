package  com.example.hk01.base

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：view的基类接口
 */
interface BaseView {
    //=======  State  =======
    fun stateError()

    fun stateEmpty()

    fun stateLoading()

    fun stateMain()

    fun hintLoading()
    fun showMsg(msg: String)



}