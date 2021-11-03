import timers from 'timers/promises'
import AWS from 'aws-sdk'

const dynamodb = new AWS.DynamoDB()

const waitForDynamoDbToStart = async () => {
    try {
        await dynamodb.listTables().promise()
    } catch (error) {
        console.log('Waiting for DynamoDB container to start...')
        await timers.setTimeout(1000)
        return waitForDynamoDbToStart()
    }
}

const start = Date.now()
waitForDynamoDbToStart()
    .then(() => {
        console.log(`DynamoDB-local started after ${Date.now() - start}ms.`)
        process.exit(0)
    })
    .catch(error => {
        console.log('Error starting DynamoDB-local!', error)
        process.exit(1)
    })
