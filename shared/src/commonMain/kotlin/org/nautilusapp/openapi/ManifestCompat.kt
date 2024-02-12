package org.nautilusapp.openapi

fun manifestCompat(manifest: SwaggerManifest, compare: SwaggerManifest, usedPaths: List<String>): Boolean {
    if(manifest.openapi != compare.openapi ) return false
    if(manifest.info.version.trim().lowercase() != compare.info.version.trim().lowercase()) return false

    compare.paths?.let {
        if(manifest.paths == null) return false
        for(pathname in usedPaths) {
            val retryString = "${pathname}/" //account for possibility of trailing /
            val comppath = if(compare.paths[pathname] != null)
                compare.paths[pathname] else compare.paths[retryString]
            val path =  if(manifest.paths[pathname] != null)
                manifest.paths[pathname] else manifest.paths[retryString]
            comppath?.let {
                if (!pathCompatible(path, comppath)) return false
            }
        }
    }

    return true
}

fun pathCompatible(path: PathItem?, compare: PathItem): Boolean {
    if(path == null) return false

    compare.get?.let {
        if(!operationCompatible(path.get, compare.get)) return false
    }
    compare.put?.let {
        if(!operationCompatible(path.put, compare.put)) return false
    }
    compare.post?.let {
        if(!operationCompatible(path.post, compare.post)) return false
    }
    compare.delete?.let {
        if(!operationCompatible(path.delete, compare.delete)) return false
    }
    compare.options?.let {
        if(!operationCompatible(path.options, compare.options)) return false
    }
    compare.head?.let {
        if(!operationCompatible(path.head, compare.head)) return false
    }
    compare.patch?.let {
        if(!operationCompatible(path.patch, compare.patch)) return false
    }
    compare.trace?.let {
        if(!operationCompatible(path.trace, compare.trace)) return false
    }

    path.parameters?.let {
        for(param in path.parameters) {
            val comp = compare.parameters?.find { it.name == param.name }
            if(!paramsCompatible(param, comp)) return false
        }
    }

    return true
}

fun operationCompatible(op: OperationObject?, compare: OperationObject): Boolean {
    if(op == null) return false
    if(op.parameters != null && compare.parameters == null) return false
    op.parameters?.let {
        for(param in op.parameters) {
            val comp = compare.parameters?.find { it.name == param.name }
            if(!paramsCompatible(param, comp)) return false
        }
    }
    if(!requestCompatible(op.requestBody, compare.requestBody)) return false
    compare.responses?.let {
        if(op.responses == null) return false
        for((code, response) in compare.responses) {
            if(!responseCompatible(op.responses[code], response)) return false
        }
    }
    if(op.deprecated != compare.deprecated) {
        println("WARNING: use of deprecated endpoint")
    }
    return true
}

fun paramsCompatible(params: ParamsObject, compare: ParamsObject?): Boolean {
    if(compare == null) return false
    if(params._in != compare._in) return false
    if(params.name != compare.name) return false
    if(params.required != compare.required) return false
    if(params.schema != compare.schema) return false
    if(params.deprecated != compare.deprecated) {
        println("WARNING: use of deprecated parameter")
    }

    return true
}

fun requestCompatible(request: RequestBodyObject?, compare: RequestBodyObject?): Boolean {
    request?.let {
        if(compare == null) return false
        if(request.required == true && compare.required != true) return false
        return RequestBodyObject(content= request.content, ref = request.ref) ==
                RequestBodyObject(content = compare.content, ref = compare.ref)
    }
    return true
}

fun responseCompatible(response: ResponseObject?, compare: ResponseObject): Boolean {
    if(response == null) return false
    return response.content == compare.content && response.items == compare.items && response.anyOf == compare.anyOf
}