package jp.room417.twitter.extension

import jp.room417.twitter4kt.Twitter

val Twitter?.isAuthorized: Boolean
    get() = this != null
