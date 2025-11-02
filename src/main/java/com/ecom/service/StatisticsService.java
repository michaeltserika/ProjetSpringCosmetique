package com.ecom.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    long getTotalOrders();

    long getTotalProducts();

    long getTotalUsers();

    long getTotalCategories();

    Map<String, Long> getOrdersByStatus();

    Map<String, Long> getProductsByCategory();

    List<Object[]> getOrdersTrendLast30Days();

    List<Object[]> getTopSellingProducts(int limit);

    List<String> getLast30DaysLabels();

    double getTotalRevenue();

    double getAverageOrderValue();
}