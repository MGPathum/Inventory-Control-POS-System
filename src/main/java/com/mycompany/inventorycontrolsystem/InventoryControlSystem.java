package com.mycompany.inventorycontrolsystem;

import com.mycompany.inventorycontrolsystem.view.LoginFrame;

import javax.swing.*;

/**
 * Application entry point.
 *
 * Sets Nimbus Look and Feel (matching LoginFrame.main), then launches the
 * LoginFrame on the Event Dispatch Thread.
 */
public class InventoryControlSystem {

    
    public static void main(String[] args) {

        // Apply Nimbus before any Swing component is constructed
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Could not apply Nimbus L&F");
        }

        System.out.println("=== Inventory Control System Starting ===");

        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
            // Second invokeLater fires after the first layout + paint pass so
            // lblImage has real pixel dimensions when triggerImageLoad() runs.
            SwingUtilities.invokeLater(frame::triggerImageLoad);
        });
    }
}
