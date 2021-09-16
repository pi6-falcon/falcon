package com.falcon.falcon.dataprovider.persistence.urlredirecthistory

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.time.LocalDateTime

@DynamoDBTable(tableName = "redirect_history")
data class UrlRedirectHistory(
    @DynamoDBHashKey(attributeName = "short_url")
    val shortUrl: String,
    @DynamoDBAttribute(attributeName = "from")
    val from: String,
    @DynamoDBAttribute(attributeName = "date")
    val date: LocalDateTime,
)
