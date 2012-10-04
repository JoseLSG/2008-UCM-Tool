/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.ArrayList;



/**
 * <p>XMLReader for the project...</p>
 * <p>This class only contains static file and retern ArrayList which contains array of strings
 * Basically, the caller must know the data structure of the xml file that that he/she want to use</p>
 * 
 * <p>The data returned is an arraylist. Each element of the arraylist is an array of strings</p>
 * Example:
 * <xml>
 *      <object>
 *          <name>Ryosuke</name>
 *          <password>$&*@^_&#fija</password>
 *      </object>
 *      <object>
 *          <name>You</name>
 *          <password>12323</password>
 *      </object>
 *  </xml>
 * You will get Arraylist that contains array of strings. 
 * The contents in this example
 * ArrayList:
 *      ArrayList.at(0) = String[] = {Ryosuke, $&*@^_&#fija}
 *      ArrayList.at(1) = String[] = {You, $&*@^_&#12323}
 * 
 * Note if you add a space between open and close tag, it will be in the string
 * for example
 *  <name> Me <name>
 * will be returned as " Me " with spaces around Me.
 * 
 * One last thing: If you make mistake writing xml, FUNNY thing will happen.
 * @author Ryosuke
 */
public class XMLReader
{
    public static final String XML_S = "<xml>";
    public static final String XML_END = "</xml>";
    public static final String OBJ_S = "<object>";
    public static final String OBJ_END = "</object>";
    
    //prevent usual java instanciation
    public XMLReader(){}
    /**
     * This reads the xml file. The reason that the return type is Arraylist is to allow 
     * an object to have different number of elements.
     * @param filePath
     * @return ArrayList 
     */
    public static ArrayList read(String filePath)
    {
        BufferedReader bread = null;
        ArrayList result = null;
        String str = "";
        int t;
        char tempChar = 'a';
        
        try
        {
            bread = new BufferedReader(new FileReader(filePath));
            t = bread.read();
            
            while (t != -1)
            {
                tempChar = (char)t;
                switch(tempChar)
                {
                    //skip white spaces
                    case '\t':
                    case '\n':
                    case '\r':
                        break; 
                    default:
                        str += tempChar;
                }
                t = bread.read();
            }
            result = decompose(str);
            
            
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            if(bread != null)
                try {bread.close();}catch(IOException e){}
        }
        return result;
    }
    
    /**
     * get the list of object as a array list
     * @param str
     * @return
     */
    private static ArrayList decompose(String str)
    {
        if(!str.contains(XML_S) || !str.contains(XML_END)) return null;
        
        ArrayList list = new ArrayList();
        int start = str.indexOf(XML_S) + XML_S.length();
        int end = str.indexOf(XML_END);
        int endTemp;
        
        while (start < end)
        {
            
            start = str.indexOf(OBJ_S, start) + OBJ_S.length();
            endTemp = str.indexOf(OBJ_END, start);
            
            if(start == -1 || endTemp == -1)
            {
                //problem reading
            }
            list.add(readObject(str, start, endTemp));
            start = endTemp + OBJ_END.length();
        }
        return list;
    }
    
    /**
     * Get object. All elements in this object are returned as a string
     * @param str
     * @param start
     * @param end   start index of the end </object> tag
     * @return
     */
    private static String[] readObject(String str, int start, int end)
    {
         ArrayList list = new ArrayList();
         String tag = "";
         String endTag = "";
         
         while(start < end)
         {
             tag = getTag(str, start);
             start = str.indexOf(tag, start) + tag.length();
             list.add(str.substring(start, str.indexOf("</", start)));
             endTag = getTag(str, start);//this actually get the string we want to get and the end Tag.
             start = start + endTag.length();
         }
         //hard copy the contents
         String[] temp;
         temp = new String[list.size()];
         for(int i = 0; i < list.size(); i++)
         {
             temp[i] = (String)list.get(i);
         }
         return temp;
    }
    
    private static String getTag(String str, int start)
    {
        int end = str.indexOf(">", start);
        return str.substring(start, end + 1);
    }
    
    
}
