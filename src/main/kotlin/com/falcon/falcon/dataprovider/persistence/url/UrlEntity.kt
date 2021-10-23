package com.falcon.falcon.dataprovider.persistence.url

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum
import com.falcon.falcon.core.enumeration.UrlType

@DynamoDBTable(tableName = "url")
data class UrlEntity(
    @DynamoDBHashKey(attributeName = "short_url")
    var shortUrl: String = "",
    @DynamoDBAttribute(attributeName = "long_url")
    var longUrl: String = "",
    @DynamoDBAttribute(attributeName = "user_identifier")
    var userIdentifier: String = "",
    @DynamoDBAttribute(attributeName = "url_type")
    @DynamoDBTypeConvertedEnum
    var type: UrlType = UrlType.RANDOM,
    /**
     * Receives an unix EPOCH as value
     */
    @DynamoDBAttribute(attributeName = "expdate")
    var expirationDate: Long? = null
)
