package ru.alfabank.ecomm.dcreator.server.utils

import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

private val zipFiles by lazy {
    ClassLoader::class.java.getResourceAsStream("/files.zip")
}

fun unzipFiles(workDir: File) {
    ZipInputStream(zipFiles).use { zipInputStream ->
        while (true) {
            val entry: ZipEntry = zipInputStream.nextEntry ?: break

            try {
                val output = File(workDir, entry.name)

                if (entry.isDirectory) {
                    output.mkdirs()
                } else {
                    output.createNewFile()
                    output.outputStream().use { zipInputStream.copyTo(it) }
                }
            } finally {
                zipInputStream.closeEntry()
            }
        }
    }
}

fun zipFiles(inputDirectory: File): File {
    val tempFile = createTempFile("zipArchive_", ".zip")

    ZipOutputStream(tempFile.outputStream()).use { outputStream ->
        inputDirectory.walkTopDown().forEach { file ->
            if (file.isFile) {
                outputStream.putNextEntry(ZipEntry(file.toRelativeString(inputDirectory)))
                file.inputStream().copyTo(outputStream)
                outputStream.closeEntry()
            }
        }
    }

    return tempFile
}