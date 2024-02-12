@file:Suppress("PropertyName")

package org.nautilusapp.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SwaggerManifest(
    val openapi: String, //"3.0.3"
    val info: Info,
    val paths: Map<String, PathItem>? = null,
    val components: Components? = null,
)

@Serializable
data class Components(
    val schemas: Map<String, SchemaObject>? = null,
    val responses: Map<String, ResponseObject>? = null,
    val parameters: Map<String, ParamsObject>? = null,
    val requestBodies: Map<String, RequestBodyObject>? = null,
    val headers: Map<String, ParamsObject>? = null,
)

@Serializable
data class Info(
    val title: String,
    val description: String,
    val version: String
)

@Serializable
data class PathItem(
    @SerialName("\$ref") val ref: String? = null,
    val summary: String? = null,
    val description: String? = null,
    val get: OperationObject? = null,
    val put: OperationObject? = null,
    val post: OperationObject? = null,
    val delete: OperationObject? = null,
    val options: OperationObject? = null,
    val head: OperationObject? = null,
    val patch: OperationObject? = null,
    val trace: OperationObject? = null,
    val parameters: List<ParamsObject>? = null
    )

@Serializable
data class OperationObject(
    val tags: List<String>? = null,
    val summary: String? = null,
    val description: String? = null,
    val operationId: String? = null,
    val parameters: List<ParamsObject>? = null,
    val requestBody: RequestBodyObject? = null,
    val responses: Map<String, ResponseObject>? = null,
    val deprecated: Boolean? = null,
    //ignoring security and servers fields
)

@Serializable
data class RefObject(
    @SerialName("\$ref") val ref: String,
    val summary: String? = null,
    val description: String? = null,
)

@Serializable
data class ParamsObject( //can also be a ref object
    val name: String? = null,
    @SerialName("in") val _in: String? = null,
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: String? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: SchemaObject? = null,
    //ignoring example and examples fields
    val content: Map<String, MediaTypeObject>? = null,
    @SerialName("\$ref") val ref: String? = null,
    val summary: String? = null,
)

@Serializable
data class SchemaObject(
    val type: String? = null,
    val anyOf: List<SchemaObject>? = null,
    val items: SchemaObject? = null,
    val required: List<String>? = null,
    val properties: Map<String, SchemaObject>? = null,
    val additionalProperties: Boolean? = null,
    val patternProperties: Map<String, SchemaObject>? = null,
    val format: String? = null
)

@Serializable
data class MediaTypeObject(
    val schema: SchemaObject? = null,
)

@Serializable
data class RequestBodyObject( //can also be a ref object
    val description: String? = null,
    val content: Map<String, MediaTypeObject>? = null,
    val required: Boolean? = null,
    @SerialName("\$ref") val ref: String? = null,
    val summary: String? = null
)

@Serializable
data class ResponseObject(
    val description: String? = null,
    val content: Map<String, MediaTypeObject>? = null,
    val headers: Map<String, ParamsObject>? = null,
    val summary: String? = null,
    val items: SchemaObject? = null,
    val anyOf: List<SchemaObject>? = null,
    @SerialName("\$ref") val ref: String? = null
)