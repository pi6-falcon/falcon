package com.falcon.falcon.dataprovider.persistence.urlredirecthistory

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.util.UUID

@DynamoDBTable(tableName = "redirect_history")
data class UrlRedirectHistoryEntity(
    @DynamoDBHashKey(attributeName = "id")
    var id: UUID = UUID.randomUUID(),
    @DynamoDBAttribute(attributeName = "short_url")
    var shortUrl: String = "",
    @DynamoDBAttribute(attributeName = "from")
    var from: String = "",
    @DynamoDBAttribute(attributeName = "date")
    var date: String = "",
)
