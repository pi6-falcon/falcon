package com.falcon.falcon.dataprovider.persistence.url

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "url")
data class Url(
    @DynamoDBHashKey(attributeName = "short_url")
    val shortUrl: String,
    @DynamoDBAttribute(attributeName = "long_url")
    val longUrl: String,
    @DynamoDBAttribute(attributeName = "user_identifier")
    val userIdentifier: String
)