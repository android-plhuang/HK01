package  com.example.hk01.base

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.example.hk01.net.di.component.ActivityComponent
import com.example.hk01.net.di.component.DaggerActivityComponent
import com.example.hk01.net.di.model.ActivityModule
import com.example.hk01.net.di.model.LifecycleProviderModule
import com.example.hk01.util.AppManager
import com.example.hk01.util.DeviceUtil
import com.example.hk01.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject


/**
 * MVP activity基类,使用到dagger
 * 1.关联presenter和view
 * 2.依赖注入
 * 3.状态视图的处理，数据展示型，加载中，空数据，网络异常；数据请求型，加载中，失败提示
 * 4.下拉刷新（加载更多在列表处理就好）
 * 4.一些统一的处理，被挤下线，重新登录等
 */
abstract class BaseActivity<T : BasePresenter<*>> : SimpleActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mLoadingDialog: ProgressLoading
    lateinit var activityComponent: ActivityComponent
    protected var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    /**
     * 网络异常的布局
     *
     * @return
     */
    protected val errorLayout: Int
        get() = -1

    protected val emptyLayout: Int
        get() = -1

    val isUseStateView: Boolean
        get() = false

    open val isUseRefreshView: Boolean
        get() = false


    override fun onViewCreated() {
        super.onViewCreated()
        //初始加载框
        mLoadingDialog = ProgressLoading.create(this)
        initActivityInjection()
        initInject()

    }


    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent(BaseApplication.appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    @Override
    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)

    }

    protected abstract fun initInject()

    fun showErrorMsg(msg: String) {
        hintSrl()
        toast(msg)
    }

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

    private fun showStateView(position: Int) {
        /* when (position) {
             0 -> pageManager?.showError(" error occured  !!!!!!!!!!!!")
             1 -> pageManager?.showEmpty("没有东西,惊喜不惊喜?\n 你可以点击重试一下")
             2 -> pageManager?.showContent()
         }*/
    }


    override fun hintLoading() {
        hintSrl()
        mLoadingDialog.hideLoading()
    }


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
            myContentView?.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            mSwipeRefreshLayout?.addView(myContentView)
            myContentView = mSwipeRefreshLayout
            initSrl()
        }
        return myContentView
    }

    /**
     * 初始化mSwipeRefreshLayout
     */
    private fun initSrl() {
        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(android.R.color.white)
        mSwipeRefreshLayout?.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light, android.R.color.holo_orange_light,
            android.R.color.holo_green_light
        )
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

    override fun loadDataAgain() {

    }


    fun checkIsLogin(): Boolean {


        return false
    }


    /**
     * 登录成功后的操作，根据需要自己实现
     */
    protected fun loginSuccess() {}


    fun login() {

    }

    fun beOffline() {

    }


    override fun showMsg(msg: String) {
        hintSrl()
        toast(msg)
    }

    open fun getStateView(): View {
        return (myContentView!! as ViewGroup).getChildAt(0)
    }

    fun hintSrl() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout?.isRefreshing!!) {
            mSwipeRefreshLayout?.isRefreshing = false
        }
    }

}