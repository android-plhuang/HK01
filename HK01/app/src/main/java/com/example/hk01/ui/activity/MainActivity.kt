package com.example.hk01.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.hk01.R
import com.example.hk01.base.BaseActivity
import com.example.hk01.data.Macro
import com.example.hk01.data.bean.ApplicationBean
import com.example.hk01.data.bean.Entry
import com.example.hk01.data.bean.TestBean
import com.example.hk01.presenter.MainPresenter
import com.example.hk01.presenter.contract.MainContract
import com.example.hk01.util.LogUtil
import com.example.hk01.util.clickView
import com.example.hk01.util.loadCircleImage
import com.example.hk01.util.loadImage
import com.example.hk01.util.recycleview.LineItemDecoration
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_application_head.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.startActivity

/**
 * 显示应用列表页面
 * item_application_head
 * item_application_content
 * 由于没有分页的接口，只能先加载第一页的数据，然后渲染数据完成后再加载剩下的数据
 * 然后在本地进行分页加载
 */
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {
    private var mData = arrayListOf<Entry>()
    private var mRecommendData = arrayListOf<Entry>()
    private var mAdapter: BaseQuickAdapter<Entry, BaseViewHolder>? = null
    private var mRecommendAdapter: BaseQuickAdapter<Entry, BaseViewHolder>? = null
    private var mRvHead: RecyclerView? = null
    private var mAppHeadView: View? = null
    private var mAllData = arrayListOf<Entry>()//100条数据
    private var mPageNo = 1
    private var mRealm: Realm? = null
    private var mLoadMore = false
    override fun initInject() {
        activityComponent.inject(this)
        mPresenter.mView = this
    }

    override fun initView() {
        clTitle.clickView {
            startActivity<SearchActivity>()
        }
        initRecommendRecyclerView()
        initRecyclerView()

    }

    /**
     * 初始化推荐应用列表
     */
    private fun initRecommendRecyclerView() {
        mAppHeadView = View.inflate(this, R.layout.item_application_head, null)
        mRvHead = mAppHeadView?.rvHead
        mRvHead?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecommendAdapter =
            object : BaseQuickAdapter<Entry, BaseViewHolder>(R.layout.item_application_recommend, mRecommendData) {
                override fun convert(helper: BaseViewHolder?, item: Entry?) {
                    helper?.setText(R.id.tvHeadName, "${item?.name?.label}")
                    helper?.setText(R.id.tvHeadType, "${item?.category?.attributes?.label}")
                    val ivHeadPic = helper?.getView<ImageView>(R.id.ivHeadPic)
                    ivHeadPic?.loadImage(this@MainActivity, item!!.image[0].label, 5)


                }

            }
        mRvHead?.adapter = mRecommendAdapter
    }


    private fun initRecyclerView() {

        LogUtil.i("===========${mData.size}")
        rvList.layoutManager = LinearLayoutManager(this)
        mAdapter = object : BaseQuickAdapter<Entry, BaseViewHolder>(R.layout.item_application_content, mData) {
            override fun convert(helper: BaseViewHolder?, item: Entry?) {
//奇数行裁剪成圆角，偶数行裁剪成圆形
//                helper?.getView<TextView>()
                val position = helper?.adapterPosition!! - 1
                helper?.setText(R.id.tvRank, "${position!! + 1}")
                helper?.setText(R.id.tvApplicationName, "${item?.name?.label}")
                helper?.setText(R.id.tvApplicationType, "${item?.category?.attributes?.label}")
                val ivContentPic = helper?.getView<ImageView>(R.id.ivContentPic)
                if (position?.rem(2) == 0) {
                    ivContentPic?.loadImage(this@MainActivity, item!!.image[0].label, 5)
                } else {
                    ivContentPic?.loadCircleImage(this@MainActivity, item!!.image[0].label)
                }


            }

        }
        rvList?.addItemDecoration(LineItemDecoration(1, resources.getColor(R.color.stair_bbb)))
        mAdapter?.addHeaderView(View.inflate(this, R.layout.item_head_title, null))
        mAdapter?.addHeaderView(mAppHeadView)
        //        加载更多
        mAdapter?.setOnLoadMoreListener({

            if (mPageNo < 10) {
                runBlocking {
                    //                    stateLoading()
                    // 阻塞1s，延迟加载的效果
                    delay(1000L)
//                    hintLoading()
                    if (mAllData.size > 0) {
                        loadMore()
                    } else {
                        mLoadMore = true
                    }

                }

            } else {
                mAdapter?.loadMoreEnd()
            }
            mAdapter?.notifyDataSetChanged()
        }, rvList)
        rvList.adapter = mAdapter

    }

    /**
     * 加载更多
     */
    private fun loadMore() {
//        mAdapter?.loadMoreEnd()
//        Thread.sleep(2000)
        val temp = mPageNo * Macro.PAGE_SIZE
        for (i in 0..9) {
            mData.add(mAllData[temp + i])
        }
        mPageNo++
        mAdapter?.loadMoreComplete()
    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initDate() {
        mPresenter?.geRecommendtList()
        mPresenter?.getList(10)
        mRealm = Realm.getDefaultInstance()
    }

    override fun getListSuccess(data: TestBean) {
        val list = data.feed.entry
        if (mData.size == 0) {
            mData.addAll(list)
            notifyAdapter()
        } else {
            mAllData.addAll(list)
            if (mLoadMore) {//没有获取数据成功时，已经滑动到底部加载更多了，这时候，加载数据完成后就去加载下一页
                mLoadMore = false
                loadMore()
            }
        }
        /*for (i in 0..9) {
            mData.add(list[i])
        }
        notifyAdapter()
        mAllData.addAll(list)*/
        saveDataToDB(data)

    }

    override fun getRecommendListSuccess(data: TestBean) {
        mRecommendData.clear()
        mRecommendData.addAll(data.feed.entry)
        notifyAdapter()
        saveDataToDB(data)
    }

    /**
     * 加载数据完成后再统一显示
     */
    private fun notifyAdapter() {
        if (mRecommendData.size > 0 && mData.size > 0) {
            mRecommendAdapter?.notifyDataSetChanged()
            mAdapter?.notifyDataSetChanged()
            hintLoading()
            mPresenter?.getList(100)
        } else {
            stateLoading()
        }

    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    /**
     * 保存数据到数据库
     */
    private fun saveDataToDB(data: TestBean) {
        if (data.feed.entry.isNotEmpty()) {
            data.feed.entry.forEach { it ->
                val appBean = ApplicationBean()
                appBean.icon = it.image[0].label
                appBean.id = it.id.attributes.id
                appBean.name = it.name.label
                appBean.type = it.category.attributes.label
                appBean.summary = it.summary.label
                mRealm?.executeTransaction {
                    it.insertOrUpdate(appBean)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm?.close()
    }
}
