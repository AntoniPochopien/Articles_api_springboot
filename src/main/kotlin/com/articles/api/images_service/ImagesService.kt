package com.articles.api.images_service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Service
class ImagesService {

    fun saveImage(
        file: MultipartFile,
        articleId: Int,
        imageIndex:Int,
    ): String {
        val dir = "images/articles/$articleId/"
        try {
            val absoluteApplicationDirectory = Paths.get(System.getProperty("user.dir"))
            val targetDirectory = absoluteApplicationDirectory.resolve(dir)
            Files.createDirectories(targetDirectory)
            val targetFile = targetDirectory.resolve("$imageIndex.png")
            Files.copy(file.inputStream, targetFile)
        } catch (e: Exception) {
            println("saveImage unexpected error: $e")
        }
        return "$dir$imageIndex.png"
    }

}