import AWS from 'aws-sdk'

const dynamodb = new AWS.DynamoDB()

const generateTables = async () => {
    createTable("url", "short_url", "short_url")
    createTable("user", "username", "username")
    createTable("redirect_history", "id", "id")
}

function createTable(tableName,  keySchema, attributeDefinitions) {
    const params = {
        TableName: tableName,
        KeySchema: [
            {AttributeName: keySchema, KeyType: "HASH"},
        ],
        AttributeDefinitions: [
            {AttributeName: attributeDefinitions, AttributeType: "S"},
        ],
        ProvisionedThroughput: {
            ReadCapacityUnits: 1,
            WriteCapacityUnits: 1
        }
    };

    dynamodb.createTable(params, function(err, data) {
        if (err) {
            console.error("Unable to create table. Error JSON:", JSON.stringify(err, null, 2));
        } else {
            console.log("Created table. Table description JSON:", JSON.stringify(data, null, 2));
        }
    });
}

generateTables().then(r => console.log(r))
