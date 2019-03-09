package com.javarush.task.task29.task2909.human;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class University  {
    private int age;
    private String name;
    private List<Student> students = StudentsDataBase.students;

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double param) {
        //List<Student> studentCopy = new ArrayList<>();
        //Collections.copy(students, studentCopy);
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
            }
        });
        for(Student stud : students){
            if(stud.getAverageGrade() == param){
                return stud;
            }
        }
        int averStudent = students.size() / 2;
        return students.get(averStudent);
    }

    public Student getStudentWithMaxAverageGrade() {
        //List<Student> studentCopy = new ArrayList<>();
        //Collections.copy(students, studentCopy);
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
            }
        });
        return students.get(students.size()-1);
    }

    public Student getStudentWithMinAverageGrade(){
        //List<Student> studentCopy = new ArrayList<>();
        //Collections.copy(students, studentCopy);
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
            }
        });
        return students.get(0);
    }

    public void expel(Student student){
        students.remove(student);

    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}