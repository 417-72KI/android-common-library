package jp.room417.common.extension

inline fun <T1, T2, R> T1.letWith(other: T2?, action: (T1, T2) -> R): R? =
    other?.let { action(this, other) }
