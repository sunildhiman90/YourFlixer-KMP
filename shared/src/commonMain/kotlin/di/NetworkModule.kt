package di

import AppConstants.TMDB_API_ACCESS_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import logger.AppLogger
import org.koin.dsl.module


fun networkModule() = module {

    single {
        HttpClient{
            install(Auth) {
                bearer {
                    loadTokens {
                        // Load tokens from a local storage and return them as the 'BearerTokens' instance
                        BearerTokens(TMDB_API_ACCESS_TOKEN, "")
                    }

                    /*refreshTokens {
                        BearerTokens("abc123", oldTokens?.accessToken!!)
                    }*/
                }
            }

            //Negotiating media types between the client and server. for using contentType
            install(ContentNegotiation) {
                //json serializer
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }

            //Logging
            install(Logging) {

                //Simple inbuilt Logging
                /*logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header -> header == HttpHeaders.Authorization }*/

                //Custom Logger: Kermit Logger
                logger = object: Logger {
                    override fun log(message: String) {
                        AppLogger.v("HTTP Client", null, message)
                    }
                }
                level = LogLevel.HEADERS


            }
        }

    }

}