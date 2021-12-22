package com.demo.handler;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.demo.AmazonDynamoFacadeImpl;
import com.demo.JjwtImpl;
import com.demo.LoginRequest;
import com.google.common.collect.Sets;

import java.util.Set;

public class UserLoginHandler implements RequestHandler<LoginRequest, String> {

    private static final Set<String> ALLOWED_EMAILS = Sets.newHashSet("admin@example.com");
    private static final String PASSWORD = "admin";

    @Override
    public String handleRequest(LoginRequest loginRequest, Context context) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if(!ALLOWED_EMAILS.contains(email) || !PASSWORD.equals(password)){
            return "provided email password pair not allowed";
        }

        String jwtToken = JjwtImpl.generateJwt();

        AmazonDynamoFacadeImpl amazonDynamoFacade = new AmazonDynamoFacadeImpl();
        DynamoDB dynamoDb = amazonDynamoFacade.getDynamoDB();

        //System.out.println(dynamoDb);

        Table table = dynamoDb.getTable("user");

        Item item = new Item().withPrimaryKey("email", email)
                                .withString("token", jwtToken);
        table.putItem(item);

        return jwtToken;
    }
}
