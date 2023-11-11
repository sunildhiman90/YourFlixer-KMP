package home.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosListResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<FeedVideoItem>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)