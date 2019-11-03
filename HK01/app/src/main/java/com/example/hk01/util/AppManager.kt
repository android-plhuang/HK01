package  com.example.hk01.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import com.example.hk01.ui.activity.MainActivity
import java.util.*




/**
 * 作者： hpl
 * 创建时间： 2018/8/30
 * email： shipotianhpl@163.com
 * 作用：管理APP
 */
class AppManager private constructor() {
    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun finishActivity(activity: Activity) {
        activityStack.remove(activity)
        activity.finish()
    }

    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    fun clearActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 清除除了当前activity之外的activity
     */
    fun clearActivtyExceptCurrent(cls: Class<*>) {
        for (i in activityStack.size - 1 downTo 0) {
            if (activityStack[i].javaClass != cls) {
                finishActivity(activityStack[i])
            }
        }
    }

    fun exitApp(context: Context) {
        clearActivity()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.killBackgroundProcesses(context.packageName)
        System.exit(0)

    }

    /**
     * 返回到指定的页面，相当于把它上面的activity清除
     */
    fun finishActivityToTopOrHome(cls: Class<*>) {
        for (i in activityStack.size - 1 downTo 0) {
            if (activityStack[i].javaClass.equals(cls) || activityStack[i].javaClass.equals(MainActivity::class.java)) {
                continue
            } else {
                activityStack[i].finish()
            }
        }
    }
}