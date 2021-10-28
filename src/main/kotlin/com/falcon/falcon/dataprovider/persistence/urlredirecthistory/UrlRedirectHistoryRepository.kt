package com.falcon.falcon.dataprovider.persistence.urlredirecthistory

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UrlRedirectHistory : CrudRepository<UrlRedirectHistoryEntity, String> {
}