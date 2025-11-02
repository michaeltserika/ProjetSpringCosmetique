package com.ecom.service;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportService {

    void exportUsersToPDF(List<UserDtls> users, HttpServletResponse response) throws Exception;

    void exportUsersToExcel(List<UserDtls> users, HttpServletResponse response) throws Exception;

    void exportUsersToCSV(List<UserDtls> users, HttpServletResponse response) throws Exception;

    void exportProductsToPDF(List<Product> products, HttpServletResponse response) throws Exception;

    void exportProductsToExcel(List<Product> products, HttpServletResponse response) throws Exception;

    void exportProductsToCSV(List<Product> products, HttpServletResponse response) throws Exception;

    void exportOrdersToPDF(List<ProductOrder> orders, HttpServletResponse response) throws Exception;

    void exportOrdersToExcel(List<ProductOrder> orders, HttpServletResponse response) throws Exception;

    void exportOrdersToCSV(List<ProductOrder> orders, HttpServletResponse response) throws Exception;

    void exportCategoriesToPDF(List<Category> categories, HttpServletResponse response) throws Exception;

    void exportCategoriesToExcel(List<Category> categories, HttpServletResponse response) throws Exception;

    void exportCategoriesToCSV(List<Category> categories, HttpServletResponse response) throws Exception;

    // Invoice generation methods
    void generateInvoicePDF(ProductOrder order, HttpServletResponse response) throws Exception;

    byte[] generateInvoicePDFBytes(ProductOrder order) throws Exception;
}