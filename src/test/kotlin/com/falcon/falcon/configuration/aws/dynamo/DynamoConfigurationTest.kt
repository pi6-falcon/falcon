package com.falcon.falcon.configuration.aws.dynamo

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamoConfigurationTest {

    private val dynamoConfiguration: DynamoConfiguration =
        DynamoConfiguration("dummy-access-key", "dummy-secret-key", "dummy-endpoint")

    @Test
    fun `Should return an instance of AmazonDynamoDB`() {
        // When
        val result = dynamoConfiguration.amazonDynamoDB()
        // Then
        result.shouldBeInstanceOf<AmazonDynamoDB>()
    }

    @Test
    fun `Should return an instance of amazonAWSCredentials`() {
        // When
        val result = dynamoConfiguration.amazonAWSCredentials()
        // Then
        result.shouldBeInstanceOf<BasicAWSCredentials>()
        result.awsSecretKey.shouldBe("dummy-secret-key")
        result.awsAccessKeyId.shouldBe("dummy-access-key")
    }
}
