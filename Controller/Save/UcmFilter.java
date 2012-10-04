/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller.Save;

import javax.swing.filechooser.*;
import java.io.File;
/**
 *
 * @author Ryosuke
 */
public class UcmFilter extends FileFilter
{
    public static final String UCM_Ext = "ucm";
    String extension;
    
    public boolean accept(File f)
    {
        if(f.isDirectory()) //accept directory
            return true;
        
        extension = getExtension(f);
        if(extension != null)
        {
            if(extension.equals(UCM_Ext))
                return true;
        }
        
        return false;
    }
    public String getDescription()
    {
        return "Use Case Map (.ucm)";
    }
    
    public String getExtension(File f)
    {
        String str = f.getName();
        int lastExt = str.lastIndexOf(".");
        if(lastExt > 0 && lastExt < str.length() - 1)
            return str.substring(lastExt+1);
        else
            return null;
    }

}
