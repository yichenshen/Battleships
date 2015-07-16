/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import battleships.controller.CommandCenterController;
import java.util.Set;
import javax.swing.DefaultListModel;

/**
 * The main and standard GUI for displaying and calculating probabilities for a
 * game of battleships.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class CommandCenter extends javax.swing.JFrame {

    /**
     * The controller for this GUI
     */
    private final CommandCenterController controller;
    private final DefaultListModel shipSelectModel;

    /**
     * Creates new form CommandCenter
     */
    public CommandCenter() {
        controller = new CommandCenterController();

        shipSelectModel = new DefaultListModel();
        Set<String> names = controller.getShipNames();

        names.forEach((name) -> shipSelectModel.addElement(name));

        initComponents();

        highSeasBoard.setSquares(controller.getBoardWidth(), controller.getBoardHeight());

        highSeasBoard.setData(controller.getData(), controller.getStateData());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidePanel = new javax.swing.JPanel();
        shipListLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        sinkButton = new javax.swing.JButton();
        shipsListScrollPane = new javax.swing.JScrollPane();
        shipsList = new javax.swing.JList();
        shipDisplay = new battleships.gui.ShipDisplay();
        jLabel1 = new javax.swing.JLabel();
        highSeasBoard = new battleships.gui.HighSeas();
        statusPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        shipListLabel.setText("Ships List");

        sinkButton.setFont(new java.awt.Font("Droid Sans", 0, 15)); // NOI18N
        sinkButton.setText("Sink");
        sinkButton.setMaximumSize(new java.awt.Dimension(100, 28));
        sinkButton.setPreferredSize(new java.awt.Dimension(80, 28));
        buttonPanel.add(sinkButton);

        shipsList.setModel(shipSelectModel);
        shipsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        shipsListScrollPane.setViewportView(shipsList);

        shipDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        javax.swing.GroupLayout shipDisplayLayout = new javax.swing.GroupLayout(shipDisplay);
        shipDisplay.setLayout(shipDisplayLayout);
        shipDisplayLayout.setHorizontalGroup(
            shipDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );
        shipDisplayLayout.setVerticalGroup(
            shipDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );

        jLabel1.setText("Ship");

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shipDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shipsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(shipListLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(shipListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shipsListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shipDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        highSeasBoard.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                highSeasBoardMouseMoved(evt);
            }
        });
        highSeasBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                highSeasBoardMouseClicked(evt);
            }
        });
        highSeasBoard.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                highSeasBoardComponentResized(evt);
            }
        });

        javax.swing.GroupLayout highSeasBoardLayout = new javax.swing.GroupLayout(highSeasBoard);
        highSeasBoard.setLayout(highSeasBoardLayout);
        highSeasBoardLayout.setHorizontalGroup(
            highSeasBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );
        highSeasBoardLayout.setVerticalGroup(
            highSeasBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        statusLabel.setText("Status");
        statusLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(highSeasBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(highSeasBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void highSeasBoardComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_highSeasBoardComponentResized
        highSeasBoard.refresh();
    }//GEN-LAST:event_highSeasBoardComponentResized

    private void highSeasBoardMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highSeasBoardMouseMoved
        highSeasBoard.setMousePos(evt.getX(), evt.getY());

        int x = highSeasBoard.getGridX(evt.getX());
        int y = highSeasBoard.getGridY(evt.getY());

        if (x != -1 && y != -1) {

            StringBuilder label = new StringBuilder();

            label.append("(").append(x + 1).append(", ").append(y + 1).append("): ");

            switch (controller.getStateData()[x][y]) {
                case OPEN: {
                    label.append(controller.getSqaureVal(x, y)).append(" possible configs.");
                    break;
                }
                case HIT: {
                    label.append("Hit ");
                    break;
                }
                case MISS: {
                    label.append("Miss ");
                    break;
                }
            }

            label.append(" Highest: ").append(controller.getMax());

            statusLabel.setText(label.toString());
        } else {
            statusLabel.setText("Status");
        }
    }//GEN-LAST:event_highSeasBoardMouseMoved

    private void highSeasBoardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highSeasBoardMouseClicked
        int x = highSeasBoard.getGridX(evt.getX());
        int y = highSeasBoard.getGridY(evt.getY());

        if (x != -1 && y != -1) {
            controller.stateChange(x, y);

            highSeasBoard.setData(controller.getData(), controller.getStateData());
        }
    }//GEN-LAST:event_highSeasBoardMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CommandCenter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CommandCenter().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private battleships.gui.HighSeas highSeasBoard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private battleships.gui.ShipDisplay shipDisplay;
    private javax.swing.JLabel shipListLabel;
    private javax.swing.JList shipsList;
    private javax.swing.JScrollPane shipsListScrollPane;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JButton sinkButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
}
