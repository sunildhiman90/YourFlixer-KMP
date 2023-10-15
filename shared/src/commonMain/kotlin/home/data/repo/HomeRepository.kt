package home.data.repo

import AppConstants.BASE_URL
import home.data.PopularVideosResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMessageBuilder

class HomeRepository(private val client: HttpClient) {

    private fun HttpMessageBuilder.commonHeaders() {
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }

    suspend fun getPopularVideos():PopularVideosResponse {
        val response: HttpResponse = client.get(BASE_URL + "movie/popular?language=en-US&page=1") {
            commonHeaders()
        }
        return response.body()
    }

}