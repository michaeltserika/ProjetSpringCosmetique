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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.microsoft.schemas.vml.CTShape.type;

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

        // Colors
        com.itextpdf.kernel.colors.Color primaryColor = com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234));

        document.add(new Paragraph("Catalogue des Produits - ShopFandresena").setTextAlignment(TextAlignment.CENTER).setFontSize(18).setFontColor(primaryColor));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 1, 1, 2}))
                .useAllAvailableWidth();

        // Header with background
        com.itextpdf.layout.element.Cell headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("ID").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Nom").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Catégorie").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Prix (€)").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Stock").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Statut").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        double totalValue = 0.0;
        int activeProducts = 0;
        for (Product product : products) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getTitle());
            table.addCell(product.getCategory() != null ? product.getCategory() : "N/A");
            table.addCell("€" + String.format("%.2f", product.getPrice() != null ? product.getPrice() : 0.0));
            table.addCell(String.valueOf(product.getStock()));
            table.addCell(product.getIsActive() != null && product.getIsActive() ? "Actif" : "Inactif");

            if (product.getIsActive() != null && product.getIsActive()) {
                activeProducts++;
                totalValue += (product.getPrice() != null ? product.getPrice() : 0.0) * product.getStock();
            }
        }

        document.add(table);

        // Add summary
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("RÉSUMÉ DU CATALOGUE").setFontSize(14).setBold().setFontColor(primaryColor));

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(50));
        summaryTable.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT);

        summaryTable.addCell("Nombre total de produits:");
        summaryTable.addCell(String.valueOf(products.size()));

        summaryTable.addCell("Produits actifs:");
        summaryTable.addCell(String.valueOf(activeProducts));

        summaryTable.addCell("Valeur totale du stock:");
        summaryTable.addCell("€" + String.format("%.2f", totalValue));

        document.add(summaryTable);

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
        headerRow.createCell(3).setCellValue("Prix (€)");
        headerRow.createCell(4).setCellValue("Stock");
        headerRow.createCell(5).setCellValue("Statut");

        int rowNum = 1;
        double totalValue = 0.0;
        int activeProducts = 0;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getTitle());
            row.createCell(2).setCellValue(product.getCategory() != null ? product.getCategory() : "N/A");
            row.createCell(3).setCellValue(product.getPrice() != null ? product.getPrice() : 0.0);
            row.createCell(4).setCellValue(product.getStock());
            row.createCell(5).setCellValue(product.getIsActive() != null && product.getIsActive() ? "Actif" : "Inactif");

            if (product.getIsActive() != null && product.getIsActive()) {
                activeProducts++;
                totalValue += (product.getPrice() != null ? product.getPrice() : 0.0) * product.getStock();
            }
        }

        // Add summary rows
        Row summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(1).setCellValue("TOTAL PRODUITS:");
        summaryRow.createCell(2).setCellValue(products.size());

        summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(1).setCellValue("PRODUITS ACTIFS:");
        summaryRow.createCell(2).setCellValue(activeProducts);

        summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(1).setCellValue("VALEUR TOTALE DU STOCK:");
        summaryRow.createCell(3).setCellValue(totalValue);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportProductsToCSV(List<Product> products, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nom,Catégorie,Prix (€),Stock,Statut");

        double totalValue = 0.0;
        int activeProducts = 0;
        for (Product product : products) {
            writer.println(product.getId() + "," +
                          product.getTitle() + "," +
                          (product.getCategory() != null ? product.getCategory() : "N/A") + "," +
                          (product.getPrice() != null ? product.getPrice() : 0.0) + "," +
                          product.getStock() + "," +
                          (product.getIsActive() != null && product.getIsActive() ? "Actif" : "Inactif"));

            if (product.getIsActive() != null && product.getIsActive()) {
                activeProducts++;
                totalValue += (product.getPrice() != null ? product.getPrice() : 0.0) * product.getStock();
            }
        }

        // Add summary
        writer.println();
        writer.println("RÉSUMÉ,,,");
        writer.println("Nombre total de produits:," + products.size() + ",,");
        writer.println("Produits actifs:," + activeProducts + ",,");
        writer.println("Valeur totale du stock:," + String.format("%.2f", totalValue) + " €,,");
    }

    @Override
    public void exportOrdersToPDF(List<ProductOrder> orders, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Colors
        com.itextpdf.kernel.colors.Color primaryColor = com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234));

        document.add(new Paragraph("Historique des Commandes - ShopFandresena").setTextAlignment(TextAlignment.CENTER).setFontSize(18).setFontColor(primaryColor));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2, 2}))
                .useAllAvailableWidth();

        // Header with background
        com.itextpdf.layout.element.Cell headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("ID").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Numéro").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Client").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Montant (€)").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Statut").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        headerCell = new com.itextpdf.layout.element.Cell();
        headerCell.add(new Paragraph("Date").setBold());
        headerCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        table.addHeaderCell(headerCell);

        double totalRevenue = 0.0;
        for (ProductOrder order : orders) {
            table.addCell(String.valueOf(order.getId()));
            table.addCell(order.getOrderId());
            table.addCell(order.getUser() != null ? order.getUser().getName() : "N/A");
            table.addCell("€" + String.format("%.2f", order.getPrice() != null ? order.getPrice() : 0.0));
            table.addCell(order.getStatus());
            table.addCell(order.getOrderDate() != null ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A");
            totalRevenue += order.getPrice() != null ? order.getPrice() : 0.0;
        }

        document.add(table);

        // Add total revenue summary
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("RÉSUMÉ").setFontSize(14).setBold().setFontColor(primaryColor));

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(50));
        summaryTable.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT);

        summaryTable.addCell("Nombre total de commandes:");
        summaryTable.addCell(String.valueOf(orders.size()));

        summaryTable.addCell("Chiffre d'affaires total:");
        summaryTable.addCell("€" + String.format("%.2f", totalRevenue));

        document.add(summaryTable);

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
        headerRow.createCell(3).setCellValue("Montant (€)");
        headerRow.createCell(4).setCellValue("Statut");
        headerRow.createCell(5).setCellValue("Date");

        int rowNum = 1;
        double totalRevenue = 0.0;
        for (ProductOrder order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getOrderId());
            row.createCell(2).setCellValue(order.getUser() != null ? order.getUser().getName() : "N/A");
            row.createCell(3).setCellValue(order.getPrice() != null ? order.getPrice() : 0.0);
            row.createCell(4).setCellValue(order.getStatus());
            row.createCell(5).setCellValue(order.getOrderDate() != null ? order.getOrderDate().toString() : "N/A");
            totalRevenue += order.getPrice() != null ? order.getPrice() : 0.0;
        }

        // Add summary row
        Row summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(2).setCellValue("TOTAL COMMANDES:");
        summaryRow.createCell(3).setCellValue(orders.size());

        summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(2).setCellValue("CHIFFRE D'AFFAIRES TOTAL:");
        summaryRow.createCell(3).setCellValue(totalRevenue);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void exportOrdersToCSV(List<ProductOrder> orders, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Numéro,Client,Montant (€),Statut,Date");

        double totalRevenue = 0.0;
        for (ProductOrder order : orders) {
            writer.println(order.getId() + "," +
                          order.getOrderId() + "," +
                          (order.getUser() != null ? order.getUser().getName() : "N/A") + "," +
                          (order.getPrice() != null ? order.getPrice() : 0.0) + "," +
                          order.getStatus() + "," +
                          (order.getOrderDate() != null ? order.getOrderDate().toString() : "N/A"));
            totalRevenue += order.getPrice() != null ? order.getPrice() : 0.0;
        }

        // Add summary
        writer.println();
        writer.println("RÉSUMÉ,,,");
        writer.println("Nombre total de commandes:," + orders.size() + ",,");
        writer.println("Chiffre d'affaires total:," + String.format("%.2f", totalRevenue) + " €,,");
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

    @Override
    public void generateInvoicePDF(ProductOrder order, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=facture-" + order.getOrderId() + ".pdf");

        generateInvoicePDFContent(order, response.getOutputStream());
    }

    @Override
    public byte[] generateInvoicePDFBytes(ProductOrder order) throws Exception {
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        generateInvoicePDFContent(order, outputStream);
        return outputStream.toByteArray();
    }

    private void generateInvoicePDFContent(ProductOrder order, java.io.OutputStream outputStream) throws Exception {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Colors
        com.itextpdf.kernel.colors.Color primaryColor = com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234));
        com.itextpdf.kernel.colors.Color secondaryColor = com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(108, 117, 125));

        // Header
        com.itextpdf.layout.element.Table headerTable = new com.itextpdf.layout.element.Table(new float[]{1, 1});
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Company info
        com.itextpdf.layout.element.Cell companyCell = new com.itextpdf.layout.element.Cell();
        companyCell.add(new com.itextpdf.layout.element.Paragraph("ShopFandresena")
                .setFontSize(24)
                .setFontColor(primaryColor)
                .setBold());
        companyCell.add(new com.itextpdf.layout.element.Paragraph("✨ Votre beauté, notre passion ✨")
                .setFontSize(12)
                .setFontColor(secondaryColor));
        companyCell.add(new com.itextpdf.layout.element.Paragraph("Email: contact@shopfandresena.com")
                .setFontSize(10));
        companyCell.add(new com.itextpdf.layout.element.Paragraph("Tél: +261 XX XX XXX XX")
                .setFontSize(10));
        companyCell.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);

        // Invoice info
        com.itextpdf.layout.element.Cell invoiceCell = new com.itextpdf.layout.element.Cell();
        invoiceCell.add(new com.itextpdf.layout.element.Paragraph("FACTURE")
                .setFontSize(20)
                .setFontColor(primaryColor)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT));
        invoiceCell.add(new com.itextpdf.layout.element.Paragraph("N° " + order.getOrderId())
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT));
        invoiceCell.add(new com.itextpdf.layout.element.Paragraph("Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT));
        invoiceCell.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);

        headerTable.addCell(companyCell);
        headerTable.addCell(invoiceCell);
        document.add(headerTable);

        document.add(new com.itextpdf.layout.element.Paragraph("\n"));

        // Customer info
        document.add(new com.itextpdf.layout.element.Paragraph("Informations client")
                .setFontSize(14)
                .setFontColor(primaryColor)
                .setBold());

        com.itextpdf.layout.element.Table customerTable = new com.itextpdf.layout.element.Table(new float[]{1, 2});
        customerTable.setWidth(UnitValue.createPercentValue(100));
        customerTable.setMarginBottom(20);

        customerTable.addCell(createCell("Nom:", order.getOrderAddress().getFirstName() + " " + order.getOrderAddress().getLastName(), false));
        customerTable.addCell(createCell("Email:", order.getOrderAddress().getEmail(), false));
        customerTable.addCell(createCell("Téléphone:", order.getOrderAddress().getMobileNo(), false));
        customerTable.addCell(createCell("Adresse:", order.getOrderAddress().getAddress() + ", " + order.getOrderAddress().getCity() + ", " + order.getOrderAddress().getState(), false));

        document.add(customerTable);

        // Products table
        document.add(new com.itextpdf.layout.element.Paragraph("Détails de la commande")
                .setFontSize(14)
                .setFontColor(primaryColor)
                .setBold());

        com.itextpdf.layout.element.Table productTable = new com.itextpdf.layout.element.Table(new float[]{3, 1, 1, 1});
        productTable.setWidth(UnitValue.createPercentValue(100));
        productTable.setMarginBottom(20);

        // Header
        productTable.addHeaderCell(createHeaderCell("Produit"));
        productTable.addHeaderCell(createHeaderCell("Quantité"));
        productTable.addHeaderCell(createHeaderCell("Prix unitaire"));
        productTable.addHeaderCell(createHeaderCell("Total"));

        // Product row
        productTable.addCell(createCell(order.getProduct().getTitle(), "", false));
        productTable.addCell(createCell(String.valueOf(order.getQuantity()), "", false));
        productTable.addCell(createCell("€" + String.format("%.2f", order.getPrice() / order.getQuantity()), "", false));
        productTable.addCell(createCell("€" + String.format("%.2f", order.getPrice()), "", false));

        document.add(productTable);

        // Totals
        com.itextpdf.layout.element.Table totalTable = new com.itextpdf.layout.element.Table(new float[]{3, 2});
        totalTable.setWidth(UnitValue.createPercentValue(50));
        totalTable.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT);

        // Subtotal
        totalTable.addCell(createCell("Sous-total:", "€" + String.format("%.2f", order.getPrice()), false));
        totalTable.addCell(createCell("Frais de livraison:", "€2.50", false));
        totalTable.addCell(createCell("Taxe:", "€1.00", false));

        // Total
        com.itextpdf.layout.element.Cell totalCell = new com.itextpdf.layout.element.Cell();
        totalCell.add(new com.itextpdf.layout.element.Paragraph("TOTAL: €" + String.format("%.2f", order.getPrice() + 3.50))
                .setBold()
                .setFontSize(14)
                .setFontColor(primaryColor));
        totalCell.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
        totalCell.setPadding(10);
        totalCell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));

        com.itextpdf.layout.element.Cell emptyCell = new com.itextpdf.layout.element.Cell();
        emptyCell.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
        totalTable.addCell(emptyCell);
        totalTable.addCell(totalCell);

        document.add(totalTable);

        // Payment info
        document.add(new com.itextpdf.layout.element.Paragraph("\n"));
        document.add(new com.itextpdf.layout.element.Paragraph("Informations de paiement")
                .setFontSize(12)
                .setBold());
        document.add(new com.itextpdf.layout.element.Paragraph("Mode de paiement: " + order.getPaymentType()));
        document.add(new com.itextpdf.layout.element.Paragraph("Statut: " + order.getStatus()));

        // Footer
        document.add(new com.itextpdf.layout.element.Paragraph("\n\n"));
        com.itextpdf.layout.element.Paragraph footer = new com.itextpdf.layout.element.Paragraph("Merci pour votre confiance ! ShopFandresena - © 2024 Tous droits réservés")
                .setFontSize(10)
                .setFontColor(secondaryColor)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(footer);

        document.close();
    }

    private com.itextpdf.layout.element.Cell createCell(String label, String value, boolean isHeader) {
        com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell();
        if (isHeader) {
            cell.add(new com.itextpdf.layout.element.Paragraph(label).setBold());
            cell.setBackgroundColor(com.itextpdf.kernel.colors.Color.convertRgbToCmyk(new com.itextpdf.kernel.colors.DeviceRgb(248, 249, 250)));
        } else {
            if (label.equals("Sous-total:") || label.equals("Frais de livraison:") || label.equals("Taxe:")) {
                cell.add(new com.itextpdf.layout.element.Paragraph(label));
                cell.add(new com.itextpdf.layout.element.Paragraph(value).setTextAlignment(TextAlignment.RIGHT));
            } else {
                cell.add(new com.itextpdf.layout.element.Paragraph(label + " " + value));
            }
        }
        cell.setPadding(5);
        return cell;
    }

    private com.itextpdf.layout.element.Cell createHeaderCell(String text) {
        return createCell(text, "", true);
    }
}