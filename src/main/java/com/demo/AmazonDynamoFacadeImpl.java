package com.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class AmazonDynamoFacadeImpl {

    private DynamoDB dynamoDB;

    public AmazonDynamoFacadeImpl() {
        initDynamoDbClient();
    }

    private void initDynamoDbClient() {
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }
}
