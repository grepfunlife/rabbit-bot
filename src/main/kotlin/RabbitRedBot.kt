import com.elbekd.bot.Bot
import com.elbekd.bot.feature.chain.chain
import com.elbekd.bot.model.toChatId

class RabbitRedBot {
    val profileService = ProfileService()
    val token = System.getenv("BOT_TOKEN")
    val bot = Bot.createPolling(token)

    fun bot() {

        var email = ""
        var password = ""
        var chatId = ""

        bot.onCommand("/start") { (msg, _) ->
            chatId = msg.chat.id.toString()
            println(chatId)
            val isChatIdExits = profileService.isChatIdExists(chatId)
            if (isChatIdExits == "false") {
                bot.sendMessage(msg.chat.id.toChatId(), "For registration pleas send /registration")
            } else {
                bot.sendMessage(msg.chat.id.toChatId(), "For login pleas send /login")
            }

        }

        bot.chain("/registration") { msg ->
            chatId = msg.chat.id.toString()
            println(chatId)
            val isChatIdExits = profileService.isChatIdExists(chatId)
            if (isChatIdExits == "false") {
                bot.sendMessage(msg.chat.id.toChatId(), "Hi! Pleas send me your email")
            }
        }
            .then { msg ->
                email = msg.text.toString()
                bot.sendMessage(msg.chat.id.toChatId(), "Thank you! Pleas send me your password")
            }
            .then { msg ->
                password = msg.text.toString()
                chatId = msg.chat.id.toChatId().toString()
                val response = profileService.registration(email, password, chatId)
                bot.sendMessage(msg.chat.id.toChatId(), "Fine! $response")
            }
            .build()

        bot.chain("/login") { msg ->
            bot.sendMessage(msg.chat.id.toChatId(), "Hi! Pleas send me your email")
        }
            .then { msg ->
                email = msg.text.toString()
                bot.sendMessage(msg.chat.id.toChatId(), "Thank you! Pleas send me your password")
            }
            .then { msg ->
                password = msg.text.toString()
                val response = profileService.login(email, password)
                bot.sendMessage(msg.chat.id.toChatId(), "Fine! $response")
            }
            .build()



        bot.start()
    }
}