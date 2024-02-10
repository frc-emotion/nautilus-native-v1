package org.team2658.nautilus.userauth

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable(with = AccountTypeSerializer::class)
enum class AccountType(val value: Int) {
    SUPERUSER(4),
    ADMIN(3),
    LEAD(2),
    BASE(1),
    UNVERIFIED(0),;

    companion object {
        fun of(value: Int?): AccountType {
            return when(value) {
                in 4..Int.MAX_VALUE -> SUPERUSER
                3 -> ADMIN
                2 -> LEAD
                1 -> BASE
                else -> UNVERIFIED
            }
        }
    }
}


object AccountTypeSerializer: KSerializer<AccountType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("AccountType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): AccountType {
        val num = decoder.decodeInt()
        return AccountType.of(num)
    }

    override fun serialize(encoder: Encoder, value: AccountType) {
        val number = value.value
        encoder.encodeInt(number)
    }

}