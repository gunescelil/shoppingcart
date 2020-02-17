package com.shoppingcart.data;

import com.shoppingcart.enums.DiscountType;

public class Campaign
{
    private Category category;

    private Double discount;

    private Integer limitRule;

    private DiscountType discountType;

    public Campaign(Category category, Double discount, Integer limitRule, DiscountType discountType)
    {
        this.category = category;
        this.discount = discount;
        this.limitRule = limitRule;
        this.discountType = discountType;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Double getDiscount()
    {
        return discount;
    }

    public void setDiscount(Double discount)
    {
        this.discount = discount;
    }

    public Integer getLimitRule()
    {
        return limitRule;
    }

    public void setLimitRule(Integer limitRule)
    {
        this.limitRule = limitRule;
    }

    public DiscountType getDiscountType()
    {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType)
    {
        this.discountType = discountType;
    }

}
