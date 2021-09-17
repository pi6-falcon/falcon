package com.falcon.falcon.dataprovider.persistence.user

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UserRepository : CrudRepository<UserEntity, String> {}
