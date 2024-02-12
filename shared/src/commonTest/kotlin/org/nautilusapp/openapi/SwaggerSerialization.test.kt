package org.nautilusapp.openapi

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals


class SwaggerSerializationTest {
    @Test
    fun testSwaggerSerialization(){
        val json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        val deserialize = json.decodeFromString<SwaggerManifest>(MANIFEST)

        assertEquals(deserialize.openapi, "3.0.3")
    }
}