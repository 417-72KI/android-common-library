package jp.room417.common.component.extension

import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.hideKeyBoard(v: View) = requireActivity().hideKeyBoard(v)
