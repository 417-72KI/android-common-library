package jp.room417.common.component.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import jp.room417.common.R
import jp.room417.common.component.base.BaseDialog

class ConfirmDialog internal constructor(
    private var title: String?,
    private var message: String?,
    private var onOk: () -> Unit,
    private var onOkLabel: String?,
    @StringRes private var onOkLabelResource: Int = R.string.ok,
    private var onCancel: () -> Unit,
    private var onCancelLabel: String?,
    @StringRes private var onCancelLabelResource: Int = R.string.cancel,
) : BaseDialog() {
    private val onOkListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { _, _ -> onOk() }

    private val onCancelListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { _, _ -> onCancel() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .apply {
                onOkLabel?.let { setPositiveButton(it, onOkListener) }
                    ?: setPositiveButton(onOkLabelResource, onOkListener)
                onCancelLabel?.let { setNegativeButton(it, onCancelListener) }
                    ?: setNegativeButton(onCancelLabelResource, onCancelListener)
            }
            .create()

    class Builder {
        private var title: String? = null
        private var message: String? = null
        private var onOk: () -> Unit = {}
        private var onOkLabel: String? = null
        private var onOkLabelResource: Int = R.string.ok
        private var onCancel: () -> Unit = {}
        private var onCancelLabel: String? = null
        private var onCancelLabelResource: Int = R.string.cancel

        fun setTitle(str: String?): Builder {
            title = str
            return this
        }

        fun setMessage(str: String?): Builder {
            message = str
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

        fun setOnCancel(label: String? = null, body: () -> Unit): Builder {
            onCancel = body
            onCancelLabel = label
            return this
        }

        fun setOnCancel(@StringRes label: Int, body: () -> Unit): Builder {
            onCancel = body
            onCancelLabel = null
            onCancelLabelResource = label
            return this
        }

        fun build() = ConfirmDialog(
            title,
            message,
            onOk,
            onOkLabel,
            onOkLabelResource,
            onCancel,
            onCancelLabel,
            onCancelLabelResource
        )
    }
}
