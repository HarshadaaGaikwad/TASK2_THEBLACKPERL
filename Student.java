import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private List<String> courses;
    private List<Double> grades;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public List<Double> getGrades() {
        return grades;
    }

    // Add a course and its grade
    public void addGrade(String course, double grade) {
        courses.add(course);
        grades.add(grade);
    }

    // Calculate GPA
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;

        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    // Display student information
    public void displayInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Courses:");

        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i) + ": " + grades.get(i));
        }

        System.out.println("GPA: " + calculateGPA());
    }
}

public class StudentManagementSystem {
    private static final String FILENAME = "students.txt";

    // Read student records from file
    private static List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                Student student = new Student(id, name);

                for (int i = 2; i < parts.length; i += 2) {
                    String course = parts[i];
                    double grade = Double.parseDouble(parts[i + 1]);
                    student.addGrade(course, grade);
                }

                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Write student records to file
    private static void writeStudentsToFile(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Student student : students) {
                StringBuilder sb = new StringBuilder();
                sb.append(student.getId()).append(",").append(student.getName());

                for (int i = 0; i < student.getCourses().size(); i++) {
                    sb.append(",").append(student.getCourses().get(i)).append(",")
                            .append(student.getGrades().get(i));
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Student> students = readStudentsFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display Student Information");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    Student newStudent = new Student(id, name);
                    students.add(newStudent);
                    writeStudentsToFile(students);
                    System.out.println("Student added successfully.");
                    break;
                case 2:
                    // Update student
                    break;
                case 3:
                    // Delete student
                    break;
                case 4:
                    // Display student information
                    System.out.print("Enter student ID: ");
                    String searchId = scanner.nextLine();
                    boolean found = false;
                    for (Student student : students) {
                        if (student.getId().equals(searchId)) {
                            student.displayInfo();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student with ID " + searchId + " not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }
}
