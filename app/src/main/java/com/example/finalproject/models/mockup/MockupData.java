package com.example.finalproject.models.mockup;

import java.util.ArrayList;
import java.util.Arrays;

public class MockupData {
    private static final MockItem[] ITEMS_ARRAY = {
            new MockItem(1, "Head & Shoulders", "Best Anti Dandruff Itchy scalp product",
                    25.2, 100),
            new MockItem(2, "Lorem ipsum", "suscipit ornare metus justo vitae tellus",
                    30.8, 100),
            new MockItem(3, "dolor sit amet", "non feugiat turpis ante non nisl",
                    21.2, 100),
            new MockItem(4, "Sed a diam", "Suspendisse elit purus, imperdiet eget efficitur et",
                    50.6, 100),
            new MockItem(5, "Curabitur tempor", "Class aptent taciti sociosqu ad litora torquent",
                    19.9, 100),
    };

    private static final MockCategory[] CATEGORIES_ARRAY = {
            new MockCategory(1, "Prescription Medications"),
            new MockCategory(2, "Over-the-Counter Medications"),
            new MockCategory(3, "Personal Care Products"),
            new MockCategory(4, "Vitamins and Supplements"),
            new MockCategory(5, "First Aid Supplies"),
            new MockCategory(6, "Baby Care Products"),
            new MockCategory(7, "Allergy Relief"),

    };

    private static final ArrayList<MockItem> MOCK_ITEM_ARRAY_LIST = new ArrayList<>(Arrays.asList(ITEMS_ARRAY));
    private static final ArrayList<MockCategory> MOCK_CATEGORY_ARRAY_LIST = new ArrayList<>(Arrays.asList(CATEGORIES_ARRAY));

    public static ArrayList<MockItem> getItemArrayList() {
        return MOCK_ITEM_ARRAY_LIST;
    }

    public static ArrayList<MockCategory> getCategoryArrayList() {
        return MOCK_CATEGORY_ARRAY_LIST;
    }

}
