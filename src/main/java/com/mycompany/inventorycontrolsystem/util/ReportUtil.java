package com.mycompany.inventorycontrolsystem.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Utility class for loading, filling, and exporting Jasper Reports.
 * Report templates (.jrxml) live in src/main/resources/reports/.
 */
public final class ReportUtil {

    
    /** Classpath folder where compiled .jasper / .jrxml files are stored. */
    private static final String REPORT_BASE = "/reports/";

    private ReportUtil() {}

    /**
     * Loads and fills a report from the classpath, then opens a Jasper
     * print preview window.
     *
     * @param reportName  filename without extension, e.g. "stock_report"
     * @param params      report parameters map (can be empty, not null)
     * @param connection  live JDBC connection for the report data source
     */
    public static void previewReport(String reportName,
                                     Map<String, Object> params,
                                     Connection connection) {
        try {
            JasperPrint print = fillReport(reportName, params, connection);
            if (print != null) {
                net.sf.jasperreports.swing.JRViewer viewer =
                    new net.sf.jasperreports.swing.JRViewer(print);
                JFrame frame = new JFrame("Report – " + reportName);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(viewer);
                frame.setSize(900, 650);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("Failed to preview report: {}");
            JOptionPane.showMessageDialog(null,
                "Error generating report:\n" + e.getMessage(),
                "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exports a report to PDF and saves it to the chosen file path.
     */
    public static void exportToPdf(String reportName,
                                   Map<String, Object> params,
                                   Connection connection,
                                   String outputPath) {
        try {
            JasperPrint print = fillReport(reportName, params, connection);
            if (print == null) return;

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
            exporter.exportReport();
            System.out.println("PDF report exported: {}");
            JOptionPane.showMessageDialog(null,
                "PDF saved to:\n" + outputPath, "Export Success",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("PDF export failed for report: {}");
            JOptionPane.showMessageDialog(null,
                "PDF export failed:\n" + e.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exports a report to Excel (.xlsx) format.
     */
    public static void exportToExcel(String reportName,
                                     Map<String, Object> params,
                                     Connection connection,
                                     String outputPath) {
        try {
            JasperPrint print = fillReport(reportName, params, connection);
            if (print == null) return;

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
            SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
            config.setOnePagePerSheet(false);
            config.setRemoveEmptySpaceBetweenRows(true);
            exporter.setConfiguration(config);
            exporter.exportReport();
            System.out.println("Excel report exported: {}");
            JOptionPane.showMessageDialog(null,
                "Excel saved to:\n" + outputPath, "Export Success",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Excel export failed for report: {}");
            JOptionPane.showMessageDialog(null,
                "Excel export failed:\n" + e.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JasperPrint fillReport(String reportName,
                                          Map<String, Object> params,
                                          Connection connection)
            throws JRException {
        // Try compiled .jasper first, fall back to .jrxml
        String jasperPath = REPORT_BASE + reportName + ".jasper";
        var stream = ReportUtil.class.getResourceAsStream(jasperPath);

        JasperReport jasperReport;
        if (stream != null) {
            jasperReport = (JasperReport) JRLoader.loadObject(stream);
        } else {
            String jrxmlPath = REPORT_BASE + reportName + ".jrxml";
            var jrxmlStream = ReportUtil.class.getResourceAsStream(jrxmlPath);
            if (jrxmlStream == null) {
                JOptionPane.showMessageDialog(null,
                    "Report template not found: " + reportName,
                    "Missing Template", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        }

        return JasperFillManager.fillReport(jasperReport, params, connection);
    }
}
