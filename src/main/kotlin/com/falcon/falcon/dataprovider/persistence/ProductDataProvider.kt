package com.falcon.falcon.dataprovider.persistence

import mu.KotlinLogging
import org.springframework.stereotype.Component

interface ProductDataProvider {
    /*
    this class is responsible for converting core entity to persistence entity and vice-versa.
     */
    fun saveProduct()
}

@Component
class ProductDataProviderImpl : ProductDataProvider {

    private val log = KotlinLogging.logger {}

    /*
    JPA repository should be injected here!
     */

    override fun saveProduct() {
        log.info { "saving product..." }
    }

}
