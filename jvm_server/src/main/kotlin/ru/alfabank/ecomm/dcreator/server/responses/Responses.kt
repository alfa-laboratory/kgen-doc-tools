package ru.alfabank.ecomm.dcreator.server.responses

interface Response {
    val status: Boolean
}

data class StatusResponse(override val status: Boolean) : Response

enum class FileType {
    File, Folder
}

data class FileInfo(val name: String, val type: FileType)

data class FilesResponse(
    override val status: Boolean,
    val files: List<FileInfo>
) : Response