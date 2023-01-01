package jp.room417.twitter.extension

import jp.room417.twitter4k.Twitter
import twitter4j.TwitterException

val Twitter.isAuthorized: Boolean
    get() = try {
        origin.oAuthAccessToken != null
    } catch (e: Exception) {
        when (e) {
            is TwitterException, is IllegalStateException -> false
            else -> throw e
        }
    }
