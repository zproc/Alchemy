/*
 *  This file is part of the Alchemy project - http://al.chemy.org
 * 
 *  Copyright (c) 2007 Karl D.D. Willis
 * 
 *  Alchemy is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Alchemy is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Alchemy.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.alchemy.core;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

class AlcPopupButton extends AlcButton implements AlcConstants {

    private final static int uiPopupMenuY = 47;
    private AlcPopupMenu popup;
    private boolean inside;

    /** Creates a new instance of AlcPopupButton */
//    public AlcPopupButton(Action action) {
//        super(action);
//    }
    /** Creates a new instance of AlcPopupButton */
    AlcPopupButton(String text, String toolTip, URL iconUrl) {
        super(text, toolTip, iconUrl);
        makePopup();
    }

    private void makePopup() {
        popup = new AlcPopupMenu();

        // Add a mouse listener to detect when the button is pressed and display the popup menu
        this.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), 0, uiPopupMenuY);
                Alchemy.canvas.setTempCursor(ARROW);
            }
        });

        popup.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                inside = popup.contains(e.getPoint());
            }

            public void mouseExited(MouseEvent e) {
                inside = popup.contains(e.getPoint());
            }
            });
    }

//    public void setup(String text, String toolTip, URL iconUrl) {
//        super.setup(text, toolTip, iconUrl);
//        makePopup();
//    }
    /** Add an interface element to the popup menu */
    void addItem(Component item) {
        popup.add(item);
    }

    /** Get visibility of the popup menu */
    boolean isPopupVisible() {
        return popup.isVisible();
    }

    /** Hide the popup menu */
    void hidePopup() {
        popup.setVisible(false);
    }

    /** Test to see if the cursor is inside the popup */
    boolean isInside() {
        //System.out.println("INSIDE: " + inside);
        return inside;
    }
}