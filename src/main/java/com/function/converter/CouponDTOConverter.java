package com.function.converter;

import com.function.dto.CategoryDTO;
import com.function.dto.AmountDTO;
import com.function.dto.CharacteristicDTO;
import com.function.model.Coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.function.dto.CouponDTO;

public class CouponDTOConverter {
    public  List<CouponDTO> generateResponse(List<Coupon> result) {

        return result.stream().map(i -> {
        	CharacteristicDTO characteristicdto = new CharacteristicDTO();
        	characteristicdto.setType(i.getCharacteristic().getType());
        	AmountDTO amountdto = new AmountDTO();
        	amountdto.setValue(i.getCharacteristic().getAmount().getValue());
        	amountdto.setCurrency(i.getCharacteristic().getAmount().getCurrency());
        	amountdto.setTaxIncluded(i.getCharacteristic().getAmount().getTaxIncluded());
        	characteristicdto.setAmount(amountdto);
        	characteristicdto.setDescription(i.getCharacteristic().getDescription());
        	
        	CategoryDTO categorydto = new CategoryDTO();
        	categorydto.setId(i.getCategory().get(0).getId());
        	categorydto.setName(i.getCategory().get(0).getName());
        	ArrayList<CategoryDTO> categories = new ArrayList<>();
        	categories.add(categorydto);
        	
        	
        	CouponDTO dto = new CouponDTO();
            dto.setId(i.getId());
            dto.setName(i.getName());
            dto.setActivationCode(i.getActivationCode());
            dto.setStartDate(i.getStartDate());
            dto.setEndDate(i.getEndDate());
            dto.setCharacteristic(characteristicdto);
            dto.setCategory(categories);
            
            return dto;
        }).collect(Collectors.toList());
    }
}
