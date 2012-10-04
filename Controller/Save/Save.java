
package Controller.Save;

import java.util.Vector;
import java.io.*;

//Application
import Model.Figures.*;
import Model.*;
/**
 *
 * @author Ryosuke
 */
public class Save 
{
    private static Vector<Integer> _IDs;
    private static Vector<UCMComponent> myComponentList;
    private static Vector<UCMLine> myLineList;
            
    private static BufferedWriter bwrite = null;
    
    public static void save(BufferedWriter inStream, MapModel theModel)
    {
        //Initializations
        myComponentList = theModel.getState();
        myLineList = theModel.getLines();
        _IDs = new Vector<Integer>();
        _IDs.trimToSize();
        _IDs.add(new Integer(0));
        bwrite = inStream;
        //now start the saving
        print(SaveLoadManager.XML_S);
        writeObject(myComponentList);
        writeLine(myLineList);
        print(SaveLoadManager.XML_End);
    }
    
    /**
     * Write object. It's a list of components
     * Format:
     *  <object> [<components>...</components>]* </object>
     * @param list
     */
    private static void writeObject(Vector<UCMComponent> list)
    {
        UCMComponent temp;
        print(SaveLoadManager.Obj_S);
        for(int i = 0; i < list.size(); i++)
        {
            temp = list.get(i);
            _IDs.set(_IDs.size() - 1, new Integer(i));
            if(temp.getType().equals("UCMComposite"))
            {
                writeComposite((UCMComposite)temp);
            }
            else if(temp.getType().equals("UCMResponsabilities") || temp.getType().equals("UCMEndResponsabilities"))
            {
                writeResponsibility((UCMResponsabilities)temp);
            }
            
        }
        _IDs.removeElementAt(_IDs.size()-1);
        print(SaveLoadManager.Obj_End);
    }
    
    private static void writeLine(Vector<UCMLine> list)
    {
        UCMLine temp;
        print(SaveLoadManager.ObjLine_S);
        for(int i = 0; i < list.size(); i++)
        {
            temp = list.get(i);

            print(SaveLoadManager.Line_S);
            writeComponent(temp);
            print(SaveLoadManager.ID_S);
            print(search(myComponentList, temp.res1));
            print(SaveLoadManager.ID_End);
            print(SaveLoadManager.ID_S);
            print(search(myComponentList, temp.res2));
            print(SaveLoadManager.ID_End);
            print(SaveLoadManager.Line_End);

        }
        print(SaveLoadManager.ObjLine_End);
    }
    
    /**
     * returns id of the responsibility
     */
    private static String search(Vector<UCMComponent> list, UCMResponsabilities resp)
    {
        //myComponentList
        UCMComponent temp;
        UCMComposite cmpist;
        String res = null;
        for(int i = 0; i < list.size(); i++)
        {
            temp = list.get(i);
            if(temp.getType().equals("UCMResponsabilities") || temp.getType().equals("UCMEndResponsabilities"))
            {
                if(temp.equals(resp))
                {
                    return (Integer.toString(i));
                }
            }
            else if(temp.getType().equals("UCMComposite"))
            {
                cmpist = (UCMComposite) temp;
                res = search(cmpist.vComponents, resp);
                if(res != null)
                {
                    return (Integer.toString(i)+ "." + res);
                }
            }
        }
        return null;
    }
    /**
     * Write composites
     * Format
     * <composite> <component>...</component> [<object>...</object>]* </composite>
     * @param component
     */
    private static void writeComposite(UCMComposite component)
    {
        print(SaveLoadManager.Comp_S);
        writeComponent(component);
        if(component.vComponents.size() > 0)
        {
            _IDs.add(0);
            writeObject(component.vComponents);
        }
        else
        {
            print(SaveLoadManager.Obj_S);
            print(SaveLoadManager.Obj_End);
        }
        print(SaveLoadManager.Comp_End);
        
    }
    
    /**
     * Write responsibility:
     * Format1:
     *  <responsibility> <component>...</component> </responsibility>
     *
     * Format2:
     *  <responsibility> <component>...</component> <end>...</end> </responsibility>
     * @param component
     * Note: It's public for the sake of testing
     */
    public static void writeResponsibility(UCMResponsabilities component)
    {
        String id = "";
        UCMEndResponsabilities temp;
        //create an id for this responsibility
        for (int i = 0; i < _IDs.size(); i++)
        {
            if(i == _IDs.size() - 1)
                id += Integer.toString(_IDs.get(i).intValue());
            else
                id += Integer.toString(_IDs.get(i).intValue()) + ".";
        }
        
        //now write the responsibility
        print(SaveLoadManager.Resp_S);
        writeComponent(component);
        if(component.getType().compareTo("UCMEndResponsabilities") == 0)
        {
            temp = (UCMEndResponsabilities)component;
            print(SaveLoadManager.End_S);
            if(temp.getEnd())
                print("T");
            else
                print("F");
            print(SaveLoadManager.End_End);
        }
            
        print(SaveLoadManager.Resp_End);
    }
    
    /**
     * write component, which is common to all figures
     * Format:
     *  <component> <x1>...</x1>  <y1>...</y1>  <x2>...</x2>  <y2>...</y2> </component>
     * @param component
     */
    private static void writeComponent(UCMComponent component)
    {
        print(SaveLoadManager.Compt_S);
        print(SaveLoadManager.Type_S);
        print(component.getType());
        print(SaveLoadManager.Type_End);
 
        print(SaveLoadManager.X1_S);
        print(Integer.toString(component._x1));
        print(SaveLoadManager.X1_End);
        
        print(SaveLoadManager.Y1_S);
        print(Integer.toString(component._y1));
        print(SaveLoadManager.Y1_End);
        
        print(SaveLoadManager.X2_S);
        print(Integer.toString(component._x2));
        print(SaveLoadManager.X2_End);
        
        print(SaveLoadManager.Y2_S);
        print(Integer.toString(component._y2));
        print(SaveLoadManager.Y2_End);
        
        print(SaveLoadManager.LABL_S);
        print(component._label);
        print(SaveLoadManager.LABL_End);
        
        print(SaveLoadManager.Compt_End);
    }
    
    /**
     * setting the _IDs. It's just for the testing
     * @param newIDs
     */
    public static void setIDs(Vector<Integer> newIDs)
    {
    	_IDs = newIDs;
    }
    public static void print(String str)
    {
        if(str == null)return;

        if(bwrite != null)
        try
        {
            bwrite.write(str + " ");
        }
        catch(IOException e){System.out.println(e);}
    }
}
