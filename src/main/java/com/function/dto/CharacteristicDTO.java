package com.function.dto;

import com.function.Enum.TypesEnum;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class CharacteristicDTO {
    private String description;

    private TypesEnum type;
    
    private AmountDTO amount;
}
