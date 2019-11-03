package com.example.hk01.base

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.example.hk01.net.di.component.DaggerFragmentComponent
import com.example.hk01.net.di.component.FragmentComponent
import com.example.hk01.net.di.model.FragmentModule
import com.example.hk01.net.di.model.LifecycleProviderModule
import com.example.hk01.util.DeviceUtil
import com.example.hk01.widgets.ProgressLoading
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * 作者： hpl
 * 创建时间： 2018/10/10
 * email： shipotianhpl@163.com
 * 作用：
 */
abstract class BaseFragment<T : BasePresenter<*>> : SimpleFragment(), BaseView {
    @Inject
    lateinit var mPresenter: T
    lateinit var mLoadingDialog: ProgressLoading
    //    var pageManager: MyPageManager? = null
    lateinit var fragmentComponent: FragmentComponent
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    val isUseStateView: Boolean
        get() = false

    open val isUseRefreshView: Boolean
        get() = false

    override fun stateError() {
        showStateView(0)
    }

    override fun stateEmpty() {
        showStateView(1)
    }

    override fun stateLoading() {
        mLoadingDialog.showLoading()
    }

    override fun stateMain() {
        showStateView(2)
    }

    override fun hintLoading() {
        hintSrl()
        mLoadingDialog.hideLoading()
    }

    override fun showMsg(msg: String) {
        hintSrl()
        toast(msg)
    }

    private fun initActivityInjection() {
        fragmentComponent = DaggerFragmentComponent.builder().appComponent(BaseApplication.appComponent)
                .fragmentModule(FragmentModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun initViewCreated() {
        //初始加载框
        mLoadingDialog = ProgressLoading.create(context!!)
        initActivityInjection()
        initInject()
    }

    abstract fun initInject()

    override fun dealWhitStateViewAndRefreshView(): View? {
        /*if (isUseStateView) {
            pageManager = MyPageManager.init(getStateView(), object : MyPageListener() {
                override fun onReallyRetry() {


                }

                override fun onEmtptyViewClicked(emptyView: View?) {
                    super.onEmtptyViewClicked(emptyView)

                }
            })
        }*/

        if (isUseRefreshView) {
            mSwipeRefreshLayout = SwipeRefreshLayout(mContext!!)
            mView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            mSwipeRefreshLayout?.addView(mView)
            mView = mSwipeRefreshLayout!!
            initSrl()

        }
        return mView
    }


    /**
     * 初始化mSwipeRefreshLayout
     */
    private fun initSrl() {
        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(android.R.color.white)
        mSwipeRefreshLayout?.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light)
        //默认显示
        mSwipeRefreshLayout?.setProgressViewOffset(false, 0, DeviceUtil.dp2px(mContext, 24F))
//        swipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout?.setOnRefreshListener {
            refreshData()
        }
    }

    /**
     * 刷新数据，需要就自己重写
     */
    open fun refreshData() {

    }

    open fun getStateView(): View {
        return (mView!! as ViewGroup).getChildAt(0)
    }

    private fun showStateView(position: Int) {
        /*when (position) {
            0 -> pageManager?.showError(" error occured  !!!!!!!!!!!!")
            1 -> pageManager?.showEmpty("没有东西,惊喜不惊喜?\n 你可以点击重试一下")
            2 -> pageManager?.showContent()
        }*/
    }

    fun hintSrl() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout?.isRefreshing!!) {
            mSwipeRefreshLayout?.isRefreshing = false
        }
    }



}