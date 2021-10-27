package com.falcon.falcon.dataprovider.persistence.user

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum
import com.falcon.falcon.core.enumeration.UserType

@DynamoDBTable(tableName = "user")
data class UserEntity(
    @DynamoDBHashKey(attributeName = "username")
    var username: String = "",
    @DynamoDBAttribute(attributeName = "password")
    var password: String = "",
    @DynamoDBTypeConvertedEnum
    var type: UserType = UserType.PERMANENT,
    /**
     * Receives an unix EPOCH as value
     */
    @DynamoDBAttribute(attributeName = "expdate")
    var expirationDate: Long? = null
)
