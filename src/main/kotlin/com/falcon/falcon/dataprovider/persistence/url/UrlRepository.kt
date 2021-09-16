package com.falcon.falcon.dataprovider.persistence.url

import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : CrudRepository<Url, String> {}
