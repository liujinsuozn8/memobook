package com.ljs.learn.generic;

public class CollegeStudent extends Student{
    private String collegeName;

    public CollegeStudent(String name, int age, String studentID, String collegeName) {
        super(name, age, studentID);
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CollegeStudent{" +
                "collegeName='" + collegeName + '\'' +
                '}';
    }
}
