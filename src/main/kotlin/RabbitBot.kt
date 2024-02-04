import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

val TOKEN = System.getenv("BOT_TOKEN")


class RabbitBot : TelegramLongPollingBot(TOKEN) {
    override fun getBotUsername(): String {
        return "RabbitRedBot"
    }

    override fun onUpdateReceived(update: Update) = runBlocking {
        val chatId = update.message.chatId.toString()
        val message = update.message
        val reply = SendMessage()

        var email = ""
        var password = ""

        if (message.text.contains("/registration")) {
            reply.chatId = chatId
            val list = message.text.split(" ")
            println(list)
            email = list[1]
            password = list[2]
            val response = registration(email, password)
            reply.text = response
            execute(reply)
        }
    }

    private suspend fun registration(email: String, password: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val host = "http://localhost:8080/"


        val response: HttpResponse = client.post("${host}auth/register") {
            contentType(ContentType.Application.Json)
            setBody(Profile(email, password))
        }


//        val response: HttpResponse = client.get("${host}habits") {
//            headers {
//                append(HttpHeaders.Authorization,
//                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyZWQucmFiYml0IiwiZW1haWwiOiJsb2dpbkBnbS5jb20iLCJleHAiOjE3MDcwNzQzMjJ9.yDokwKDq6_FoxB2XlaALr_auNueCkcMEC0yqMB8CwNE")
//            }
//        }
        return response.body()
    }
}

@Serializable
data class Profile(
    val email: String,
    val password: String
)