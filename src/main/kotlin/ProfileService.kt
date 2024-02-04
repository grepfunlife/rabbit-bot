import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val host = "http://localhost:8080/"

class ProfileService {

    suspend fun registration(email: String, password: String, chatId: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response: HttpResponse = client.post("${host}auth/registration") {
            contentType(ContentType.Application.Json)
            setBody(Profile(email, password, chatId))
        }

//        val response: HttpResponse = client.get("${host}habits") {
//            headers {
//                append(HttpHeaders.Authorization,
//                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyZWQucmFiYml0IiwiZW1haWwiOiJsb2dpbkBnbS5jb20iLCJleHAiOjE3MDcwNzQzMjJ9.yDokwKDq6_FoxB2XlaALr_auNueCkcMEC0yqMB8CwNE")
//            }
//        }
        return response.body()
    }

    suspend fun isChatIdExists(chatId: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response: HttpResponse = client.get("${host}auth/isChatIdExists") {
            parameter("chatId", chatId)
        }
        return response.body()
    }

    suspend fun login(email: String, password: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response: HttpResponse = client.post("${host}auth/login") {
            contentType(ContentType.Application.Json)
            setBody(Profile(email, password, null))
        }

        return response.body()
    }

}