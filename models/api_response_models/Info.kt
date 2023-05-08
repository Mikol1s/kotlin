package models.api_response_models

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)