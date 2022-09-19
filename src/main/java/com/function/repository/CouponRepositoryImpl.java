package com.function.repository;

import java.util.ArrayList;
import java.util.List;
import com.function.model.Coupon;
import com.function.model.Category;
import com.function.model.Characteristic;
import com.function.Enum.CategoryEnumId;
import com.function.Enum.CategoryEnumName;
import com.function.Enum.TypesEnum;
import com.function.model.Amount;
import com.microsoft.azure.functions.ExecutionContext;

public class CouponRepositoryImpl {
    
	Coupon coupon;
	Characteristic characteristic;
	Amount amount;
	Category category;
	List<Category> categoryList;
	List<Coupon> couponList;
	ExecutionContext context;
	
    protected String getSetting(String setting) {
        return System.getenv(setting);
    }
	
	public List<Coupon> findCouponsWelcome(String userId, String[] categoryNameReq) {
		
		coupon = new Coupon();
		characteristic = new Characteristic();
		amount = new Amount();
		category = new Category();
		categoryList = new ArrayList<>();
		couponList = new ArrayList<>();
	
		category.setId(CategoryEnumId.valueOf(getSetting("couponCategoriesId")));
		category.setName(CategoryEnumName.valueOf(getSetting("couponCategoriesName")));
		categoryList.add(category);
		
		amount.setValue(Float.valueOf(getSetting("couponCharacteristicsAmountValue")));
		amount.setCurrency(getSetting("couponCharacteristicsAmountCurrency"));
		amount.setTaxIncluded(Boolean.parseBoolean((getSetting("couponCharacteristicsAmountTaxIncluded"))));
		
		characteristic.setDescription((getSetting("couponCharacteristicsDescription")));
		characteristic.setType(TypesEnum.valueOf((getSetting("couponCharacteristicsType"))));
		characteristic.setAmount(amount);
				
		coupon.setId((getSetting("couponId")));
		coupon.setName(getSetting("couponName"));
		coupon.setActivationCode((getSetting("couponActivationCode")));
		coupon.setStartDate((getSetting("couponStartDate")));
		coupon.setEndDate((getSetting("couponEndDate")));
		coupon.setCategory(categoryList);
		coupon.setCharacteristic(characteristic);
		
		couponList.add(coupon);
		

	    return couponList;
	}

}
