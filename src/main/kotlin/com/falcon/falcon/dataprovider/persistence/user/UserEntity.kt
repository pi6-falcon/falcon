package com.falcon.falcon.dataprovider.persistence.user

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "user")
data class UserEntity(
    @DynamoDBHashKey(attributeName = "username")
    var username: String = "",
    @DynamoDBAttribute(attributeName = "password")
    var password: String = "",
)