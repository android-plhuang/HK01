package com.example.hk01.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.hk01.R
import com.example.hk01.base.SimpleActivity
import com.example.hk01.data.bean.ApplicationBean
import com.example.hk01.util.LogUtil
import com.example.hk01.util.clickView
import com.example.hk01.util.loadCircleImage
import com.example.hk01.util.loadImage
import com.example.hk01.util.recycleview.LineItemDecoration
import io.realm.Case
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜索页面的activity
 * 需要先把100条数据添加到数据库，然后再从数据库搜索
 */
class SearchActivity : SimpleActivity() {
    private var mRealm: Realm? = null
    private var mData = arrayListOf<ApplicationBean>()
    private var mAdapter: BaseQuickAdapter<ApplicationBean, BaseViewHolder>? = null
    private var mEmpty: View?=null

    override fun initView() {
        ivBack?.clickView {
            finish()
        }
        initRecyclerView()
        edKeyWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchByKey(s.toString())
            }

        })
    }

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun initDate() {
        mRealm = Realm.getDefaultInstance()
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    private fun searchByKey(key: String?) {
        mData?.clear()
        if (key?.isNullOrEmpty()!!) {
            mAdapter?.setEmptyView(mEmpty)
            mAdapter?.setNewData(mData)
        } else {
            val list = mRealm?.where(ApplicationBean::class.java)?.contains("name", key, Case.INSENSITIVE)
                ?.or()?.contains("type", key,Case.INSENSITIVE)?.or()?.contains("summary", key,Case.INSENSITIVE)
                ?.findAll()
            if (list?.isNullOrEmpty()!!) {
                mAdapter?.setEmptyView(mEmpty)
                mAdapter?.setNewData(mData)
            } else {
                mData.addAll(list)
                mAdapter?.notifyDataSetChanged()

            }
        }


    }

    private fun initRecyclerView() {

        LogUtil.i("===========${mData.size}")
        rvSearch.layoutManager = LinearLayoutManager(this)
        mAdapter =
            object : BaseQuickAdapter<ApplicationBean, BaseViewHolder>(R.layout.item_application_content, mData) {
                override fun convert(helper: BaseViewHolder?, item: ApplicationBean?) {
                    val position = helper?.adapterPosition!!
                    helper?.setText(R.id.tvApplicationName, "${item?.name}")
                    helper?.setText(R.id.tvApplicationType, "${item?.type}")
                    helper?.setVisible(R.id.tvRank, false)
                    val ivContentPic = helper?.getView<ImageView>(R.id.ivContentPic)
                    if (position?.rem(2) == 0) {
                        ivContentPic?.loadImage(this@SearchActivity, item!!.icon!!, 5)
                    } else {
                        ivContentPic?.loadCircleImage(this@SearchActivity, item!!.icon!!)
                    }


                }

            }
        mEmpty = View.inflate(this!!, R.layout.layout_empty, null)
        rvSearch?.addItemDecoration(LineItemDecoration(1, resources.getColor(R.color.stair_bbb)))
        rvSearch.adapter = mAdapter

    }
}
