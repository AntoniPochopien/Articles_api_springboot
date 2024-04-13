package com.articles.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArticlesApiApplication

fun main(args: Array<String>) {
	runApplication<ArticlesApiApplication>(*args)
}
