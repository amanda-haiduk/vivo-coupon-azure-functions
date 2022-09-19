package com.function.model;

import com.function.Enum.TypesEnum;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class Characteristic {
    
    private String description;

    private TypesEnum type;
    
    private Amount amount;


}
