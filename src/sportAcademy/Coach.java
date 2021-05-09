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
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdul Aziz
 */
public class Coach {
    private int id = (int) (Math.random()*Math.random()*10000);
    private String name;
    private GregorianCalendar dateJoined;
    private GregorianCalendar dateTerminated;
    private int hourlyRate;
    private long phone;
    private String address;
    private int sportCenterCode;
    private int sportCode;
    private int ratings;
    
    public Coach(){};
    public Coach(String name, GregorianCalendar dateJoined, int hourlyRate, long phone, String address, int sportCenterCode, int sportCode) {
        this.name = name;
        this.dateJoined = dateJoined;
        this.hourlyRate = hourlyRate;
        this.phone = phone;
        this.address = address;
        this.sportCenterCode = sportCenterCode;
        this.sportCode = sportCode;
    }
    
    public static String toJson(Coach coach){
        Gson gson = new Gson();
        return gson.toJson(coach, Coach.class);
    }
    public static Coach fromJson(String json){
        Gson gson = new Gson(); 
        return gson.fromJson(json, Coach.class);
    }
    public static ArrayList<Coach> getAllCoaches(){
        ArrayList<Coach> list = new ArrayList<Coach>();
        String fileName = "src/Records/coaches.txt";
        Path path = new File(fileName).toPath();
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(path, 
                StandardCharsets.UTF_8)) {
            
            Coach c[] = gson.fromJson(reader, Coach[].class);
            if(c!=null)
            for(int i=0; i<c.length; i++){
                System.out.println("Record from file: "+c[i].id);
                list.add(c[i]);
            }
            return list;
        } catch (IOException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Coach.java in getAllCoaches()");
        }
        return list;        
    }
    public boolean saveToDisk(){
        ArrayList<Coach> list = Coach.getAllCoaches();
        list.add(this);
        return this.saveAllToDisk(list);
    }
    private static boolean saveAllToDisk(ArrayList<Coach> list){
        String fileName = "src/Records/coaches.txt";        
        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter isr = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8)) {
            
            Gson gson = new Gson();                      
            gson.toJson(list, isr);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Coach.java in saveAllToDisk()");
        }
        return false;
    }
    public static Coach findInDisk(int id){
        ArrayList<Coach> list = Coach.getAllCoaches();
        for(int i=0; i<list.size(); i++){
            if(id==list.get(i).id){
                return list.get(i); 
            }
        }
        return null;
    }
    public static ArrayList<Coach> findInDiskByRatings(int ratings){
        ArrayList<Coach> list = Coach.getAllCoaches();
        ArrayList<Coach> result = new ArrayList<Coach>();
        for(int i=0; i<list.size(); i++){
            if(list.get(i).ratings==ratings){
                result.add(list.get(i));
            }
        }
        return result;
    }
    public static boolean deleteFromDisk(int id){
        ArrayList<Coach> list = Coach.getAllCoaches();
        for(int i=0; i<list.size(); i++){
            if(id==list.get(i).id){
                list.remove(i);
                return Coach.saveAllToDisk(list); 
            }
        }
        return false;
    }
    public static boolean updateInDisk(Coach updatedCoachObject){
        ArrayList<Coach> list = Coach.getAllCoaches();
        for(int i=0; i<list.size(); i++){
            if(updatedCoachObject.id==list.get(i).id){
                list.remove(i);
                list.add(i, updatedCoachObject);
                return Coach.saveAllToDisk(list); 
            }
        }
        return false;
    }
    
    public static void main(String[] args){
        Coach c = new Coach(
                "c1",
                new GregorianCalendar(),
                100,
                5468987708l,
                "address",
                324,
                556
        );
        Coach c2 = new Coach();
        System.out.println(Coach.toJson(c));
        //c.saveToDisk();
        c2.saveToDisk();   
        System.out.println("Remove form disk: "+Coach.deleteFromDisk(c.getId()));
    }
    
    public int getId(){
        return this.id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public GregorianCalendar getDateJoined() {
        return dateJoined;
    }
    public void setDateJoined(GregorianCalendar dateJoined) {
        this.dateJoined = dateJoined;
    }
    public GregorianCalendar getDateTerminated() {
        return dateTerminated;
    }
    public void setDateTerminated(GregorianCalendar dateTerminated) {
        this.dateTerminated = dateTerminated;
    }
    public int getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getSportCenterCode() {
        return sportCenterCode;
    }
    public void setSportCenterCode(int sportCenterCode) {
        this.sportCenterCode = sportCenterCode;
    }
    public int getSportCode() {
        return sportCode;
    }
    public void setSportCode(int sportCode) {
        this.sportCode = sportCode;
    }
    public int getRatings() {
        return ratings;
    }
    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
    
}
