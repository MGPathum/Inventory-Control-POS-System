package com.mycompany.inventorycontrolsystem.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utility class to generate barcodes using the ZXing library.
 * Supports CODE_128 (products) and QR_CODE formats.
 */
public final class BarcodeUtil {

    
    private static final int DEFAULT_WIDTH  = 250;
    private static final int DEFAULT_HEIGHT = 80;

    private BarcodeUtil() {}

    /**
     * Generates a CODE_128 barcode as a {@link BufferedImage}.
     *
     * @param content the text/number to encode (e.g. product code)
     * @param width   image width in pixels
     * @param height  image height in pixels
     * @return rendered barcode image
     */
    public static BufferedImage generateBarcode128(String content, int width, int height) {
        try {
            com.google.zxing.MultiFormatWriter writer = new com.google.zxing.MultiFormatWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.CODE_128, width, height);
            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (WriterException e) {
            System.out.println("Failed to generate CODE_128 barcode for content: {}");
            return createErrorImage(width, height);
        }
    }

    /**
     * Generates a QR code as a {@link BufferedImage}.
     *
     * @param content any string content
     * @param size    width = height for QR codes
     * @return rendered QR image
     */
    public static BufferedImage generateQRCode(String content, int size) {
        try {
            com.google.zxing.MultiFormatWriter writer = new com.google.zxing.MultiFormatWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size);
            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (WriterException e) {
            System.out.println("Failed to generate QR code for content: {}");
            return createErrorImage(size, size);
        }
    }

    /**
     * Convenience method: generate a CODE_128 barcode and wrap it in a
     * scaled {@link ImageIcon} ready to set on a {@link JLabel}.
     *
     * @param content product code string
     * @return ImageIcon containing the barcode, or an error placeholder
     */
    public static ImageIcon generateBarcodeIcon(String content) {
        BufferedImage img = generateBarcode128(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        return new ImageIcon(img);
    }

    /**
     * Convenience method: generate a CODE_128 barcode and set it directly
     * on the supplied {@link JLabel}.
     *
     * @param label   the label to update
     * @param content the barcode content
     */
    public static void applyBarcodeToLabel(JLabel label, String content) {
        if (content == null || content.isBlank()) {
            label.setIcon(null);
            label.setText("No barcode");
            return;
        }
        label.setText(null);
        label.setIcon(generateBarcodeIcon(content));
    }

    /** Returns a grey placeholder image when generation fails. */
    private static BufferedImage createErrorImage(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.RED);
        g.drawString("Barcode Error", 10, h / 2);
        g.dispose();
        return img;
    }
}
