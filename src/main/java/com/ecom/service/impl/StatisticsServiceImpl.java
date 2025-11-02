package com.ecom.service.impl;

import com.ecom.model.ProductOrder;
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
        List<String> validStatuses = Arrays.asList("En attente", "En cours", "Livré", "Annulé");

        orderRepository.findAll().forEach(order -> {
            String status = order.getStatus();
            if (status != null && !status.trim().isEmpty()) {
                // Normalize status to match expected values
                if (status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("en attente")) {
                    statusMap.merge("En attente", 1L, Long::sum);
                } else if (status.equalsIgnoreCase("processing") || status.equalsIgnoreCase("en cours")) {
                    statusMap.merge("En cours", 1L, Long::sum);
                } else if (status.equalsIgnoreCase("delivered") || status.equalsIgnoreCase("livré")) {
                    statusMap.merge("Livré", 1L, Long::sum);
                } else if (status.equalsIgnoreCase("cancelled") || status.equalsIgnoreCase("annulé")) {
                    statusMap.merge("Annulé", 1L, Long::sum);
                } else {
                    statusMap.merge(status, 1L, Long::sum);
                }
            }
        });

        // Ensure all expected statuses are present with at least 0
        for (String validStatus : validStatuses) {
            statusMap.putIfAbsent(validStatus, 0L);
        }

        return statusMap;
    }

    @Override
    public Map<String, Long> getProductsByCategory() {
        Map<String, Long> categoryMap = new HashMap<>();
        productRepository.findAll().forEach(product -> {
            String category = product.getCategory();
            if (category != null && !category.trim().isEmpty()) {
                categoryMap.merge(category, 1L, Long::sum);
            } else {
                categoryMap.merge("Sans catégorie", 1L, Long::sum);
            }
        });

        // If no categories found, add a default entry
        if (categoryMap.isEmpty()) {
            categoryMap.put("Aucune catégorie", 0L);
        }

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
                    .filter(order -> order.getOrderDate() != null && order.getOrderDate().equals(currentDate))
                    .count();
            results.add(new Object[]{date.format(DateTimeFormatter.ofPattern("dd/MM")), count});
        }
        return results;
    }

    @Override
    public List<Object[]> getTopSellingProducts(int limit) {
        // Group orders by product and count quantities
        Map<String, Long> productSales = new HashMap<>();
        orderRepository.findAll().forEach(order -> {
            if (order.getProduct() != null && order.getProduct().getTitle() != null) {
                String productName = order.getProduct().getTitle();
                Long quantity = order.getQuantity() != null ? order.getQuantity().longValue() : 0L;
                productSales.merge(productName, quantity, Long::sum);
            }
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

    @Override
    public double getTotalRevenue() {
        return orderRepository.findAll().stream()
                .mapToDouble(order -> order.getPrice() != null ? order.getPrice() : 0.0)
                .sum();
    }

    @Override
    public double getAverageOrderValue() {
        List<ProductOrder> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return 0.0;
        }
        return orders.stream()
                .mapToDouble(order -> order.getPrice() != null ? order.getPrice() : 0.0)
                .average()
                .orElse(0.0);
    }
}