package com.falcon.falcon.configuration.aws.dynamo

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.falcon.falcon.dataprovider.persistence"])
class DynamoConfiguration(
    @Value("\${aws.access_key_id}")
    private var awsAccessKeyId: String,
    @Value("\${aws.secret_key}")
    private var awsSecretKey: String,
    @Value("\${aws.endpoint}")
    private var awsEndpoint: String,
) {

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB = AmazonDynamoDBClient().apply {
        setEndpoint(awsEndpoint)
    }

    @Bean
    fun amazonAWSCredentials() = BasicAWSCredentials(awsAccessKeyId, awsSecretKey)
}
