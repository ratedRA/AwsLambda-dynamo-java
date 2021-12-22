package com.demo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "user")
public class DynamoUser {

    @DynamoDBHashKey
    private String token;

    public String getId() {
        return getToken();
    }


    public void setId(String id) {
        setToken(id);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
