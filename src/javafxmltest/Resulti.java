/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmltest;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jazzpreet
 */
public class Resulti {
    
    public SimpleStringProperty  comand = new SimpleStringProperty();
    public SimpleIntegerProperty count=new SimpleIntegerProperty();
    
    public Resulti(String comand, Integer count)
    {
        this.comand=new SimpleStringProperty(comand);
        this.count=new SimpleIntegerProperty(count);
    }
    
    public void setComand(String cnt)
    {
        comand.set(cnt);
        System.out.println(cnt);
    }
    public String getComand()
    {
        
        return comand.get();
    }
    public void setCount(Integer v)
    {
        System.out.println(v);
        count.set(v);
    }
    public Integer getCount()
    {
        return count.get();
    }
    
}
