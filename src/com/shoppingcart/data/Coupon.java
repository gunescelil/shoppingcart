package com.shoppingcart.data;

import com.shoppingcart.enums.DiscountType;

public class Coupon
{
    private Integer minimumPurchaseLimit;

    private Integer discountAmount;

    private DiscountType discountType;

    public Coupon(Integer minimumPurchaseLimit, Integer discountAmount, DiscountType discountType)
    {
        this.minimumPurchaseLimit = minimumPurchaseLimit;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    public Integer getMinimumPurchaseLimit()
    {
        return minimumPurchaseLimit;
    }

    public void setMinimumPurchaseLimit(Integer minimumPurchaseLimit)
    {
        this.minimumPurchaseLimit = minimumPurchaseLimit;
    }

    public Integer getDiscountAmount()
    {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
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
