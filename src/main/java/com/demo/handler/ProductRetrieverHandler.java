package com.demo.handler;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.demo.AmazonDynamoFacadeImpl;
import com.demo.DomainProduct;
import com.demo.GetProductRequest;
import com.demo.JjwtImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;

public class ProductRetrieverHandler implements RequestHandler<GetProductRequest, String> {

    private static String DYNAMODB_TABLE_NAME = "user";

    @Override
    public String handleRequest(GetProductRequest productRequest, Context context) {

        AmazonDynamoFacadeImpl amazonDynamoFacade = new AmazonDynamoFacadeImpl();
        DynamoDB dynamoDB = amazonDynamoFacade.getDynamoDB();
        Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);

        String email = null;

        try {
            Jws<Claims> claimsJws = JjwtImpl.validateJwt(productRequest.getJwtToken());
            email = (String) claimsJws.getBody().get("email");

            GetItemSpec getItemSpec = new GetItemSpec().withPrimaryKey("email", email);
            Item item = table.getItem(getItemSpec);
            String token = item.getString("token");

            //System.out.println(email);

            //System.out.println(token);

            assert productRequest.getJwtToken().equals(token):"differentTokens";

        } catch (ExpiredJwtException e) {

            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("email", "admin@example.com");

            table.deleteItem(deleteItemSpec);

            throw new RuntimeException("token is expired", e);

        } catch (SignatureException e) {
            throw new RuntimeException("invalid token exception", e);

        } catch(Exception e){
            throw new RuntimeException("exception occurred while parsing exception", e);
        }

        return new DomainProduct(1L, "demo", "demo with lambda and dynamo").toString();
    }
}
