package com.function.model;
import java.util.List;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class Coupon {

    private String id;

    private String name;

    private String activationCode;

    private String startDate;
    
    private String endDate;    
    
    private Characteristic characteristic;
    
    private List<Category> category;

}
