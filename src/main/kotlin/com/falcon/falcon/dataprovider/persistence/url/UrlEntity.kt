package com.falcon.falcon.dataprovider.persistence.url

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "url")
data class UrlEntity(
    @DynamoDBHashKey(attributeName = "short_url")
    var shortUrl: String = "",
    @DynamoDBAttribute(attributeName = "long_url")
    var longUrl: String = "",
    @DynamoDBAttribute(attributeName = "user_identifier")
    var userIdentifier: String = "",
    @DynamoDBAttribute(attributeName = "is_custom")
    var isCustom: Boolean = false,
)
