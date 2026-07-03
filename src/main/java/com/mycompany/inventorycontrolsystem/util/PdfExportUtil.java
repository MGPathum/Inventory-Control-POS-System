package com.mycompany.inventorycontrolsystem.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Utility class for generating styled PDF reports using Apache PDFBox.
 *
 * <p>Produces a real binary PDF file (starts with {@code %PDF-}) that any
 * PDF reader can open.  No external template files are required.
 *
 * <p>Usage:
 * <pre>
 *   PdfExportUtil.exportTable(
 *       "Invoice Report",
 *       new String[]{"Invoice ID", "Total", "Status"},
 *       rows,           // List of Object[]
 *       "/path/out.pdf",
 *       "Admin User"
 *   );
 * </pre>
 */
public final class PdfExportUtil {

    // ── Colours ──────────────────────────────────────────────────────────────
    private static final Color HEADER_BG    = new Color(20,  30,  54);   // dark navy
    private static final Color HEADER_FG    = Color.WHITE;
    private static final Color ALT_ROW_BG   = new Color(245, 247, 250);  // light grey
    private static final Color BORDER_COLOR = new Color(200, 210, 220);
    private static final Color TITLE_COLOR  = new Color(20,  30,  54);

    // ── Layout constants (points — 1 pt = 1/72 inch) ─────────────────────────
    private static final float MARGIN        = 40f;
    private static final float HEADER_H      = 24f;   // column header row height
    private static final float ROW_H         = 18f;   // data row height
    private static final float TITLE_SIZE    = 16f;
    private static final float SUBTITLE_SIZE = 9f;
    private static final float HEADER_FONT_SIZE = 9f;
    private static final float DATA_FONT_SIZE   = 8f;

    private PdfExportUtil() {}

    /**
     * Exports tabular data to a PDF file with a styled header and alternating
     * row colours.  Handles multi-page output automatically.
     *
     * @param reportTitle  text shown at the top of every page
     * @param columnNames  column header labels
     * @param rows         data rows (each element converted via {@code toString()})
     * @param outputPath   absolute path for the output {@code .pdf} file
     * @param generatedBy  user name printed in the subtitle line
     * @throws IOException if the file cannot be written
     */
    public static void exportTable(String reportTitle,
                                   String[] columnNames,
                                   List<Object[]> rows,
                                   String outputPath,
                                   String generatedBy) throws IOException {

        PDType1Font fontBold    = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

        try (PDDocument doc = new PDDocument()) {

            // ── Compute column widths (equal split of usable width) ───────────
            PDPage firstPage = new PDPage(PDRectangle.A4);
            float pageW  = firstPage.getMediaBox().getWidth();
            float pageH  = firstPage.getMediaBox().getHeight();
            float usableW = pageW - 2 * MARGIN;
            float colW    = usableW / columnNames.length;

            // ── Pagination state ─────────────────────────────────────────────
            PDPage      page    = null;
            PDPageContentStream cs = null;
            float y = 0;

            String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm").format(new Date());

            int totalPages = estimatePageCount(rows.size(), pageH);
            int pageNum    = 0;
            int rowIdx     = 0;

            while (rowIdx <= rows.size()) {   // <= so we always create at least one page

                // ── Start a new page ─────────────────────────────────────────
                if (cs != null) cs.close();
                page = new PDPage(PDRectangle.A4);
                doc.addPage(page);
                cs = new PDPageContentStream(doc, page);
                pageNum++;

                y = pageH - MARGIN;

                // ── Title block ───────────────────────────────────────────────
                y = drawTitle(cs, fontBold, fontRegular,
                              reportTitle, generatedBy, timestamp,
                              pageNum, totalPages,
                              pageW, y);

                // ── Column header row ─────────────────────────────────────────
                y = drawColumnHeaders(cs, fontBold, columnNames,
                                      MARGIN, y, colW, HEADER_H);

                // ── Data rows until the page is full ─────────────────────────
                while (rowIdx < rows.size()) {
                    // Reserve space for at least one more row + footer
                    if (y - ROW_H < MARGIN + 20) break;

                    Object[] row     = rows.get(rowIdx);
                    boolean  isEven  = (rowIdx % 2 == 0);
                    y = drawDataRow(cs, fontRegular, row,
                                    MARGIN, y, colW, ROW_H,
                                    isEven ? Color.WHITE : ALT_ROW_BG);
                    rowIdx++;
                }

                // ── Footer line ───────────────────────────────────────────────
                drawFooter(cs, fontRegular, pageW, pageNum, totalPages);

                // If we've drawn all rows, close and break
                if (rowIdx >= rows.size()) {
                    cs.close();
                    cs = null;
                    break;
                }
            }

            if (cs != null) cs.close();
            doc.save(outputPath);
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private static float drawTitle(PDPageContentStream cs,
                                   PDType1Font fontBold, PDType1Font fontRegular,
                                   String title, String generatedBy,
                                   String timestamp, int pageNum, int totalPages,
                                   float pageW, float y) throws IOException {

        // App name micro-label
        cs.beginText();
        cs.setFont(fontRegular, 7f);
        cs.setNonStrokingColor(new Color(120, 130, 150));
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("INVENTORY CONTROL SYSTEM");
        cs.endText();
        y -= 14f;

        // Report title
        cs.beginText();
        cs.setFont(fontBold, TITLE_SIZE);
        cs.setNonStrokingColor(TITLE_COLOR);
        cs.newLineAtOffset(MARGIN, y);
        cs.showText(title);
        cs.endText();
        y -= 16f;

        // Subtitle — generated by / date
        cs.beginText();
        cs.setFont(fontRegular, SUBTITLE_SIZE);
        cs.setNonStrokingColor(new Color(100, 110, 130));
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("Generated by: " + generatedBy + "   |   " + timestamp
                    + "   |   Page " + pageNum + " of " + totalPages);
        cs.endText();
        y -= 6f;

        // Divider rule
        cs.setStrokingColor(TITLE_COLOR);
        cs.setLineWidth(1.2f);
        cs.moveTo(MARGIN, y);
        cs.lineTo(pageW - MARGIN, y);
        cs.stroke();
        y -= 8f;

        return y;
    }

    private static float drawColumnHeaders(PDPageContentStream cs,
                                           PDType1Font font,
                                           String[] cols,
                                           float x, float y,
                                           float colW, float rowH) throws IOException {
        // Fill background
        cs.setNonStrokingColor(HEADER_BG);
        cs.addRect(x, y - rowH, colW * cols.length, rowH);
        cs.fill();

        // Text
        cs.setNonStrokingColor(HEADER_FG);
        cs.setFont(font, HEADER_FONT_SIZE);
        for (int i = 0; i < cols.length; i++) {
            cs.beginText();
            cs.newLineAtOffset(x + i * colW + 4f, y - rowH + 6f);
            cs.showText(truncate(cols[i], colW - 6f, font, HEADER_FONT_SIZE));
            cs.endText();
        }

        // Bottom border
        cs.setStrokingColor(BORDER_COLOR);
        cs.setLineWidth(0.4f);
        cs.moveTo(x, y - rowH);
        cs.lineTo(x + colW * cols.length, y - rowH);
        cs.stroke();

        return y - rowH;
    }

    private static float drawDataRow(PDPageContentStream cs,
                                     PDType1Font font,
                                     Object[] row,
                                     float x, float y,
                                     float colW, float rowH,
                                     Color bgColor) throws IOException {
        // Row background
        cs.setNonStrokingColor(bgColor);
        cs.addRect(x, y - rowH, colW * row.length, rowH);
        cs.fill();

        // Cell text
        cs.setNonStrokingColor(Color.BLACK);
        cs.setFont(font, DATA_FONT_SIZE);
        for (int i = 0; i < row.length; i++) {
            String text = row[i] != null ? row[i].toString() : "";
            cs.beginText();
            cs.newLineAtOffset(x + i * colW + 4f, y - rowH + 5f);
            cs.showText(truncate(text, colW - 6f, font, DATA_FONT_SIZE));
            cs.endText();
        }

        // Bottom border
        cs.setStrokingColor(BORDER_COLOR);
        cs.setLineWidth(0.3f);
        cs.moveTo(x, y - rowH);
        cs.lineTo(x + colW * row.length, y - rowH);
        cs.stroke();

        return y - rowH;
    }

    private static void drawFooter(PDPageContentStream cs,
                                   PDType1Font font,
                                   float pageW,
                                   int pageNum, int totalPages) throws IOException {
        float y = 28f;
        cs.setStrokingColor(new Color(180, 190, 200));
        cs.setLineWidth(0.5f);
        cs.moveTo(MARGIN, y + 10f);
        cs.lineTo(pageW - MARGIN, y + 10f);
        cs.stroke();

        cs.beginText();
        cs.setFont(font, 7f);
        cs.setNonStrokingColor(new Color(130, 140, 155));
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("Inventory Control System — Confidential");
        cs.endText();

        String pageStr = "Page " + pageNum + " / " + totalPages;
        float tw = getTextWidth(pageStr, font, 7f);
        cs.beginText();
        cs.setFont(font, 7f);
        cs.setNonStrokingColor(new Color(130, 140, 155));
        cs.newLineAtOffset(pageW - MARGIN - tw, y);
        cs.showText(pageStr);
        cs.endText();
    }

    /** Truncates {@code text} so it fits within {@code maxWidth} points. */
    private static String truncate(String text, float maxWidth,
                                   PDType1Font font, float fontSize) {
        try {
            while (text.length() > 1 &&
                   font.getStringWidth(text) / 1000f * fontSize > maxWidth) {
                text = text.substring(0, text.length() - 1);
            }
        } catch (IOException ignored) { /* use as-is */ }
        return text;
    }

    private static float getTextWidth(String text, PDType1Font font, float fontSize) {
        try {
            return font.getStringWidth(text) / 1000f * fontSize;
        } catch (IOException e) {
            return 0f;
        }
    }

    /** Rough page-count estimate based on rows per page. */
    private static int estimatePageCount(int rowCount, float pageH) {
        float titleH    = 60f;
        float colHdrH   = HEADER_H;
        float footerH   = 40f;
        float available = pageH - MARGIN * 2 - titleH - colHdrH - footerH;
        int   rowsPerPage = Math.max(1, (int) (available / ROW_H));
        return Math.max(1, (int) Math.ceil((double) rowCount / rowsPerPage));
    }
}
