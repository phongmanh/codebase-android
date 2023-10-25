package vn.liam.codebase.base.networking

data class Error(
    val success: Boolean?,
    val status_code: Int?,
    val status_message: String?
)

