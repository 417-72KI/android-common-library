package jp.room417.common.extension.liveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class LiveDataExtensionTest {
    @ParameterizedTest
    @ArgumentsSource(IsTrueTestCase::class)
    fun isTrue(param1: Boolean?, expected: Boolean) {
        val ld: LiveData<Boolean> = MutableLiveData(param1)
        assertThat(ld.isTrue).isEqualTo(expected)
    }

    private class IsTrueTestCase : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
            Arguments.arguments(true, true),
            Arguments.arguments(null, false),
        )
    }
}
