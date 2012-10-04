package Controller.Save;

import java.io.*;

//Application
import Model.Figures.*;
import Model.*;


public class SaveLoadManager
{
    public static final String XML_S = "<xml>";
    public static final String XML_End = "</xml>";
    public static final String Obj_S = "<object>";
    public static final String Obj_End = "</object>";
    public static final String ObjLine_S = "<obj_line>";
    public static final String ObjLine_End = "</obj_line>";
    public static final String Comp_S = "<composite>";
    public static final String Comp_End = "</composite>";
    public static final String Resp_S = "<responsibility>";
    public static final String Resp_End = "</responsibility>";
    public static final String Line_S = "<line>";
    public static final String Line_End = "</line>";
    public static final String X1_S = "<x1>";
    public static final String X1_End = "</x1>";
    public static final String Y1_S = "<y1>";
    public static final String Y1_End = "</y1>";
    public static final String X2_S = "<x2>";
    public static final String X2_End = "</x2>";
    public static final String Y2_S = "<y2>";
    public static final String Y2_End = "</y2>";
    public static final String LABL_S = "<label>";
    public static final String LABL_End = "</label>";
    public static final String Type_S = "<type>";
    public static final String Type_End = "</type>";
    public static final String ID_S = "<respid>";
    public static final String ID_End = "</respid>";
    public static final String End_S = "<end>";
    public static final String End_End = "</end>";
    public static final String Compt_S = "<component>";
    public static final String Compt_End = "</component>";
    
    private static BufferedWriter bwrite = null;
    private static BufferedReader bread = null;
            
    
    public static void load(String path)
    {
        try{bread = new BufferedReader(new FileReader(path));}catch(Exception e){}
        
        if(bread != null)
        {
            Load.load(bread);
            try{bread.close();}catch(IOException e){}
        }
    }
    
    public static void save(String path, MapModel theModel)
    {
        if(!path.substring(path.length()-4).equals(".ucm"))
            path += ".ucm";
        
        try{bwrite = new BufferedWriter(new FileWriter(path));}catch(Exception e){}
        
        if(bwrite != null)
        {
            Save.save(bwrite, theModel);
            try{bwrite.close();}catch(IOException e){}
        }
        else
            System.out.println("Problem opening the file!!");
    }
    
    //prevent the usual java instanciation
    private SaveLoadManager(){}
    
}
