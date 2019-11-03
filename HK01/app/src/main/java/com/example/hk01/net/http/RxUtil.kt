package  com.example.hk01.net.http

import com.example.hk01.data.bean.TestBean
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：
 */
class RxUtil private constructor() {
    private var dis: Disposable? = null

    companion object {
        val instance by lazy { RxUtil() }
    }

    /**
     * 统一线程处理
     *c
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程,这时的泛型是被包裹的
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T : TestBean> handleResult(): FlowableTransformer<T, T> {   //compose判断结果，这时候的泛型是实际数据类型
//        这里其实就使用了一个操作符，flatMap
        return FlowableTransformer {
            it.flatMap { it ->

                creatData(it)
                /*if (it.code == Macro.CODE_SUCCESS) {
                    creatData(it)

                } else {
                    Flowable.error<T>(ApiException(it.code, it.msg, 1))
                }*/
            }
        }


    }


    /**
     * 生成Flowable,这里还和view无关
     *
     * @param <T>
     * @return
    </T> */
    fun <T> creatData(t: T?): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                if (t == null) {
                    emitter.onError(ApiException(301, "数据为空", "数据为空"))
                } else {
                    emitter.onNext(t)
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }


}


