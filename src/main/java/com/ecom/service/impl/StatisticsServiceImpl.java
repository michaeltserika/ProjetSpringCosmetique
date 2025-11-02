package com.ecom.service.impl;

import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ProductOrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public long getTotalOrders() {
        long count = orderRepository.count();
        System.out.println("StatisticsServiceImpl.getTotalOrders(): " + count);
        return count;
    }

    @Override
    public long getTotalProducts() {
        long count = productRepository.count();
        System.out.println("StatisticsServiceImpl.getTotalProducts(): " + count);
        return count;
    }

    @Override
    public long getTotalUsers() {
        long count = userRepository.count();
        System.out.println("StatisticsServiceImpl.getTotalUsers(): " + count);
        return count;
    }

    @Override
    public long getTotalCategories() {
        long count = categoryRepository.count();
        System.out.println("StatisticsServiceImpl.getTotalCategories(): " + count);
        return count;
    }

    @Override
    public Map<String, Long> getOrdersByStatus() {
        Map<String, Long> statusMap = new HashMap<>();
        orderRepository.findAll().forEach(order ->
            statusMap.merge(order.getStatus(), 1L, Long::sum)
        );
        return statusMap;
    }

    @Override
    public Map<String, Long> getProductsByCategory() {
        Map<String, Long> categoryMap = new HashMap<>();
        productRepository.findAll().forEach(product -> {
            if (product.getCategory() != null && !product.getCategory().isEmpty()) {
                categoryMap.merge(product.getCategory(), 1L, Long::sum);
            }
        });
        return categoryMap;
    }

    @Override
    public List<Object[]> getOrdersTrendLast30Days() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        List<Object[]> results = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            final LocalDate currentDate = date;
            long count = orderRepository.findAll().stream()
                    .filter(order -> order.getOrderDate().equals(currentDate))
                    .count();
            results.add(new Object[]{date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), count});
        }
        return results;
    }

    @Override
    public List<Object[]> getTopSellingProducts(int limit) {
        // Group orders by product and count quantities
        Map<String, Long> productSales = new HashMap<>();
        orderRepository.findAll().forEach(order -> {
            String productName = order.getProduct().getTitle();
            Long quantity = order.getQuantity() != null ? order.getQuantity().longValue() : 0L;
            productSales.merge(productName, quantity, Long::sum);
        });

        // Sort by sales count and take top products
        return productSales.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .toList();
    }

    @Override
    public List<String> getLast30DaysLabels() {
        List<String> labels = new ArrayList<>();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            labels.add(date.format(DateTimeFormatter.ofPattern("dd/MM")));
        }
        return labels;
    }
}