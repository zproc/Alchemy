/*
 * This file is part of the Alchemy project - http://al.chemy.org
 * 
 * Copyright (c) 2007 Karl D.D. Willis
 * 
 * Alchemy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Alchemy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Alchemy.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.alchemy.core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

/**
 * AlcPalette
 * @author Karl D.D. Willis
 */
class AlcPalette extends JWindow implements KeyListener, MouseListener, AlcConstants {

    //private JPanel paletteContent;
    public JPanel mainPalette;
    private Component content;
    private AlcPaletteTitleBar titleBar;
    private int maxSize = 1280;
    private int minSize = 600;
    private int paletteHeight = 88;

    AlcPalette(AlcWindow owner) {
        super(owner);

        setGoodSize();
        this.setBackground(Color.WHITE);
        //this.setLocationRelativeTo(null); 
        this.setFocusable(true);

        mainPalette = new JPanel();
        mainPalette.setBackground(AlcToolBar.toolBarBgColour);
        mainPalette.setBorder(new LineBorder(AlcToolBar.toolBarLineColour, 1));
        mainPalette.setLayout(new BorderLayout());
        mainPalette.setCursor(ARROW);
        this.addKeyListener(this);
        this.addMouseListener(this);

        titleBar = new AlcPaletteTitleBar(this);
        //titleBar.add(new JLabel("ho"));
        //this.setLayout(new BorderLayout());
        mainPalette.add("West", titleBar);

        this.setContentPane(mainPalette);
//        this.pack();
//        this.setVisible(true);
    }

    void shiftPalette(int x, int y) {
        Point aPoint = this.getLocation();
        this.setLocation(aPoint.x + x, aPoint.y + y);
    }

    /** Add a component to the main content area */
    void addContent(Component comp) {
        setGoodSize();
        if (content != null) {
            mainPalette.remove(content);
        }
        this.content = comp;
        mainPalette.add("Center", comp);
        mainPalette.revalidate();
    }

    private void setGoodSize() {
        if (Alchemy.window.getWindowSize().width < minSize) {
            this.setSize(minSize, paletteHeight);
        } else if (Alchemy.window.getWindowSize().width > maxSize) {
            this.setSize(maxSize, paletteHeight);
        } else {
            this.setSize(Alchemy.window.getWindowSize().width, paletteHeight);
        }
    }

    // Send the key events to the root
    public void keyPressed(KeyEvent event) {
        Alchemy.window.keyPressed(event);
    }

    public void keyReleased(KeyEvent event) {
        Alchemy.window.keyReleased(event);
    }

    public void keyTyped(KeyEvent event) {
        Alchemy.window.keyTyped(event);
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
        // Set the default cursor
        Alchemy.canvas.setTempCursor(ARROW);
    }

    public void mouseExited(MouseEvent event) {
        // Set the cursor back to a cross hair when leaving the palette
        if (!this.contains(event.getPoint())) {
            Alchemy.canvas.restoreCursor();
            //Alchemy.canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    public void mousePressed(MouseEvent event) {
        // TODO - Main window is unfocused on a mac when clicking on the palette
        this.requestFocus();
        this.toFront();
    }

    public void mouseReleased(MouseEvent event) {
    }
}