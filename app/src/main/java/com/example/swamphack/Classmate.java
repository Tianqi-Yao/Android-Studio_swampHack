package com.example.swamphack;

public class Classmate {
    private String studentName;
    private String studentEmail;

    public Classmate(String studentName, String studentEmail){
        this.studentEmail= studentEmail;
        this.studentName=studentName;
    }

    public String getStudentName(){
        return studentName;
    }
    public String getStudentEmail(){
        return studentEmail;
    }
}
