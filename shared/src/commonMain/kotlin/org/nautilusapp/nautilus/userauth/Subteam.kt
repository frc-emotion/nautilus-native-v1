package org.nautilusapp.nautilus.userauth

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = SubteamSerializer::class)
enum class Subteam {
    NONE,
    SOFTWARE,
    ELECTRICAL,
    BUILD,
    MARKETING,
    DESIGN,
    EXECUTIVE,
}

object SubteamSerializer: KSerializer<Subteam> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Subteam", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Subteam {
        return when(decoder.decodeString().trim().lowercase()) {
            "software" -> Subteam.SOFTWARE
            "electrical" -> Subteam.ELECTRICAL
            "build" -> Subteam.BUILD
            "marketing" -> Subteam.MARKETING
            "design" -> Subteam.DESIGN
            "executive" -> Subteam.EXECUTIVE
            else -> Subteam.NONE
        }
    }

    override fun serialize(encoder: Encoder, value: Subteam) {
//        TODO("Not yet implemented")
        val string = value.name.lowercase()
        encoder.encodeString(string)
    }

}