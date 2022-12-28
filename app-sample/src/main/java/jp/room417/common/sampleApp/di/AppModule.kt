package jp.room417.common.sampleApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.room417.common.sampleApp.BuildConfig
import jp.room417.twitter.service.DefaultTwitterServiceFactory
import jp.room417.twitter.service.TwitterService

@Module
@InstallIn(SingletonComponent::class)
object TwitterModule {
    @Provides
    fun provideTwitterService(@ApplicationContext context: Context): TwitterService =
        DefaultTwitterServiceFactory().createService(
            context,
            apiKey = BuildConfig.TWITTER_API_KEY,
            secret = BuildConfig.TWITTER_API_SECRET
        )
}
