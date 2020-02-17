package com.shoppingcart;

import com.shoppingcart.business.DeliveryCostCalculator;
import com.shoppingcart.business.ShoppingCart;
import com.shoppingcart.data.Campaign;
import com.shoppingcart.data.Category;
import com.shoppingcart.data.Coupon;
import com.shoppingcart.data.Product;
import com.shoppingcart.enums.DiscountType;

public class Application
{

    public static void main(final String[] args)
    {
        Category food = new Category("food");
        
        Product apple = new Product("Apple", 100.0, food);       
        Product almond = new Product("Almonds", 150.0, food);
        
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apple, 3);
        cart.addItem(almond, 1);
        
        Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
        Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
        Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);
        
        cart.applyDiscounts(campaign1, campaign2, campaign3);
        
        Coupon coupon = new Coupon(100, 10, DiscountType.Rate);        
        cart.applyCoupon(coupon);
        
        double costPerDelivery = 1.0;
        double costPerProduct = 1.0;
        double fixedCost = 2.99;
        
        DeliveryCostCalculator deliveryCostCalculator = new 
                DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
        
        double deliveryCost = deliveryCostCalculator.calculateFor(cart);
        
        cart.setDeliveryCost(deliveryCost);
        
        cart.print();
        
    }

}
