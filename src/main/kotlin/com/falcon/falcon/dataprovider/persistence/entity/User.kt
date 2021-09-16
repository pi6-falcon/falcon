package com.falcon.falcon.dataprovider.persistence.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "user")
data class User(
    @DynamoDBHashKey(attributeName = "username")
    val username: String,
    @DynamoDBAttribute(attributeName = "password")
    val password: String,
)
