package com.falcon.falcon.entrypoint.rest

import com.falcon.falcon.core.usecase.product.CreateProduct
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(private val createProductUseCase: CreateProduct) {

    @PostMapping
    fun createProduct(): String {
        createProductUseCase.createProduct()
        return "teste"
    }
}
