package com.example.hk01.base

import com.example.hk01.data.Macro

/**
 * 作者： hpl
 * 创建时间： 2018/8/30
 * email： shipotianhpl@163.com
 * 作用：存放一些基本常量
 */
class BaseConstant {
    companion object {
        const val TEST_HTTP_HOST = Macro.CONNECT_TEST_HOST + "/"
        const val CONNECT_HTTP_HOST = Macro.CONNECT_TEST_HOST + "/"
        const val NORMAL_CHANNEL_VALUE = "888"


        const val SP_AUDIO_PATH = "sp_audio_path"
        const val MSG_COUNT_SYS = "msg_count_sys"
        const val MSG_COUNT_PROPS = "msg_count_props"
        const val MSG_COUNT_SESSION = "msg_count_session"

        //event相关的
        const val EVENT_RECEIVE_WEB_SOCKET = 11
        const val EVENT_MSG_UN_READ = 1
        const val EVENT_MSG_SINGLE_NO_READ = 302
        const val EVENT_MSG_SYSTEM_NO_READ = 305
        const val EVENT_RECEIVE_MSG = 201
        const val EVENT_UPDATE_MSG_COUNT = 1001//更新未读消息数
        const val EVENT_BE_OFFLINE = 102//更新未读消息数


        //        聊天相关的
        const val MSG_TYPE_NORMAL_TEXT = -1//普通文本消息

        /**
         * 消息类型，图片
         */
        const val MSG_TYPE_IMG = 9

        /**
         * 消息类型，文字
         */
        const val MSG_TYPE_TEXT = 10
        /**
         * 消息类型，结束通话
         */
        const val MSG_TYPE_END_CHAT = 11
        /**
         * 消息类型，关门通话或加锁通话
         */
        const val MSG_TYPE_CHANGE_PRIVATE = 12
        /**
         * 消息类型，短语音
         */
        const val MSG_TYPE_SHORT_VOICE = 13
        /**
         * 消息类型，录音分享
         */
        const val MSG_TYPE_VOICE_SHARE = 14
        /**
         * 消息类型，通话分享
         */
        const val MSG_TYPE_CALL_SHARE = 15
        /**
         * 消息类型，撤回消息
         */
        const val MSG_TYPE_REVOKE = 16
        /**
         * 消息类型，@
         */
        const val MSG_TYPE_AT = 27

        //        SP相关
        const val SP_USER_INFO = "sp_user_info"
        const val SP_TOKEN = "sp_token"
        const val SP_UID = "sp_uid"
        const val SP_IMEI = "sp_imei"
        const val SP_LONGITUDE = "sp_longitude"
        const val SP_LATITUDE = "sp_latitude"
        const val SP_CHANNEL = "sp_channel"
        const val SP_SEX = "sp_sex"

        const val SP_SEND_CODE = "sp_send_code"
        const val SP_ICON = "sp_icon"
        const val SP_MSG_SYS = "sp_msg_sys"//系统消息未读数量
        const val SP_MSG_IM = "sp_msg_im"//im未读数量
        const val SP_MSG_ADD_FRIENDS = "sp_msg_im_friend"//im未读数量
        const val SP_USER_WALLET = "sp_user_wallet"//im未读数量
        const val SP_MY_STATE = "sp_my_state"//我的状态，在线、通话中
        const val SP_CHAT_ID = "sp_chat_id"//当前会话的ID
        const val SP_CALL_INFO = "sp_call_info"//正在通话的callInfo
        const val SP_IS_CALLING = "sp_is_calling"//是否是在通话中
        const val SP_IS_MATCHING = "sp_is_matching"//是否是匹配中
        const val SP_IS_REFUSE_ALL = "sp_is_refuse_all"//拒绝所有来电
        const val SP_LAST_MSG_ID_SINGLE = "sp_last_msg_id_single"//最新单聊消息ID
        const val SP_LAST_MSG_ID_GROUP = "sp_last_msg_id_group"//最新群聊消息ID
        const val SP_LAST_MSG_ID_SYSTEM = "sp_last_msg_id_system"//最新系统消息ID

        //        阿里云相关
        const val ENDPOINT = "http://oss-ap-south-1.aliyuncs.com"
        const val BUCKET_NAME = "hitchat"
        const val OSS_ACCESS_KEY_ID = "LTAIBYf2vQlH4cNY"
        const val OSS_ACCESS_KEY_SECRET = "KDKyU7hELSj8i8n7yFJespQKiHGFrz"

        //            图片目录
        const val PIC_DIR_HAND = "head"//head 头像
        const val PIC_DIR_POST = "post"//  动态
        //        const val PIC_DIR_YUE = "yue"//yue 邀约
        const val PIC_DIR_PHOTO = "photo"//photo 照片墙
        const val PIC_DIR_COMPLAIN = "complain"//complain 举报
        //        const val PIC_DIR_IDENTITY = "identity"//identity 验证
        const val VOICE_DIR = "voice"//voice 音频

        const val PIC_DIR_CHAT_IMAGE = "chat/image"//聊天图片
        const val PIC_DIR_CHAT_VOICE = "chat/voice"//聊天音频

        const val PAGE_SIZE = 20

        //数据库相关
        const val DATA_BASE_NAME = "hichat_data.db"

        //        在线配置参数
        const val USER_PROTOCOL = "user_protocol"
        const val USE_EXPLAIN = "use_explain"
        const val ABOUT_US = "about_us"
        const val IMG_SERVER = "img_server"


        //        页面跳转相关的
        const val REQUEST_REVISIONOFINFORMATION_RECORD = 0x2015
        const val REQUEST_CALL_CHARGING_SETTINGS_RECORD = 0x2016


        const val googlePayKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4FEuVD4jsEsdk15TyyzdYF9rP0gTZnzUiXQcOpkMR6QJnwbPnTrUVJZLJaJoDzCiXvVpssinN+DWtaqe5lj1t/XoY8/GMcFJuHPXgHuSnTtya16cd235Gu5S/W9gIFt4eLqgf+ODdQYuI66ODkR84qlZqnZkbJUPLlL33nHW0NM9gAWpimiNFZM3CqOiGTrFcWpajpVH40QZr02W43oBCBq9WNaQVVU3Rbn7ao8Y9SvEVUb4xYq6OZ5p96ptwBVj5tUGXGhuT5XBeRPjgZSthGrTtaQk07VVNQqnHaKdrruwEblSCblMgWbbcGVf2UrQ9AfT29+yXu/sIGE5C4Lc8wIDAQAB"





    }
}