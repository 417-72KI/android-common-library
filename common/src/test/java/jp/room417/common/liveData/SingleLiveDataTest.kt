package jp.room417.common.liveData

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SingleLiveDataTest {
    class TestViewModel {
        private val _liveData = MutableSingleLiveData<String>()
        val liveData: SingleLiveData<String> = _liveData

        fun setValue(value: String) {
            _liveData.value = value
        }
    }

    private lateinit var viewModel: TestViewModel

    @BeforeEach
    fun setUp() {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
                override fun postToMainThread(runnable: Runnable) = runnable.run()
                override fun isMainThread(): Boolean = true
            })
        viewModel = TestViewModel()
    }

    @AfterEach
    fun tearDown() {
        ArchTaskExecutor.getInstance()
            .setDelegate(null)
    }

    @Test
    fun observeWithMock() {
        val observer = mock<Observer<String>>()
        viewModel.liveData.observeForever(observer)

        viewModel.setValue("foo")
        verify(observer).onChanged("foo")

        viewModel.setValue("bar")
        verify(observer).onChanged("bar")
    }
}
