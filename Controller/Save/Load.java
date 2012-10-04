
package Controller.Save;
import java.io.*;
import java.util.Vector;

//Application
import Model.Figures.*;
import Model.*;

/**
 *
 * @author Ryosuke
 */
public class Load 
{
    private static String token;
    private static String[] list;
    private static int index;
    public static Vector<UCMComponent> myComponentList;
    public static Vector<UCMLine> myLineList;
    
    public static void load(BufferedReader bread)
    {
        try{token = bread.readLine();}catch(IOException e){}
        list = token.split(" ");
        index = 1;  //skip <xml>
        
        if(list.length < 1) throw new RuntimeException("This is not right");
        
        myComponentList = loadObject(new Vector<UCMComponent>(0));
        myLineList = loadLineObject(new Vector<UCMLine>(0));
        bindParents(null, myComponentList);
        
    }
    
    private static Vector<UCMComponent> loadObject(Vector<UCMComponent> myList)
    {
        //if(!list[index].equals(SaveLoadManager.Obj_S)) return null;
        skip(SaveLoadManager.Obj_S);
        while(index < list.length)
        {
            if(list[index].equals(SaveLoadManager.Obj_End))
                return myList;
            else if(list[index].equals(SaveLoadManager.Comp_S))
                myList.add(loadComposite());
            else if(list[index].equals(SaveLoadManager.Resp_S))
                myList.add(loadResponsabilities());
        }
        skip(SaveLoadManager.Obj_End);
        return myList;
    }
    
    private static Vector<UCMLine> loadLineObject(Vector<UCMLine> myLines)
    {
        skip(SaveLoadManager.ObjLine_S);
        while(index < list.length)
        {
            if(list[index].equals(SaveLoadManager.ObjLine_End))
                return myLines;
            else if(list[index].equals(SaveLoadManager.Line_S))
                myLines.add(loadLine());
        }
        skip(SaveLoadManager.ObjLine_End);
        return myLines;
    }
    
    private static UCMComposite loadComposite()
    {
        if(!list[index].equals(SaveLoadManager.Comp_S)) throw new RuntimeException("Require Composite!!");
        
        skip(SaveLoadManager.Comp_S);
        
        String[] component;
        Vector<UCMComponent> cpList;
        UCMComposite compo;
        component = loadComponent();
        compo = new UCMComposite(null, null, null, Integer.parseInt(component[1]),
                                        Integer.parseInt(component[2]),
                                        Integer.parseInt(component[3]),
                                        Integer.parseInt(component[4]));
        compo._label = component[5];
        cpList = loadObject(new Vector<UCMComponent>(0));
        compo.vComponents = cpList;  
 
        skip(SaveLoadManager.Comp_End);
        return compo;
    }
    
    private static UCMResponsabilities loadResponsabilities()
    {
        if(!list[index].equals(SaveLoadManager.Resp_S)) throw new RuntimeException("Require Responsabilities!!");
        
        skip(SaveLoadManager.Resp_S);
        UCMResponsabilities resp;
        String[] component = loadComponent();
        
        if(component[0].equals("UCMEndResponsabilities"))
        {
            String end = loadAttr();
            UCMEndResponsabilities endResp = new UCMEndResponsabilities(null, Integer.parseInt(component[1]),
                                                                        Integer.parseInt(component[2]));
            if(end.equals("T")) endResp.onOff();
            
            resp = endResp;
        }
        else
            resp = new UCMResponsabilities(null, null, null, Integer.parseInt(component[1]),Integer.parseInt(component[2]));
        
        skip(SaveLoadManager.Resp_End);
        resp._label = component[5];
        return resp;
    }
    
    private static String[] loadComponent()
    {
        if(!list[index].equals(SaveLoadManager.Compt_S)) throw new RuntimeException("Require Component!!");
        skip(SaveLoadManager.Compt_S);
        
        String[] str = new String[6];
        str[0] = loadAttr();    checkType(str[0]);
        str[1] = loadAttr();    checkInt(str[1]);
        str[2] = loadAttr();    checkInt(str[2]);
        str[3] = loadAttr();    checkInt(str[3]);
        str[4] = loadAttr();    checkInt(str[4]);
        str[5] = loadAttr();
        skip(SaveLoadManager.Compt_End);
        return str;
    }
    
    private static UCMLine loadLine()
    {
        skip(SaveLoadManager.Line_S);
        
        String id1, id2;
        UCMLine line = new UCMLine(null);
        String[] component = new String[5];
        
        component = loadComponent();
        id1 = loadAttr();
        id2 = loadAttr();
        
        for (int i = 0; i < id1.length(); i++)
            if(id1.charAt(i) == '.')
                id1 = id1.substring(0, i) + " " + id1.substring(i+1);
        for (int i = 0; i < id2.length(); i++)
            if(id2.charAt(i) == '.')
                id2 = (id2.substring(0, i) + " " + id2.substring(i+1));
//        id1.replace('.', ' ');
//        id2.replace('.', ' ');
        
        line.res1 = getResp(id1.split(" "));
        line.res2 = getResp(id2.split(" "));

        skip(SaveLoadManager.Line_End);
       line._label = component[5];
        return line;
        
    }
    /**
     * grab the attribute
     * @return
     */
    private static String loadAttr()
    {
        String endTag = "";
        String attr = "";   //element to return
        
        if(list[index].equals(SaveLoadManager.Type_S))      	endTag = SaveLoadManager.Type_End;
        else if(list[index].equals(SaveLoadManager.X1_S))   	endTag = SaveLoadManager.X1_End;
        else if(list[index].equals(SaveLoadManager.Y1_S))   	endTag = SaveLoadManager.Y1_End;
        else if(list[index].equals(SaveLoadManager.X2_S))   	endTag = SaveLoadManager.X2_End;
        else if(list[index].equals(SaveLoadManager.Y2_S))   	endTag = SaveLoadManager.Y2_End;
        else if(list[index].equals(SaveLoadManager.ID_S))   	endTag = SaveLoadManager.ID_End;
        else if(list[index].equals(SaveLoadManager.LABL_S))   	endTag = SaveLoadManager.LABL_End;
        else if(list[index].equals(SaveLoadManager.End_S))   	endTag = SaveLoadManager.End_End;
        else throw new RuntimeException("unKnown attribute tag found!!");
        
        attr = list[index + 1];
        skip(endTag);
        return attr;
    }
    
    //checks if the given string is one of components
    private static void checkType(String str)
    {
    	if(str.equals("UCMComposite") || 
    	           str.equals("UCMResponsabilities") || 
    	           str.equals("UCMEndResponsabilities") ||
    	           str.equals("UCMLine"))
    	            return;
        
        throw new RuntimeException("This is not acceptted type!!");
    }
    
    //checks if the given string is a integer
    private static void checkInt(String str)
    {
        try{Integer.parseInt(str);}
        catch(NumberFormatException e)
        {throw new RuntimeException(e);}
    }
    
    private static UCMResponsabilities getResp(String[] nums)
    {
        if(nums.length == 1)
        {
            if(myComponentList.get(Integer.parseInt(nums[0])) instanceof UCMResponsabilities)
                return (UCMResponsabilities) myComponentList.get(Integer.parseInt(nums[0]));
            else throw new RuntimeException("Non Responsabilities component found");
        }
        else
        {
            if(myComponentList.get(Integer.parseInt(nums[0])) instanceof UCMComposite)
            {
                UCMComposite compo = (UCMComposite)myComponentList.get(Integer.parseInt(nums[0]));
                return getResponsabilities((UCMComposite)myComponentList.get(Integer.parseInt(nums[0])),
                                            nums,
                                            1);
            }
            else throw new RuntimeException("Non Composite component found");
        }
    }
    private static UCMResponsabilities getResponsabilities(UCMComposite vec, String[] ids, int num)
    {
        if(ids.length - 1 == num)
            if(vec.vComponents.get(Integer.parseInt(ids[num])) instanceof UCMResponsabilities)
                return (UCMResponsabilities)vec.vComponents.get(Integer.parseInt(ids[num]));
            else
                throw new RuntimeException("Non UCMResponsabilities found");
        
        if(vec.vComponents.get(Integer.parseInt(ids[num])) instanceof UCMComposite)
        {
            return getResponsabilities((UCMComposite)vec.vComponents.get(Integer.parseInt(ids[num])), ids, ++num);
        }
        else
            throw new RuntimeException("Non Composite Component found");
                
    }
    
    //skip to the next tag from the given tag
    private static void skip(String tag)
    {
        if(index >= list.length) throw new RuntimeException("Index out of Bount");
        while(!list[index].equals(tag))
        {
            index++;
            if(index >= list.length) throw new RuntimeException("Can't find the tag!!");
        }
        //to the next
        index++;
    }
    
    private static void bindParents(UCMComponent parent, Vector<UCMComponent> list)
    {
        int i;
        UCMComposite tmp;
        for(i = 0; i < list.size(); i++)
        {
            list.get(i)._parentComponent = parent;
            if(list.get(i) instanceof UCMComposite)
            {
                tmp = (UCMComposite)list.get(i);
                bindParents(tmp, tmp.vComponents);
            }
        }
    }
    
    
}

