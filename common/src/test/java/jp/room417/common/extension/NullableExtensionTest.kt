package jp.room417.common.extension

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class NullableExtensionTest {
    @ParameterizedTest
    @ArgumentsSource(LetWithTestCase::class)
    fun letWith(param1: String?, param2: Int?, expected: String?) {
        assertThat(param1?.letWith(param2) { p1, p2 -> "$p1: $p2" })
            .isEqualTo(expected)
    }

    private class LetWithTestCase : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
            arguments(null, null, null),
            arguments(null, 1, null),
            arguments("foo", null, null),
            arguments("bar", 1, "bar: 1"),
        )
    }
}
