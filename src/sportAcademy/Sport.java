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
public class Sport {
    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    private int fees;
    private String schedule;
    public Sport() {}
    public Sport(int code, String name, int fees, String schedule){
        this.code = code;
        this.name = name;
        this.fees = fees;
        this.schedule = schedule;
    }
    public String toPrettyString(){
        String pretty = "\t\tSPORT\t\t\n";
        pretty += "ID: \t"+this.code+"\n";
        pretty += "Name: \t"+this.name+"\n";        
        pretty += "Fees: \t"+this.fees+"\n";
        pretty += "Schedule: \t"+this.schedule+"\n";
        return pretty;
    }
    public static String toJson(Sport sport){
        Gson gson = new Gson();
        return gson.toJson(sport, Sport.class);
    }
    public static Sport fromJson(String json){
        Gson gson = new Gson(); 
        return gson.fromJson(json, Sport.class);
    }
    public static ArrayList<Sport> getAllSports(){
        ArrayList<Sport> list = new ArrayList<Sport>();
        String fileName = "src/Records/sports.txt";
        Path path = new File(fileName).toPath();
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(path, 
                StandardCharsets.UTF_8)) {
            
            Sport c[] = gson.fromJson(reader, Sport[].class);
            if(c!=null)
            for(int i=0; i<c.length; i++){
                System.out.println("Record from file: "+c[i].code);
                list.add(c[i]);
            }
            return list;
        } catch (IOException ex) {
            Logger.getLogger(Sport.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Sport.java in getAllSports()");
        }
        return list;        
    }
    public boolean saveToDisk(){
        if(Sport.findInDisk(this.code)!=null) return false;
        ArrayList<Sport> list = Sport.getAllSports();
        list.add(this);
        return this.saveAllToDisk(list);
    }
    private static boolean saveAllToDisk(ArrayList<Sport> list){
        String fileName = "src/Records/sports.txt";        
        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter isr = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8)) {
            
            Gson gson = new Gson();                      
            gson.toJson(list, isr);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Sport.java in saveAllToDisk()");
        }
        return false;
    }
    public static Sport findInDisk(int code){
        ArrayList<Sport> list = Sport.getAllSports();
        for(int i=0; i<list.size(); i++){
            if(code==list.get(i).code){
                return list.get(i); 
            }
        }
        return null;
    }
    public static boolean deleteFromDisk(int code){
        ArrayList<Sport> list = Sport.getAllSports();
        for(int i=0; i<list.size(); i++){
            if(code==list.get(i).code){
                list.remove(i);
                return Sport.saveAllToDisk(list); 
            }
        }
        return false;
    }
    public static boolean updateInDisk(Sport updatedSportObject){
        ArrayList<Sport> list = Sport.getAllSports();
        for(int i=0; i<list.size(); i++){
            if(updatedSportObject.code==list.get(i).code){
                list.remove(i);
                list.add(i, updatedSportObject);
                return Sport.saveAllToDisk(list); 
            }
        }
        return false;
    }
}
