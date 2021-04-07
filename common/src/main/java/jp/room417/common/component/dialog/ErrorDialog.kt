package jp.room417.common.component.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import jp.room417.common.R
import jp.room417.common.component.base.BaseDialog

class ErrorDialog internal constructor(
    @StringRes private var titleResource: Int? = R.string.error,
    private var title: String?,
    @StringRes private var messageResource: Int?,
    private var message: String?,
    @StringRes private var onOkLabelResource: Int = R.string.ok,
    private var onOkLabel: String?,
    private var onOk: () -> Unit
) : BaseDialog() {
    private val onOkListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { _, _ -> onOk() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireActivity())
            .apply {
                titleResource?.let { setTitle(it) }
                    ?: setTitle(title)
                messageResource?.let { setMessage(it) }
                    ?: setMessage(message)
                onOkLabel?.let { setPositiveButton(it, onOkListener) }
                    ?: setPositiveButton(onOkLabelResource, onOkListener)
            }
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
            }

    class Builder {
        @StringRes
        private var titleResource: Int? = R.string.error
        private var title: String? = null

        @StringRes
        private var messageResource: Int? = null
        private var message: String? = null
        private var onOk: () -> Unit = {}
        private var onOkLabel: String? = null
        private var onOkLabelResource: Int = R.string.ok

        fun setTitle(@StringRes resId: Int): Builder {
            title = null
            titleResource = resId
            return this
        }

        fun setTitle(str: String?): Builder {
            title = str
            titleResource = null
            return this
        }

        fun setMessage(@StringRes resId: Int): Builder {
            message = null
            messageResource = resId
            return this
        }

        fun setMessage(str: String?): Builder {
            message = str
            messageResource = null
            return this
        }

        fun setOnOk(label: String? = null, action: () -> Unit): Builder {
            onOk = action
            onOkLabel = label
            return this
        }

        fun setOnOk(@StringRes label: Int, action: () -> Unit): Builder {
            onOk = action
            onOkLabel = null
            onOkLabelResource = label
            return this
        }

        fun build() = ErrorDialog(
            titleResource,
            title,
            messageResource,
            message,
            onOkLabelResource,
            onOkLabel,
            onOk
        )
    }
}
