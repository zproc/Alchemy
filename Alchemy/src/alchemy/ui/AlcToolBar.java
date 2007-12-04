/**
 * AlcToolBar.java
 *
 * Created on November 24, 2007, 3:08 PM
 *
 * @author  Karl D.D. Willis
 * @version 1.0
 */

package alchemy.ui;

import alchemy.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class AlcToolBar extends JPanel implements ActionListener, ItemListener { // Extend JPanel rather than JComponent so the background can be set
    
    /** Reference to the root **/
    private AlcMain root;
    /** Visibility of the ToolBar */
    private boolean toolBarVisible = true;
    /** Height of the ToolBar */
    private int toolBarHeight = 60;
    /** Total height of all tool bars */
    private int totalHeight = toolBarHeight;
    
    /** Keep track of the windowSize */
    public Dimension windowSize;
    
    /** ToolBar Background Colour */
    public static Color toolBarBgColour = new Color(225, 225, 225);
    public static Color toolBarBgStartColour = new Color(235, 235, 235);
    public static Color toolBarBgEndColour = new Color(215, 215, 215);
    public static Color toolBarLineColour = new Color(140, 140, 140);
    public static Color toolBarHighlightColour = new Color(231, 231, 231);
    
    
    /** ToolBar Text Size */
    private final int toolBarTextSize = 10;
    /** ToolBar Popup Menu Y Location */
    public final int uiPopupMenuY = toolBarHeight - 10;
    
    /** Popup buttons for the create and affect button in the toolbar - these are declared global so we can hide the popup when hiding the toolbar */
    private AlcPopupButton createButton, affectButton;
    
    /** The main tool bar inside the tool bar */
    private AlcMainToolBar mainToolBar;
    /** The create tool bar inside the tool bar */
    private AlcSubToolBar createToolBar;
    /** The afect tool bars inside the tool bar */
    private ArrayList<AlcSubToolBar> affectToolBars;
    
    /**
     * Creates a new instance of AlcToolBar
     */
    public AlcToolBar(AlcMain root) {
        
        // General Toolbar settings
        this.root = root;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Turn off the visibility untill the mouse enters the top of the screen
        setToolBarVisible(false);
        
        // Create the main toolbar
        mainToolBar = new AlcMainToolBar(root);
        
        // Buttons in the main toolbar
        // Align LEFT
        JPanel toolBarLeft = new JPanel();
        toolBarLeft.setOpaque(false);   // Turn off the background
        AlcMainButton markButton = new AlcMainButton(this, "Marks", "Change the settings for making Marks", getUrlPath("../data/icon.png"));
        toolBarLeft.add(markButton);
        
        // TODO - Add Marks Button set
        //JToggleButton tbtn = new JToggleButton("Yes");
        //toolBarLeft.add(tbtn);
        mainToolBar.add(toolBarLeft, BorderLayout.LINE_START);
        
        
        // Align Right
        JPanel toolBarRight = new JPanel();
        toolBarRight.setOpaque(false);
        // Create
        createButton = new AlcPopupButton(this, "Create", "Create Shapes", getUrlPath("../data/create.png"), root.creates);
        toolBarRight.add(createButton);
        // Affect
        affectButton = new AlcPopupButton(this, "Affect", "Affect Shapes", getUrlPath("../data/create.png"), root.affects);
        toolBarRight.add(affectButton);
        
        mainToolBar.add(toolBarRight, BorderLayout.CENTER);
        this.add(mainToolBar);
        
        
        
        //JToggleButton tbtn = new JToggleButton("Yes");
        //this.add(tbtn);
        
    }
    
    public void resizeToolBar(){
        Dimension windowSize = new Dimension(this.windowSize.width, getTotalHeight());
        resizeToolBar(windowSize);
    }
    
    public void resizeToolBar(Dimension windowSize){
        this.setBounds(0, 0, windowSize.width, getTotalHeight());
        this.windowSize = windowSize;
    }
    
    
    /** Set the visibility of the UI Toolbar */
    public void setToolBarVisible(boolean b){
        this.setVisible(b);
        // Turn off the popup(s) when we leave the toolbar area
        if(!b){
            if(createButton != null) createButton.hidePopup();
            if(affectButton != null) affectButton.hidePopup();
        }
        toolBarVisible = b;
    }
    
    /** Add a Create Module sub-toolbar */
    public void addCreateSubToolBar(AlcSubToolBar subToolBar){
        
        createToolBar = subToolBar;
        createToolBar.setLocation(0, 100);
        this.add(createToolBar);
        
        // Recalculate the total height of the tool bar
        calculateTotalHeight();
        // Then resize it
        resizeToolBar();
        // And refresh it
        this.revalidate();
        
    }
    
    
    // GETTERS
    /** Return the visibility of the UI Toolbar */
    public boolean getToolBarVisible(){
        return toolBarVisible;
    }
    
    /** Return the height of the UI Toolbar */
    public int getToolBarHeight(){
        return toolBarHeight;
    }
    
    /** Return the height of the UI Toolbar */
    public int getToolBarTextSize(){
        return toolBarTextSize;
    }
    
    
    
    // IMAGE LOADING FUNCTIONS
    /** Returns a URL from a String, or null if the path was invalid. */
    public URL getUrlPath(String path){
        URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return imgUrl;
        } else {
            System.err.println("Couldn't find file: " + imgUrl.toString());
            return null;
        }
    }
    
    /** Function to append a string to the end of a given URL */
    public static URL appendStringToUrl(URL url, String append){
        String urlString = url.toString();
        URL newUrl = null;
        // Look for a file extension
        int dot = url.toString().lastIndexOf(".");
        if(dot == -1){
            try {
                // If no file extension return as is
                newUrl = new URL(urlString + append);
            } catch (MalformedURLException ex) {
                System.err.println(ex);
            }
        } else{
            try {
                // Append the string before the file extension
                newUrl = new URL(urlString.substring(0, dot) + append + urlString.substring(dot));
            } catch (MalformedURLException ex) {
                System.err.println(ex);
            }
        }
        return newUrl;
    }
    
    
    /** Returns an ImageIcon from a String, or null if the path was invalid. */
    public ImageIcon createImageIcon(String path){
        URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return createImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + imgUrl.toString());
            return null;
        }
    }
    
    /** Returns an ImageIcon from a URL, or null if the path was invalid. */
    public ImageIcon createImageIcon(URL imgUrl) {
        // TODO - somehow test that this is a valid file
        //URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            //System.out.println(imgUrl.toString());
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + imgUrl.toString());
            return null;
        }
    }
    
    // Returns just the class name -- no package info.
    public String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }
    
    
    /** Calculate the total height of the toolbar and its subtoolbars */
    public void calculateTotalHeight(){
        // Start with the main toolbar height
        int totalHeight = mainToolBar.getHeight();
        // Add the create toolbar height
        if(createToolBar != null)
            totalHeight +=  createToolBar.getHeight();
        // Add the height of each affect toolbar
        if(affectToolBars != null) {
            for (int i = 0; i < affectToolBars.size(); i++) {
                totalHeight += affectToolBars.get(i).getHeight();
            }
        }
        
        this.totalHeight = totalHeight;
    }
    
    /** Return the total height of the toolbar and its subtoolbars */
    public int getTotalHeight(){
        return totalHeight;
    }
    
    
    public void loadCreate(int index){
        root.setCurrentCreate(index);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        //String rawCommand = e.getActionCommand();
        
        //JMenuItem source = (JMenuItem)(e.getSource());
        //System.out.println( source.getClass().getName() );
        
        // Get the type of command
        //String commandType = rawCommand.substring(0, rawCommand.lastIndexOf("-"));
        // Get the index
        //int index = Integer.parseInt( rawCommand.substring(rawCommand.lastIndexOf("-")+1) );
        
        AlcRadioButtonMenuItem source = (AlcRadioButtonMenuItem)e.getSource();
        String command = source.getCommand();
        int index = source.getIndex();
        
        
        if(command.equals("mark")){
            
        } else if(command.equals("create")){
            
            //AlcMenuItem source = (AlcMenuItem)(e.getSource());
            //int index = createPopup.getComponentIndex(source);
            //System.out.println(index);
            //System.out.println(e.getSource());
            root.setCurrentCreate(index);
            
        }
        
        
    }
    
    public void itemStateChanged(ItemEvent e) {
        
        // TODO - Right now this can only be used for the affect module popup button
        // Needs to be coded so we can tell who is sending the command
        
        AlcCheckBoxMenuItem source = (AlcCheckBoxMenuItem)e.getItemSelectable();
        String command = source.getCommand();
        int index = source.getIndex();
        
        
        // SELECTED
        if (e.getStateChange() == ItemEvent.SELECTED) {
            
            //System.out.println( index );
            
            root.addAffect(index);
            
            // DESELECTED
        } else {
            
            root.removeAffect(index);
            
        }
    }
    
    
}