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
import javax.swing.JLabel;

/**
 * AlcSeparator
 *  
 */
class AlcSeparator extends JLabel {

    AlcSeparator() {

        this.setIcon(AlcUtil.getImageIcon("separator.png"));
        this.setAlignmentY(Component.TOP_ALIGNMENT);

    // Top Left Bottom Right
    //this.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));
    //this.setLocation(this.getX(), this.getY()-10);
    }
}

