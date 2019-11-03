package  com.example.hk01.base

import android.app.Application
import android.content.Context
import com.example.hk01.data.Macro
import com.example.hk01.net.di.component.AppComponent
import com.example.hk01.net.di.component.DaggerAppComponent
import com.example.hk01.net.di.model.AppModel
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * 作者： hpl
 * 创建时间： 2018/8/30
 * email： shipotianhpl@163.com
 * 作用：项目中的application
 */
@Suppress("UNREACHABLE_CODE")
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        initRealm()

    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name(Macro.DB_NAME)
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)

    }


    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModel(AppModel(this)).build()
    }

    /*
         全局伴生对象
      */
    companion object {
        lateinit var context: Context
        lateinit var appComponent: AppComponent
        open var isAddWebsocketListener = false//在没有初始化的
        open fun setIsAddWebsocketListener(flag: Boolean) {
            isAddWebsocketListener = flag
        }


    }


}