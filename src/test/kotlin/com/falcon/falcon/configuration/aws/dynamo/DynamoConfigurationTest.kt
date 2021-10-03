package com.falcon.falcon.configuration.aws.dynamo

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamoConfigurationTest {

    private val dynamoConfiguration: DynamoConfiguration =
        DynamoConfiguration("dummy-access-key", "dummy-secret-key", "dummy-aws-endpoint")


    @Nested
    inner class AmazonDynamoDB {
        @Test
        fun `Should instantiate client, apply aws endpoint then return it`() {

        }
    }

    @Nested
    inner class AmazonAWSCredentials {
        @Test
        fun `Should instantiate AWS Credentials with access key and secret key`() {

        }
    }
}
