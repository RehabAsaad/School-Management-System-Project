package schoolsystem;

import java.util.Scanner;

public class schoolsystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        School s = new School();
        s.run();
    }
}

class Student {
    private String studentName;
    private int studentId;
    private String studentPassword;
    private Course[] courses;
    private int courseCount;

    public Student(String studentName, int studentId, String studentPassword) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentPassword = studentPassword;
        this.courses = new Course[6];
        this.courseCount = 0;
    }

    public String getName() {
        return studentName;
    }

    public int getId() {
        return studentId;
    }

    public String getPassword() {
        return studentPassword;
    }

    public Course[] getCourses() {
        return courses;
    }
//Checks if there is enough space in the array to add a new course
    public void enrollCourse(Course course) {
        if (courseCount < courses.length) {
            courses[courseCount++] = course;
        }
    }
//Find the course and remove it
    public void removeCourse(Course course) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].equals(course)) {
                for (int j = i; j < courseCount - 1; j++) {
                    courses[j] = courses[j + 1];
                }
                courses[--courseCount] = null;
                break;
            }
        }
    }
}

class Course {
    private String courseId;
    private String courseName;
    private int credits;

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    
}

class School {
    private Student[] students;
    private static Course[] courses;
    private static int courseCount;
    private int studentCount;

    public School() {
        students = new Student[100]; // Maximum 100 students
        courses = new Course[6]; // Maximum 6 courses
        studentCount = 0;
        courseCount = 0;
        initializeCourses();
    }
//initialize Courses
    private static void initializeCourses() {
        courses[0] = new Course("Ar01", "Arabic", 4);
        courses[1] = new Course("Ma02", "Mathematics", 3);
        courses[2] = new Course("En03", "English", 3);
        courses[3] = new Course("Bi04", "Biology", 4);
        courses[4] = new Course("Ph05", "Physics", 4);
        courses[5] = new Course("Ch06", "Chemistry", 4);
        courseCount = 6; // Update courseCount to reflect the number of courses
    }
//add new student
    public void addStudent(String id, String name, String password) {
        if (isStudentExists(id)) {
            System.out.println("Student with this ID already exists.");
        } else if (studentCount < students.length) {
            students[studentCount++] = new Student(name, Integer.parseInt(id), password); // Assuming max 6 courses per student
            System.out.println("Student added successfully...");
        } else {
            System.out.println("Student limit reached...");
        }
    }
// check if student id is repeated
    private boolean isStudentExists(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() == Integer.parseInt(id)) {
                return true;
            }
        }
        return false;
    }
//make sure if the student’s data exists or not
    public Student verifyStudent(String id, String password) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() == Integer.parseInt(id) && students[i].getPassword().equals(password)) {
                return students[i];
            }
        }
        System.out.println("This student does not exist..");
        return null;
    }
//enroll in  Course if student and course are exist
    public void enrollCourse(String studentId, String courseId, String password) {
        Student student = verifyStudent(studentId, password);
        if (student != null) {
            Course course = getCourseById(courseId);
            if (course != null) {
                student.enrollCourse(course);
                System.out.println("You have successfully registered for the course.");
            } else {
                System.out.println("The course doesn't exist.");
            }
        }
    }
// remove course if  student and course are exist
    public void removeCourse(String studentId, String courseId, String password) {
        Student student = verifyStudent(studentId, password);
        if (student != null) {
            Course course = getCourseById(courseId);
            if (course != null) {
                student.removeCourse(course);
                System.out.println("The course has been removed successfully.");
            } else {
                System.out.println("The course doesn't exist.");
            }
        }
    }
//print student information
    public void showStudentInfo(String studentId, String password) {
        Student student = verifyStudent(studentId, password);
        if (student != null) {
            System.out.println("Student Information:");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Enrolled Courses:");
            Course[] courses = student.getCourses();
            for (Course course : courses) {
                if (course != null) {
                    System.out.println(course.getCourseName() + " (" + course.getCredits() + " credits)");
                }
            }
        }
    }
//return course name by his ID
    private Course getCourseById(String courseId) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId().equals(courseId)) {
                return courses[i];
            }
        }
        return null;
    }
//Make sure the student ID is valid
    private String getValidStudentIdInput(Scanner input, String prompt) {
        String inputStr;
        while (true) {
            System.out.println(prompt);
            inputStr = input.nextLine();
            if (isNumeric(inputStr) == true) {
                break;
            } else {
                System.out.println("Invalid student ID. Please enter numbers only.");
            }
        }
        return inputStr;
    }
//Make sure the course ID is valid
    private String getValidCourseIdInput(Scanner input, String prompt) {
        String inputStr;
        while (true) {
            System.out.println(prompt);
            inputStr = input.nextLine();
            if (inputStr != null) {
                break;
            } else {
                System.out.println("Invalid course ID. Please enter a valid course ID.");
            }
        }
        return inputStr;
    }
//Make sure the student name is valid
    private String getValidStringInput(Scanner input, String prompt) {
        String inputStr;
        while (true) {
            System.out.println(prompt);
            inputStr = input.nextLine();
            if (prompt.contains("name") && containsDigit(inputStr) == 1) {
                System.out.println("Invalid name. Try again...");
            } else {
                break;
            }
        }
        return inputStr;
    }

    private boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    private int containsDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                return 1;
            }
        }
        return 0;
    }

    public void run() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1 - Add Student");
            System.out.println("2 - Enroll in Course");
            System.out.println("3 - Remove Course");
            System.out.println("4 - Show Student Info");
            System.out.println("5 - Exit");
            
     boolean validChoice = false;
            int choice = 0;
            
            while (!validChoice) {
            System.out.print("Enter your choice: ");
            String choiceInput = input.nextLine();

            if (!isNumeric(choiceInput)) {
                System.out.println("Invalid input. Please enter a number.");
            } else {
                choice = Integer.parseInt(choiceInput);
                if (choice >= 1 && choice <= 5) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        }

            switch (choice) {
                case 1:
                    String studentId = getValidStudentIdInput(input, "Enter student ID:");
                    String studentName = getValidStringInput(input, "Enter student name:");
                    String studentPassword = getValidStringInput(input, "Enter password:");
                    addStudent(studentId, studentName, studentPassword);
                    break;

                case 2:
                    String enrollStudentId = getValidStudentIdInput(input, "Enter student ID:");
                    String enrollPassword = getValidStringInput(input, "Enter password:");

                    System.out.println("All courses: ");
                    for (int i = 0; i < courseCount; i++) {
                        System.out.println(courses[i].getCourseId() + ": " + courses[i].getCourseName() + " (" + courses[i].getCredits() + " credits)");
                    }

                    String enrollCourseId = getValidCourseIdInput(input, "Enter course ID:");
                    enrollCourse(enrollStudentId, enrollCourseId, enrollPassword);
                    break;

                case 3:
                    String removeStudentId = getValidStudentIdInput(input, "Enter student ID:");
                    String removePassword = getValidStringInput(input, "Enter password:");
                    String removeCourseId = getValidCourseIdInput(input, "Enter course ID:");
                    removeCourse(removeStudentId, removeCourseId, removePassword);
                    break;

                case 4:
                    String infoStudentId = getValidStudentIdInput(input, "Enter student ID:");
                    String infoPassword = getValidStringInput(input, "Enter password:");
                    showStudentInfo(infoStudentId, infoPassword);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    input.close();
                    return;

                default:      
            }
            }
        }
    }
