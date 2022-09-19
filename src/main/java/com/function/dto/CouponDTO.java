package com.function.dto;

import java.util.List;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class CouponDTO {
    private String id;

    private String name;

    private String activationCode;

    private String startDate;
    
    private String endDate;    
    
    private CharacteristicDTO characteristic;
    
    private List<CategoryDTO> category;
}
