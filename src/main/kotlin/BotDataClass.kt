import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val email: String,
    val password: String,
    val chatId: String?
)
