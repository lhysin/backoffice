package io.backoffice.common.external

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

@Component
@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
interface JsonPlaceholderClient {
    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable("id") id: Int): Post

    @GetMapping("/posts")
    fun getAllPosts(): List<Post>

}