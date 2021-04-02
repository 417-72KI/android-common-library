package jp.room417.twitter.extension

import twitter4j.Twitter
import twitter4j.TwitterException

val Twitter.isAuthorized: Boolean
    get() = try {
        oAuthAccessToken != null
    } catch (e: Exception) {
        when(e) {
            is TwitterException, is IllegalStateException -> false
            else -> throw e
        }
    }
