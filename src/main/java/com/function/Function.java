package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.function.repository.CouponRepositoryImpl;

import com.function.model.Coupon;
import com.function.converter.CouponDTOConverter;
/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("userCoupons")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                route = "users/{userId}",
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage <Optional<String>> request,
                @BindingName("userId") String userId,
            final ExecutionContext context){
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("category");             
        
		List<Coupon> result = new ArrayList<>();
        CouponRepositoryImpl repository = new CouponRepositoryImpl();
        
        if (query == null) {
            result.addAll(repository.findCouponsWelcome(userId, null));
        } else {  
            String[] category = query.split(",");
            for(String name:category) {  
                if (name.equals("voucher-vivo-play-birthday")){
                    return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Coupon Type not found birthday.").build();
                } else if (name.equals("voucher_vivo_play_welcome")) {
                    result.addAll(repository.findCouponsWelcome(userId, category));
                } else {
                    return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Coupon Type not found").build();
                }
            }
            
        }
		
        CouponDTOConverter converter = new CouponDTOConverter(); 
        return request.createResponseBuilder(HttpStatus.OK).body(converter.generateResponse(result)).build();

    }

}
