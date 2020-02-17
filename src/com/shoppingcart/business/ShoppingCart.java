package com.shoppingcart.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shoppingcart.data.Campaign;
import com.shoppingcart.data.Category;
import com.shoppingcart.data.Coupon;
import com.shoppingcart.data.Product;
import com.shoppingcart.enums.DiscountType;

public class ShoppingCart
{
    private List<Product> listOfProducts;
    private double campaignDiscount;
    private double couponDiscount;
    private double deliveryCost;

    public ShoppingCart()
    {
        this.listOfProducts = new ArrayList<>();
        this.campaignDiscount = 0;
        this.couponDiscount = 0;
        this.deliveryCost = 0;
    }

    public List<Product> getListOfProducts()
    {
        return listOfProducts;
    }

    public void addItem(Product product, int count)
    {
        for (int i = 0; i < count; i++)
        {
            Product newProduct = new Product(product.getTitle(), product.getPrice(), product.getCategory());
            this.listOfProducts.add(newProduct);
        }
    }

    public void print()
    {
        Map<Category, List<Product>> listsByCategory = this.getListOfProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        for (Entry<Category, List<Product>> categoryGroup : listsByCategory.entrySet())
        {
            System.out.println("Category: " + categoryGroup.getKey().getTitle() + System.lineSeparator());

            Map<String, List<Product>> productsByTitle = categoryGroup.getValue().stream()
                    .collect(Collectors.groupingBy(Product::getTitle));

            for (Entry<String, List<Product>> productGroup : productsByTitle.entrySet())
            {
                String productName = productGroup.getKey();
                int quantity = productGroup.getValue().size();
                double unitPrice = productGroup.getValue().get(0).getPrice();
                double totalPrice = quantity * unitPrice;
                System.out.println("Product: " + productName + " " + "Quantity: " + quantity + " " + "Unit Price: "
                        + unitPrice + " " + "TotalPrice: " + totalPrice + System.lineSeparator());
            }

        }
        double coupon = this.getCouponDiscounts();
        double campaign = this.getCampaignDiscounts();
        System.out.println("Total Discount:" + (coupon + campaign) + " , Coupon: " + coupon + " Campaign: " + campaign
                + System.lineSeparator());
        double totalAmountAfterDiscounts = this.getTotalAmountAfterDiscounts();
        double totalAmount = totalAmountAfterDiscounts + coupon + campaign;
        double deliveryCost = this.getDeliveryCost();
        System.out.println("Total Amount: " + totalAmount + System.lineSeparator());
        System.out.println("Total Amount after discounts: " + totalAmountAfterDiscounts + System.lineSeparator());
        System.out.println("Delivery Cost : " + deliveryCost + System.lineSeparator());
        System.out.println("Cost with delivery: " + (totalAmountAfterDiscounts + deliveryCost) + System.lineSeparator());

    }

    public double getTotalAmountAfterDiscounts()
    {
        double totalPrice = this.getListOfProducts().stream().map(Product::getPrice).reduce(0.0, (a, b) -> a + b);
        return totalPrice - (this.campaignDiscount + this.couponDiscount);
    }

    public double getCouponDiscounts()
    {
        return this.couponDiscount;
    }

    public double getCampaignDiscounts()
    {
        return this.campaignDiscount;
    }

    public double getDeliveryCost()
    {
        return this.deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost)
    {
        this.deliveryCost = deliveryCost;
    }

    public void applyDiscounts(Campaign campaign1, Campaign campaign2, Campaign campaign3)
    {
        double discount1 = calculateDiscountForCampaign(campaign1);
        double discount2 = calculateDiscountForCampaign(campaign2);
        double discount3 = calculateDiscountForCampaign(campaign3);

        double semiProcessDiscount = discount1 > discount2 ? discount1 : discount2;
        double discountFinal = semiProcessDiscount > discount3 ? semiProcessDiscount : discount3;
        this.campaignDiscount = discountFinal;
    }

    public void applyCoupon(Coupon coupon)
    {
        double discount = 0;
        double totalPrice = this.getListOfProducts().stream().map(Product::getPrice).reduce(0.0, (a, b) -> a + b);
        double priceAfterCampaings = totalPrice - this.getCampaignDiscounts();
        if (priceAfterCampaings > coupon.getMinimumPurchaseLimit())
        {
            discount = priceAfterCampaings * coupon.getDiscountAmount();
            DiscountType discountType = coupon.getDiscountType();
            if (discountType.equals(DiscountType.Rate))
            {
                discount = priceAfterCampaings * (coupon.getDiscountAmount() / 100.0);
            }
        }
        this.couponDiscount = discount;
    }

    private double calculateDiscountForCampaign(Campaign campaign)
    {
        double discountAmount = 0;
        Category category = campaign.getCategory();
        List<Product> filteredList = this.getListOfProducts().stream()
                .filter(x -> category.getTitle().equals(x.getCategory().getTitle())).collect(Collectors.toList());

        if (campaign.getLimitRule() < filteredList.size())
        {
            DiscountType discountType = campaign.getDiscountType();
            if (discountType.equals(DiscountType.Amount))
            {
                discountAmount = filteredList.size() * campaign.getDiscount();
            }
            else if (discountType.equals(DiscountType.Rate))
            {
                double sumOfCategory = filteredList.stream().map(Product::getPrice).reduce(0.0, (a, b) -> a + b);
                discountAmount = sumOfCategory * (campaign.getDiscount() / 100);
            }
        }
        return discountAmount;
    }

}
