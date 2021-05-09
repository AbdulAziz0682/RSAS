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
public class Student {
    private int id = (int) (Math.random()*Math.random()*10000);
    private String name;
    private int sportCenterCode;
    private int sportCode;
    private int age;
    private String address;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Student(){}

    public Student(String name, int sportCenterCode, int sportCode, int age, String address) {
        this.name = name;
        this.sportCenterCode = sportCenterCode;
        this.sportCode = sportCode;
        this.age = age;
        this.address = address;
    }
    public String toPrettyString(){
        String pretty = "\t\tSTUDENT\t\t\n";
        pretty += "ID: \t"+this.id+"\n";
        pretty += "Name: \t"+this.name+"\n";
        pretty += "CenterID: \t"+this.sportCenterCode+"\n";
        pretty += "SportID: \t"+this.sportCode+"\n";
        pretty += "Age: \t"+this.age+"\n";
        pretty += "Address: \t"+this.address+"\n";
        return pretty;
    }
    public static String toJson(Student std){
        Gson gson = new Gson();
        return gson.toJson(std, Student.class);
    }
    public static Student fromJson(String json){
        Gson gson = new Gson(); 
        return gson.fromJson(json, Student.class);
    }
    public static ArrayList<Student> getAllStudents(){
        ArrayList<Student> list = new ArrayList<Student>();
        String fileName = "src/Records/students.txt";
        Path path = new File(fileName).toPath();
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(path, 
                StandardCharsets.UTF_8)) {
            
            Student c[] = gson.fromJson(reader, Student[].class);
            if(c!=null)
            for(int i=0; i<c.length; i++){
                System.out.println("Record from file: "+c[i].id);
                list.add(c[i]);
            }
            return list;
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Student.java in getAllStudents()");
        }
        return list;        
    }
    public boolean saveToDisk(){
        ArrayList<Student> list = Student.getAllStudents();
        list.add(this);
        return this.saveAllToDisk(list);
    }
    private static boolean saveAllToDisk(ArrayList<Student> list){
        String fileName = "src/Records/students.txt";        
        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter isr = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8)) {
            
            Gson gson = new Gson();                      
            gson.toJson(list, isr);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error at Student.java in saveAllToDisk()");
        }
        return false;
    }
    public static Student findInDisk(int id){
        ArrayList<Student> list = Student.getAllStudents();
        for(int i=0; i<list.size(); i++){
            if(id==list.get(i).id){
                return list.get(i); 
            }
        }
        return null;
    }
    public static boolean deleteFromDisk(int id){
        ArrayList<Student> list = Student.getAllStudents();
        for(int i=0; i<list.size(); i++){
            if(id==list.get(i).id){
                list.remove(i);
                return Student.saveAllToDisk(list); 
            }
        }
        return false;
    }
    public static boolean updateInDisk(Student updatedStudentObject){
        ArrayList<Student> list = Student.getAllStudents();
        for(int i=0; i<list.size(); i++){
            if(updatedStudentObject.id==list.get(i).id){
                list.remove(i);
                list.add(i, updatedStudentObject);
                return Student.saveAllToDisk(list); 
            }
        }
        return false;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public static void main(String[] args){
        Student std = new Student();
        std.saveToDisk();
    }
     
    
}
