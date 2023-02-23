package jp.room417.common.component.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import jp.room417.common.R
import jp.room417.common.component.base.BaseDialog

class ConfirmDialog internal constructor(
    @StringRes private var titleResource: Int?,
    private var title: String?,
    @StringRes private var messageResource: Int?,
    private var message: String?,
    @StringRes private var onOkLabelResource: Int = R.string.ok,
    private var onOkLabel: String?,
    private var onOk: () -> Unit,
    @StringRes private var onCancelLabelResource: Int = R.string.cancel,
    private var onCancelLabel: String?,
    private var onCancel: () -> Unit,
) : BaseDialog() {
    private val onOkListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { _, _ -> onOk() }

    private val onCancelListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { _, _ -> onCancel() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireActivity())
            .setCancelable(true)
            .apply {
                titleResource?.let { setTitle(it) }
                    ?: setTitle(title)
                messageResource?.let { setMessage(it) }
                    ?: setMessage(message)
                onOkLabel?.let { setPositiveButton(it, onOkListener) }
                    ?: setPositiveButton(onOkLabelResource, onOkListener)
                onCancelLabel?.let { setNegativeButton(it, onCancelListener) }
                    ?: setNegativeButton(onCancelLabelResource, onCancelListener)
            }
            .create()

    class Builder {
        @StringRes
        private var titleResource: Int? = null
        private var title: String? = null

        @StringRes
        private var messageResource: Int? = null
        private var message: String? = null

        @StringRes
        private var onOkLabelResource: Int = R.string.ok
        private var onOkLabel: String? = null
        private var onOk: () -> Unit = {}

        @StringRes
        private var onCancelLabelResource: Int = R.string.cancel
        private var onCancelLabel: String? = null
        private var onCancel: () -> Unit = {}

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

        fun setOnOk(@StringRes label: Int, action: () -> Unit): Builder {
            onOk = action
            onOkLabel = null
            onOkLabelResource = label
            return this
        }

        fun setOnOk(label: String? = null, action: () -> Unit): Builder {
            onOk = action
            onOkLabel = label
            return this
        }

        fun setOnCancel(@StringRes label: Int, body: () -> Unit): Builder {
            onCancel = body
            onCancelLabel = null
            onCancelLabelResource = label
            return this
        }

        fun setOnCancel(label: String? = null, body: () -> Unit): Builder {
            onCancel = body
            onCancelLabel = label
            return this
        }

        fun build() = ConfirmDialog(
            titleResource,
            title,
            messageResource,
            message,
            onOkLabelResource,
            onOkLabel,
            onOk,
            onCancelLabelResource,
            onCancelLabel,
            onCancel,
        )
    }
}
