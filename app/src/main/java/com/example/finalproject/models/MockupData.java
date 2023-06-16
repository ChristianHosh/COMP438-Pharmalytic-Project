package com.example.finalproject.models;

import java.util.ArrayList;
import java.util.Arrays;

public class MockupData {
    private static final Product[] productsArray = {
            new Product(1, "Head & Shoulders", "Best Anti Dandruff Itchy scalp product",
                    25.2, 100),
            new Product(2, "Lorem ipsum", "suscipit ornare metus justo vitae tellus",
                    30.8, 100),
            new Product(3, "dolor sit amet", "non feugiat turpis ante non nisl",
                    21.2, 100),
            new Product(4, "Sed a diam", "Suspendisse elit purus, imperdiet eget efficitur et",
                    50.6, 100),
            new Product(5, "Curabitur tempor", "Class aptent taciti sociosqu ad litora torquent",
                    19.9, 100),
    };

    private static final Category[] categoriesArray = {
            new Category(1, "Prescription Medications"),
            new Category(2, "Over-the-Counter Medications"),
            new Category(3, "Personal Care Products"),
            new Category(4, "Vitamins and Supplements"),
            new Category(5, "First Aid Supplies"),
            new Category(6, "Baby Care Products"),
            new Category(7, "Allergy Relief"),

    };

    private static final ArrayList<Product> productsList = new ArrayList<>(Arrays.asList(productsArray));
    private static final ArrayList<Category> categoriesList = new ArrayList<>(Arrays.asList(categoriesArray));

    public static ArrayList<Product> getProductsList() {
        return productsList;
    }

    public static ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }

}
