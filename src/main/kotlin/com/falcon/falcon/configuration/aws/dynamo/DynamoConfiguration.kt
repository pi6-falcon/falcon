package com.falcon.falcon.configuration.aws.dynamo

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableDynamoDBRepositories
    (basePackages = arrayOf("com.falcon.falcon.dataprovider.persistence"))
class DynamoConfiguration(
    @Value("\${envs.aws.dynamodb.endpoint}")    private val ENDPOINT        : String,
    @Value("\${envs.aws.dynamodb.accesskey}")   private val ACCESS_KEY      : String,
    @Value("\${envs.aws.dynamodb.secretkey}")   private val SECRET_KEY      : String
) {

    @Bean
    fun amazonDynamoDB() : AmazonDynamoDB {
        val AWS_DYNAMODB = AmazonDynamoDBClient()

        AWS_DYNAMODB.setEndpoint(ENDPOINT)

        return AWS_DYNAMODB
    }


    @Bean
    fun amazonAWSCredentials() = BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)

}
