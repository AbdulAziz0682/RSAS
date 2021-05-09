/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportAcademy;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdul Aziz
 */
public class SportCenter {
    private int code;
    private String name;
    private String location;
    
    public SportCenter(){}

    public SportCenter(int code, String name, String location) {
        this.code = code;
        this.name = name;
        this.location = location;
    }
    public String toPrettyString(){
        String pretty = "\t\tSPORT CENTER\t\t\n";
        pretty += "ID: \t"+this.code+"\n";
        pretty += "Name: \t"+this.name+"\n";
        pretty += "Location: \t"+this.location+"\n";
        return pretty;
    }
    public static String toJson(SportCenter center){
        Gson gson = new Gson();
        return gson.toJson(center, SportCenter.class);
    }
    public static SportCenter fromJson(String json){
        Gson gson = new Gson(); 
        return gson.fromJson(json, SportCenter.class);
    }
    public static ArrayList<SportCenter> getAllSportCenteres(){
        ArrayList<SportCenter> list = new ArrayList<SportCenter>();
        String fileName = "src/Records/centers.txt";
        Path path = new File(fileName).toPath();
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(path, 
                StandardCharsets.UTF_8)) {
            
            SportCenter c[] = gson.fromJson(reader, SportCenter[].class);
            if(c!=null)
            for(int i=0; i<c.length; i++){
                System.out.println("Record from file: "+c[i].code);
                list.add(c[i]);
            }
            return list;
        } catch (IOException ex) {
            Logger.getLogger(SportCenter.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at SportCenter.java in getAllSportCenteres()");
        }
        return list;        
    }
    public boolean saveToDisk(){
        if(SportCenter.findInDisk(this.code)!=null) return false;
        ArrayList<SportCenter> list = SportCenter.getAllSportCenteres();
        list.add(this);
        return this.saveAllToDisk(list);
    }
    private static boolean saveAllToDisk(ArrayList<SportCenter> list){
        String fileName = "src/Records/centers.txt";        
        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter isr = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8)) {
            
            Gson gson = new Gson();                      
            gson.toJson(list, isr);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at SportCenter.java in saveAllToDisk()");
        }
        return false;
    }
    public static SportCenter findInDisk(int code){
        ArrayList<SportCenter> list = SportCenter.getAllSportCenteres();
        for(int i=0; i<list.size(); i++){
            if(code==list.get(i).code){
                return list.get(i); 
            }
        }
        return null;
    }
    public static boolean deleteFromDisk(int code){
        ArrayList<SportCenter> list = SportCenter.getAllSportCenteres();
        for(int i=0; i<list.size(); i++){
            if(code==list.get(i).code){
                list.remove(i);
                return SportCenter.saveAllToDisk(list); 
            }
        }
        return false;
    }
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public static void main(String[] args){
        SportCenter sp1= new SportCenter(1, "SPC1", "North-Kuala Lumpur");
        SportCenter sp2 = new SportCenter(2, "SPC2", "South-West Kuala Lumpur");
        SportCenter sp3 = new SportCenter(3, "SPC3", "Central Kuala Lumpur");
        System.out.println(sp1.saveToDisk());
        System.out.println(sp2.saveToDisk());
        System.out.println(sp3.saveToDisk());
    }
}
