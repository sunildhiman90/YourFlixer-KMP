package home.data.repo

import AppConstants.BASE_URL
import home.data.VideosListResponse
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


    suspend fun getPopularVideos(): VideosListResponse {
        val response: HttpResponse = client.get(BASE_URL + "movie/popular?language=en-US&page=1") {
            commonHeaders()
        }
        return response.body()
    }

    suspend fun getNowPlayingVideos(): VideosListResponse {
        //val finalUrlPath = "movie/now_playing?language=en-US&page=1"
        val finalUrlPath = "discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2023-10-01&primary_release_date.lte=2023-10-28&sort_by=primary_release_date.desc"
        val response: HttpResponse = client.get(BASE_URL + finalUrlPath) {
            commonHeaders()
        }
        return response.body()
    }

    suspend fun getTopRatedVideos(): VideosListResponse {
        val finalUrlPath = "discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2023-10-01&primary_release_date.lte=2023-10-28&sort_by=vote_count.desc&vote_count.gte=50"
        val response: HttpResponse = client.get(BASE_URL + finalUrlPath) {
            commonHeaders()
        }
        return response.body()
    }



}