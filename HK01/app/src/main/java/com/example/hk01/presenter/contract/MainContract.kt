package  com.example.hk01.presenter.contract

import com.example.hk01.base.BaseView
import com.example.hk01.data.bean.TestBean


/**
 * 作者： hpl
 * 创建时间： 2019/6/6
 * email： shipotianhpl@163.com
 * 作用： 空的contract
 */
interface MainContract {

    interface View : BaseView {
        fun getListSuccess(data: TestBean)
        fun getRecommendListSuccess(data: TestBean)
    }

    interface Presenter {
        fun getList(number:Int)
        fun geRecommendtList()
    }
}