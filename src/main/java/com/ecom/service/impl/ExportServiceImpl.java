package com.ecom.service.impl;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.ExportService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    @Override
    public void exportUsersToPDF(List<UserDtls> users, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=users.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Liste des Utilisateurs").setTextAlignment(TextAlignment.CENTER).setFontSize(18));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2}))
                .useAllAvailableWidth();

        table.addHeaderCell("ID");
        table.addHeaderCell("Nom");
        table.addHeaderCell("Email");
        table.addHeaderCell("Rôle");
        table.addHeaderCell("Statut");

        for (UserDtls user : users) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getName());
            table.addCell(user.getEmail());
            table.addCell(user.getRole());
            table.addCell(user.getAccountNonLocked() ? "Actif" : "Bloqué");
        }

        document.add(table);
        document.close();
    }

    @Override
    public void exportUsersToExcel(List<UserDtls> users, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Utilisateurs");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Rôle");
        headerRow.createCell(4).setCellValue("Statut");

        int rowNum = 1;
        for (UserDtls user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getRole());
            row.createCell(4).setCellValue(user.getAccountNonLocked() ? "Actif" : "Bloqué");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportUsersToCSV(List<UserDtls> users, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=users.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nom,Email,Rôle,Statut");

        for (UserDtls user : users) {
            writer.println(user.getId() + "," +
                         user.getName() + "," +
                         user.getEmail() + "," +
                         user.getRole() + "," +
                         (user.getAccountNonLocked() ? "Actif" : "Bloqué"));
        }
    }

    @Override
    public void exportProductsToPDF(List<Product> products, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=products.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Catalogue des Produits").setTextAlignment(TextAlignment.CENTER).setFontSize(18));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 1, 1, 2}))
                .useAllAvailableWidth();

        table.addHeaderCell("ID");
        table.addHeaderCell("Nom");
        table.addHeaderCell("Catégorie");
        table.addHeaderCell("Prix");
        table.addHeaderCell("Stock");
        table.addHeaderCell("Statut");

        for (Product product : products) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getTitle());
            table.addCell(product.getCategory());
            table.addCell(String.valueOf(product.getPrice()));
            table.addCell(String.valueOf(product.getStock()));
            table.addCell(product.getIsActive() ? "Actif" : "Inactif");
        }

        document.add(table);
        document.close();
    }

    @Override
    public void exportProductsToExcel(List<Product> products, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produits");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Catégorie");
        headerRow.createCell(3).setCellValue("Prix");
        headerRow.createCell(4).setCellValue("Stock");
        headerRow.createCell(5).setCellValue("Statut");

        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getTitle());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getPrice());
            row.createCell(4).setCellValue(product.getStock());
            row.createCell(5).setCellValue(product.getIsActive() ? "Actif" : "Inactif");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportProductsToCSV(List<Product> products, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nom,Catégorie,Prix,Stock,Statut");

        for (Product product : products) {
            writer.println(product.getId() + "," +
                         product.getTitle() + "," +
                         product.getCategory() + "," +
                         product.getPrice() + "," +
                         product.getStock() + "," +
                         (product.getIsActive() ? "Actif" : "Inactif"));
        }
    }

    @Override
    public void exportOrdersToPDF(List<ProductOrder> orders, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Historique des Commandes").setTextAlignment(TextAlignment.CENTER).setFontSize(18));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2, 2}))
                .useAllAvailableWidth();

        table.addHeaderCell("ID");
        table.addHeaderCell("Numéro");
        table.addHeaderCell("Client");
        table.addHeaderCell("Montant");
        table.addHeaderCell("Statut");
        table.addHeaderCell("Date");

        for (ProductOrder order : orders) {
            table.addCell(String.valueOf(order.getId()));
            table.addCell(order.getOrderId());
            table.addCell(order.getUser().getName());
            table.addCell(String.valueOf(order.getPrice()));
            table.addCell(order.getStatus());
            table.addCell(order.getOrderDate().toString());
        }

        document.add(table);
        document.close();
    }

    @Override
    public void exportOrdersToExcel(List<ProductOrder> orders, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Commandes");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Numéro");
        headerRow.createCell(2).setCellValue("Client");
        headerRow.createCell(3).setCellValue("Montant");
        headerRow.createCell(4).setCellValue("Statut");
        headerRow.createCell(5).setCellValue("Date");

        int rowNum = 1;
        for (ProductOrder order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getOrderId());
            row.createCell(2).setCellValue(order.getUser().getName());
            row.createCell(3).setCellValue(order.getPrice());
            row.createCell(4).setCellValue(order.getStatus());
            row.createCell(5).setCellValue(order.getOrderDate().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportOrdersToCSV(List<ProductOrder> orders, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Numéro,Client,Montant,Statut,Date");

        for (ProductOrder order : orders) {
            writer.println(order.getId() + "," +
                         order.getOrderId() + "," +
                         order.getUser().getName() + "," +
                         order.getPrice() + "," +
                         order.getStatus() + "," +
                         order.getOrderDate().toString());
        }
    }

    @Override
    public void exportCategoriesToPDF(List<Category> categories, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=categories.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Liste des Catégories").setTextAlignment(TextAlignment.CENTER).setFontSize(18));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 3}))
                .useAllAvailableWidth();

        table.addHeaderCell("ID");
        table.addHeaderCell("Nom");
        table.addHeaderCell("Statut");

        for (Category category : categories) {
            table.addCell(String.valueOf(category.getId()));
            table.addCell(category.getName());
            table.addCell(category.getIsActive() ? "Actif" : "Inactif");
        }

        document.add(table);
        document.close();
    }

    @Override
    public void exportCategoriesToExcel(List<Category> categories, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=categories.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Catégories");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Statut");

        int rowNum = 1;
        for (Category category : categories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getId());
            row.createCell(1).setCellValue(category.getName());
            row.createCell(2).setCellValue(category.getIsActive() ? "Actif" : "Inactif");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportCategoriesToCSV(List<Category> categories, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=categories.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nom,Statut");

        for (Category category : categories) {
            writer.println(category.getId() + "," +
                         category.getName() + "," +
                         (category.getIsActive() ? "Actif" : "Inactif"));
        }
    }
}