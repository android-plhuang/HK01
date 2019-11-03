package  com.example.hk01.util


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import com.example.hk01.base.BaseApplication


/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：
 */
class DeviceUtil {
    companion object {
        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.getResources().getDisplayMetrics().density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 sp,字体的转换
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun sp2px(spValue: Float): Int {
            return (spValue * BaseApplication.context.resources.displayMetrics.scaledDensity + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dp2px(context: Context?, dpValue: Float): Int {
//            BaseApplication.get
            val scale = context?.getResources()?.getDisplayMetrics()?.density
            return (dpValue * scale!! + 0.5f).toInt()
        }

        fun dp2px(dpValue: Float): Int {
//            BaseApplication.get
            val scale = BaseApplication.context.getResources()?.getDisplayMetrics()?.density
            return (dpValue * scale!! + 0.5f).toInt()
        }

        fun setPX(context: Context, px: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(),
                    context.getResources().getDisplayMetrics()).toInt()
        }
        fun sp2px(context: Context, spValue: Int): Int {
            val fontScale = context.getResources().getDisplayMetrics().scaledDensity
            return  (spValue * fontScale + 0.5f).toInt()
        }



        /**
         * 获取DisplayMetrics，包括屏幕高宽，密度等
         * @param context
         * @return
         */
        fun getDisplayMetrics(context: Activity): DisplayMetrics {
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            return dm
        }

        /**
         * 获得屏幕宽度 px
         * @param context
         * @return
         */
        fun getWidth(context: Activity?): Int {
            val dm = DisplayMetrics()
            context?.windowManager?.defaultDisplay?.getMetrics(dm)
            return dm.widthPixels
        }

        /**
         * 获得屏幕宽度 px
         * @param context
         * @return
         */
        fun getWidth(): Int {
            /* WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;*/
            val dm = DisplayMetrics()
            val wm = BaseApplication.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        /**
         * 获得屏幕高度 px
         * @param context
         * @return
         */
        fun getHeight(context: Activity): Int {
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

        @SuppressLint("MissingPermission")
        fun getIMSI(context: Context?): String? {
            try {
                if (context == null) {
                    return ""
                }
                val telephonyManager = context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                return telephonyManager.subscriberId
            } catch (exception1: Exception) {
            }

            return ""
        }

        @SuppressLint("MissingPermission")
        fun getIMEI(context: Context?): String? {
            try {
                if (context == null) {
                    return ""
                }
                val telephonyManager = context!!
                        .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val imei = telephonyManager.deviceId
                if (imei != null && imei != "") {
                    return imei
                }
            } catch (exception1: Exception) {
            }

            return ""
        }

        fun checkApkExist(context: Context, packageName: String?): Boolean {
            if (packageName == null || "" == packageName)
                return false
            try {
                val info = context.getPackageManager().getApplicationInfo(packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                return false
            }

        }

        /**
         * 获取渠道名
         * @param ctx 此处习惯性的设置为activity，实际上context就可以
         * @return 如果没有获取成功，那么返回值为空
         */
        fun getChannelName(context: Context?): String? {
            if (context == null) {
                return ""
            }
            var channelName: String? = ""
            try {
                val packageManager = context!!.getPackageManager()
                if (packageManager != null) {
                    //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                    val applicationInfo = packageManager!!.getApplicationInfo(context!!.getPackageName(), PackageManager.GET_META_DATA)
                    if (applicationInfo != null) {
                        if (applicationInfo!!.metaData != null) {
                            channelName = applicationInfo!!.metaData.getString("InstallChannel")
                            if (channelName == null) {
                                return ""
                            }
                        }
                    }

                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return channelName
        }

        /**
         * 获取application中指定的meta-data
         * @return 如果没有获取成功(没有对应值,或者异常)，则返回值为空
         */
        fun getAppMetaData(ctx: Context?, key: String): String? {
            if (ctx == null || TextUtils.isEmpty(key)) {
                return null
            }
            var resultData: String? = null
            try {
                val packageManager = ctx!!.getPackageManager()
                if (packageManager != null) {
                    val applicationInfo = packageManager!!.getApplicationInfo(ctx!!.getPackageName(), PackageManager.GET_META_DATA)
                    if (applicationInfo != null) {
                        if (applicationInfo!!.metaData != null) {
                            resultData = applicationInfo!!.metaData.getString(key)
                        }
                    }

                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return resultData
        }

        /**
         * 获取状态栏高度
         *
         * @param context context
         * @return 状态栏高度
         */
        fun getStatusBarHeight(context: Activity): Int {
            // 获得状态栏高度
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }
        /**
         * 是否存在底部导航栏
         */
        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {

            }

            return hasNavigationBar
        }
    }


}