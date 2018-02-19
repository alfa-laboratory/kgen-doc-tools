package ru.alfabank.ecomm.dcreator.server.responses

interface Request

data class NewFileRequest(val path: String, val type: String): Request

data class RenameRequest(val path: String, val name: String): Request