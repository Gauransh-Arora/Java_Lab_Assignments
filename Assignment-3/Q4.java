import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numSubjects = 3;
        double[] marks = new double[numSubjects];
        double total = 0;

        System.out.println("Enter marks for " + numSubjects + " subjects (0-100):");

        try {
            for (int i = 0; i < numSubjects; i++) {
                System.out.print("Subject " + (i + 1) + ": ");
                if (!scanner.hasNextDouble()) {
                    throw new InvalidMarksException("Invalid input type. Please enter numbers only.");
                }
                marks[i] = scanner.nextDouble();

                if (marks[i] < 0 || marks[i] > 100) {
                    throw new InvalidMarksException("Marks must be between 0 and 100. Entered: " + marks[i]);
                }
                total += marks[i];
            }

            double percentage = total / numSubjects;
            String grade;

            if (percentage >= 90) {
                grade = "A";
            } else if (percentage >= 80) {
                grade = "B";
            } else if (percentage >= 70) {
                grade = "C";
            } else if (percentage >= 60) {
                grade = "D";
            } else {
                grade = "F";
            }

            System.out.println("\n--- Result ---");
            System.out.println("Total Marks: " + total);
            System.out.println("Percentage: " + percentage + "%");
            System.out.println("Grade: " + grade);

        } catch (InvalidMarksException e) {
            System.out.println("Error Processing Result: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
