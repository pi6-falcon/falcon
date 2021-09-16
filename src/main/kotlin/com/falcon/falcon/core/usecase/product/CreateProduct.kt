package com.falcon.falcon.core.usecase.product

//import com.falcon.falcon.core.entity.Url
//import com.falcon.falcon.dataprovider.persistence.ProductDataProvider
//import mu.KotlinLogging
//import org.springframework.stereotype.Service
//
//interface CreateProduct {
//    fun createProduct()
//}
//
//@Service
//class CreateProductImpl(private val productDataProvider: ProductDataProvider) : CreateProduct {
//
//    private val log = KotlinLogging.logger {}
//
//    override fun createProduct() {
//        log.info { "creating product..." }
//        val url = Url("this-product-is-from-core-package")
//        productDataProvider.saveProduct() // repository abstraction
//
//        // business rules
//    }
//
//}
