import java.util.*;

class Result<T extends Number> {
    private final String studentId;
    private final String studentName;
    private final List<T> marks;
    private final double maxMarksPerSubject;

    public Result(String studentId, String studentName, List<T> marks, double maxMarksPerSubject) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.studentName = Objects.requireNonNull(studentName, "Student Name cannot be null");

        if (maxMarksPerSubject <= 0) {
            throw new IllegalArgumentException("Max marks per subject must be greater than zero.");
        }
        this.maxMarksPerSubject = maxMarksPerSubject;
        this.marks = (marks != null) ? new ArrayList<>(marks) : null;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Optional<List<T>> getMarks() {
        return Optional.ofNullable(marks);
    }

    public Optional<Double> calculateAverage() {
        if (marks == null || marks.isEmpty()) {
            return Optional.empty();
        }
        double sum = 0.0;
        for (T mark : marks) {
            if (mark != null) {
                sum += mark.doubleValue();
            }
        }
        return Optional.of(sum / marks.size());
    }

    public Optional<Double> calculatePercentage() {
        return calculateAverage().map(avg -> (avg / maxMarksPerSubject) * 100.0);
    }

    public Optional<Boolean> hasPassed(double thresholdPercentage) {
        return calculatePercentage().map(percentage -> percentage >= thresholdPercentage);
    }

    public void displayResult(double thresholdPercentage) {
        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);

        Optional<List<T>> marksOptional = getMarks();

        if (marksOptional.isPresent() && !marksOptional.get().isEmpty()) {
            List<T> marksList = marksOptional.get();
            System.out.println("Marks: " + marksList);

            double average = calculateAverage().orElse(0.0);
            double percentage = calculatePercentage().orElse(0.0);
            boolean passed = hasPassed(thresholdPercentage).orElse(false);

            System.out.printf("Average: %.2f\n", average);
            System.out.printf("Percentage: %.2f%%\n", percentage);
            System.out.println("Status: " + (passed ? "PASSED" : "FAILED"));
        } else {
            System.out.println("Marks: Not Available");
            System.out.println("Status: Result Pending");
        }
    }
}

public class Q3 {

    public static void main(String[] args) {
        double passingThreshold = 40.0;

        Result<Integer> student1 = new Result<>(
                "102103001",
                "Aarav Sharma",
                Arrays.asList(85, 78, 92, 88, 76),
                100.0
        );
        student1.displayResult(passingThreshold);
        System.out.println();

        Result<Double> student2 = new Result<>(
                "102103002",
                "Diya Patel",
                Arrays.asList(68.5, 72.0, 59.5, 81.0),
                100.0
        );
        student2.displayResult(passingThreshold);
        System.out.println();

        Result<Float> student3 = new Result<>(
                "102103003",
                "Rohan Verma",
                Arrays.asList(32.5f, 38.0f, 29.5f, 41.0f),
                100.0
        );
        student3.displayResult(passingThreshold);
        System.out.println();

        Result<Integer> student4 = new Result<>(
                "102103004",
                "Simran Kaur",
                null,
                100.0
        );
        student4.displayResult(passingThreshold);
        System.out.println();

        try {
            Result<Integer> invalidStudent = new Result<>("102103005", null, Arrays.asList(90, 80), 100.0);
        } catch (NullPointerException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

        try {
            Result<Integer> invalidStudent = new Result<>(null, "Ananya", Arrays.asList(90, 80), 100.0);
        } catch (NullPointerException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }
}
