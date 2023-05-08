package models.api_response_models

data class ApiResponse(
    val info: Info,
    val results: List<Result>
)