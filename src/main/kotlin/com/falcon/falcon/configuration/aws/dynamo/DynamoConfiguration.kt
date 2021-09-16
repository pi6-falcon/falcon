package com.falcon.falcon.configuration.aws.dynamo

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoConfiguration {

    @Bean
    fun generateDynamoDb(): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain()).build()
}
