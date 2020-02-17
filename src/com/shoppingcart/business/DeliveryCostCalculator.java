package com.shoppingcart.business;

import java.util.Map;
import java.util.stream.Collectors;

import com.shoppingcart.data.Category;
import com.shoppingcart.data.Product;

public class DeliveryCostCalculator
{
    private double costPerDelivery;

    private double costPerProduct;

    private double fixedCost;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost)
    {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public double calculateFor(ShoppingCart cart)
    {
        int numberOfDeliveries = getDiscintCategoriesInChart(cart);
        int numberOfProducts = getNumberOfProductsInChart(cart);
        return (this.costPerDelivery * numberOfDeliveries) + (this.costPerProduct * numberOfProducts) + this.fixedCost;
    }

    private int getDiscintCategoriesInChart(ShoppingCart cart)
    {
        Map<Category, Long> result = cart.getListOfProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));      
        return result.size();
    }
    
    private int getNumberOfProductsInChart(ShoppingCart cart)
    {
        Map<String, Long> result = cart.getListOfProducts().stream()
                .collect(Collectors.groupingBy(Product::getTitle, Collectors.counting()));      
        return result.size();
    }

    public Double getCostPerDelivery()
    {
        return costPerDelivery;
    }

    public void setCostPerDelivery(double costPerDelivery)
    {
        this.costPerDelivery = costPerDelivery;
    }

    public double getCostPerProduct()
    {
        return costPerProduct;
    }

    public void setCostPerProduct(double costPerProduct)
    {
        this.costPerProduct = costPerProduct;
    }

    public double getFixedCost()
    {
        return fixedCost;
    }

    public void setFixedCost(double fixedCost)
    {
        this.fixedCost = fixedCost;
    }

}
