import java.util.*;

class Course {
    private String courseCode;
    private String courseName;
    private int capacity;
    private Set<String> enrolledStudents;
    private List<String> prerequisites;
    private Queue<String> waitlist;

    public Course(String courseCode, String courseName, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = capacity;
        this.enrolledStudents = new HashSet<>();
        this.prerequisites = new ArrayList<>();
        this.waitlist = new LinkedList<>();
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCapacity() { return capacity; }
    public int getEnrolledCount() { return enrolledStudents.size(); }
    public int getAvailableSlots() { return capacity - enrolledStudents.size(); }
    public Set<String> getEnrolledStudents() { return Collections.unmodifiableSet(enrolledStudents); }
    public List<String> getPrerequisites() { return Collections.unmodifiableList(prerequisites); }
    public Queue<String> getWaitlist() { return waitlist; }

    public void addPrerequisite(String prerequisiteCode) {
        if (!prerequisites.contains(prerequisiteCode)) {
            prerequisites.add(prerequisiteCode);
        }
    }

    public boolean isStudentEnrolled(String studentId) {
        return enrolledStudents.contains(studentId);
    }

    public boolean isStudentOnWaitlist(String studentId) {
        return waitlist.contains(studentId);
    }

    public boolean canEnroll(String studentId) {
        return !enrolledStudents.contains(studentId) && enrolledStudents.size() < capacity;
    }

    public boolean enrollStudent(String studentId) {
        if (canEnroll(studentId)) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }

    public void addToWaitlist(String studentId) {
        if (!enrolledStudents.contains(studentId) && !waitlist.contains(studentId)) {
            waitlist.offer(studentId);
        }
    }

    public String promoteFromWaitlist() {
        if (!waitlist.isEmpty() && enrolledStudents.size() < capacity) {
            String studentId = waitlist.poll();
            enrolledStudents.add(studentId);
            return studentId;
        }
        return null;
    }

    public boolean dropStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }

    public void displayCourseInfo() {
        System.out.println("\n--- Course: " + courseCode + " - " + courseName + " ---");
        System.out.println("Capacity: " + capacity + " | Enrolled: " + enrolledStudents.size() + " | Available: " + getAvailableSlots());
        System.out.println("Prerequisites: " + (prerequisites.isEmpty() ? "None" : String.join(", ", prerequisites)));
        System.out.println("Enrolled Students: " + (enrolledStudents.isEmpty() ? "None" : String.join(", ", enrolledStudents)));
        System.out.println("Waitlist: " + (waitlist.isEmpty() ? "Empty" : String.join(" -> ", waitlist)));
    }
}

public class Q1 {
    private static Map<String, Course> courseCatalog = new HashMap<>();
    private static Map<String, Set<String>> studentRegistrations = new HashMap<>();

    public static void main(String[] args) {
        initializeCourses();
        demonstrateRegistrationSystem();
    }

    private static void initializeCourses() {
        Course cs101 = new Course("CS101", "Introduction to Programming", 3);
        courseCatalog.put("CS101", cs101);

        Course cs201 = new Course("CS201", "Data Structures", 2);
        cs201.addPrerequisite("CS101");
        courseCatalog.put("CS201", cs201);

        Course cs301 = new Course("CS301", "Algorithms", 2);
        cs301.addPrerequisite("CS201");
        cs301.addPrerequisite("MATH200");
        courseCatalog.put("CS301", cs301);

        Course math100 = new Course("MATH100", "Calculus I", 4);
        courseCatalog.put("MATH100", math100);

        Course math200 = new Course("MATH200", "Discrete Mathematics", 3);
        math200.addPrerequisite("MATH100");
        courseCatalog.put("MATH200", math200);

        Course cs401 = new Course("CS401", "Operating Systems", 2);
        cs401.addPrerequisite("CS201");
        cs401.addPrerequisite("CS301");
        courseCatalog.put("CS401", cs401);
    }

    private static void demonstrateRegistrationSystem() {
        System.out.println("=== Course Registration Portal Demo ===");
        displayAllCourses();

        System.out.println("\n=== Registration Attempts ===");

        registerStudent("S001", "CS101");
        registerStudent("S002", "CS101");
        registerStudent("S003", "CS101");
        registerStudent("S004", "CS101");
        registerStudent("S005", "CS101");

        registerStudent("S001", "MATH100");
        registerStudent("S002", "MATH100");
        registerStudent("S003", "MATH100");

        registerStudent("S001", "CS201");
        registerStudent("S002", "CS201");
        registerStudent("S003", "CS201");

        registerStudent("S001", "CS301");
        registerStudent("S002", "CS301");

        registerStudent("S001", "CS401");

        System.out.println("\n=== After Initial Registrations ===");
        displayAllCourses();

        System.out.println("\n=== Testing Prerequisite Check ===");
        registerStudent("S004", "CS201");
        registerStudent("S004", "CS101");

        System.out.println("\n=== Student S001 drops CS201 ===");
        dropStudent("S001", "CS201");
        displayCourse("CS201");

        System.out.println("\n=== Student S003 drops CS101 ===");
        dropStudent("S003", "CS101");
        displayCourse("CS101");

        System.out.println("\n=== Final State ===");
        displayAllCourses();

        System.out.println("\n=== Student Schedules ===");
        displayStudentSchedule("S001");
        displayStudentSchedule("S002");
        displayStudentSchedule("S003");
        displayStudentSchedule("S004");
        displayStudentSchedule("S005");
    }

    private static boolean checkPrerequisites(String studentId, String courseCode) {
        Course course = courseCatalog.get(courseCode);
        if (course == null) return false;

        Set<String> studentCourses = studentRegistrations.getOrDefault(studentId, Collections.emptySet());

        for (String prereq : course.getPrerequisites()) {
            if (!studentCourses.contains(prereq)) {
                System.out.println("Prerequisite not met: " + prereq + " required for " + courseCode);
                return false;
            }
        }
        return true;
    }

    public static void registerStudent(String studentId, String courseCode) {
        Course course = courseCatalog.get(courseCode);
        if (course == null) {
            System.out.println("Error: Course " + courseCode + " not found.");
            return;
        }

        if (!checkPrerequisites(studentId, courseCode)) {
            System.out.println("Registration Failed: " + studentId + " -> " + courseCode + " (Prerequisites not satisfied)");
            return;
        }

        if (course.enrollStudent(studentId)) {
            studentRegistrations.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseCode);
            System.out.println("Registered: " + studentId + " -> " + courseCode);
        } else if (course.isStudentEnrolled(studentId)) {
            System.out.println("Already Enrolled: " + studentId + " -> " + courseCode);
        } else if (course.isStudentOnWaitlist(studentId)) {
            System.out.println("Already Waitlisted: " + studentId + " -> " + courseCode);
        } else {
            course.addToWaitlist(studentId);
            System.out.println("Waitlisted: " + studentId + " -> " + courseCode + " (Position: " + course.getWaitlist().size() + ")");
        }
    }

    public static void dropStudent(String studentId, String courseCode) {
        Course course = courseCatalog.get(courseCode);
        if (course == null) {
            System.out.println("Error: Course " + courseCode + " not found.");
            return;
        }

        if (course.dropStudent(studentId)) {
            Set<String> studentCourses = studentRegistrations.get(studentId);
            if (studentCourses != null) {
                studentCourses.remove(courseCode);
            }
            System.out.println("Dropped: " + studentId + " -> " + courseCode);

            String promoted = course.promoteFromWaitlist();
            if (promoted != null) {
                studentRegistrations.computeIfAbsent(promoted, k -> new HashSet<>()).add(courseCode);
                System.out.println("Promoted from waitlist: " + promoted + " -> " + courseCode);
            }
        } else {
            System.out.println("Not Enrolled: " + studentId + " is not enrolled in " + courseCode);
        }
    }

    private static void displayAllCourses() {
        System.out.println("\n=== Course Catalog ===");
        for (Course course : courseCatalog.values()) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseName() +
                " | Capacity: " + course.getCapacity() +
                " | Enrolled: " + course.getEnrolledCount() +
                " | Waitlist: " + course.getWaitlist().size());
        }
    }

    private static void displayCourse(String courseCode) {
        Course course = courseCatalog.get(courseCode);
        if (course != null) {
            course.displayCourseInfo();
        }
    }

    private static void displayStudentSchedule(String studentId) {
        Set<String> courses = studentRegistrations.get(studentId);
        System.out.println(studentId + ": " + (courses == null || courses.isEmpty() ? "No courses" : String.join(", ", courses)));
    }
}