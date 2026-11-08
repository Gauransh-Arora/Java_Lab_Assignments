import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student {
    private int rollNo;
    private String name;
    private String course;
    private double marks;

    public Student(int rollNo, String name, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public String toFileString() {
        return rollNo + "," + name + "," + course + "," + marks;
    }

    public static Student fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            int rollNo = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String course = parts[2].trim();
            double marks = Double.parseDouble(parts[3].trim());
            return new Student(rollNo, name, course, marks);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Roll No: %-5d | Name: %-16s | Course: %-15s | Marks: %.2f",
                rollNo, name, course, marks);
    }
}

public class Q1 {

    public static String resolvePath(String fileName) {
        File dir = new File("Assignment-6");
        if (dir.exists() && dir.isDirectory()) {
            return "Assignment-6" + File.separator + fileName;
        }
        return fileName;
    }

    public static void writeStudentsToFile(String filePath, List<Student> students, boolean append) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, append);
            for (Student student : students) {
                writer.write(student.toFileString() + System.lineSeparator());
            }
            System.out.println("Successfully written " + students.size() + " student record(s) to: " + filePath);
        } catch (IOException e) {
            System.err.println("IOException occurred while writing to file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("FileWriter closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close FileWriter: " + e.getMessage());
                }
            }
        }
    }

    public static List<Student> readStudentsFromFile(String filePath) {
        List<Student> students = new ArrayList<>();
        FileReader reader = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found at path: " + filePath);
            }

            reader = new FileReader(file);
            StringBuilder content = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }

            String[] lines = content.toString().split("\\r?\\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Student student = Student.fromFileString(line);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
            System.out.println("Successfully read " + students.size() + " student record(s) from: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException occurred while reading from file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("FileReader closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close FileReader: " + e.getMessage());
                }
            }
        }
        return students;
    }

    public static boolean copyBinaryFile(String sourcePath, String destinationPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        long totalBytesCopied = 0;

        try {
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                throw new FileNotFoundException("Source binary file not found: " + sourcePath);
            }

            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destinationPath);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesCopied += bytesRead;
            }

            fos.flush();
            System.out.println("Binary file copied successfully.");
            System.out.println("Source: " + sourcePath);
            System.out.println("Destination: " + destinationPath);
            System.out.println("Total Bytes Copied: " + totalBytesCopied + " bytes");
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("IOException during binary file copy: " + e.getMessage());
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    System.out.println("FileInputStream closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close FileInputStream: " + e.getMessage());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                    System.out.println("FileOutputStream closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close FileOutputStream: " + e.getMessage());
                }
            }
        }
    }

    private static void createSampleBinaryFile(String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            byte[] dummyBinaryData = new byte[] {
                (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
                0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
                0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00,
                (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD,
                0x12, 0x34, 0x56, 0x78
            };
            fos.write(dummyBinaryData);
            System.out.println("Sample binary file created at: " + filePath + " (" + dummyBinaryData.length + " bytes)");
        } catch (IOException e) {
            System.err.println("Error creating sample binary file: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("PART 1: Character Streams (FileReader & FileWriter)");
        System.out.println("==================================================");

        String textFilePath = resolvePath("student_records.txt");

        List<Student> initialStudents = new ArrayList<>();
        initialStudents.add(new Student(101, "Gauransh Arora", "Computer Science", 92.5));
        initialStudents.add(new Student(102, "Aarav Sharma", "Data Science", 88.0));
        initialStudents.add(new Student(103, "Diya Patel", "Cyber Security", 95.0));

        System.out.println("\n[Writing initial records to text file]");
        writeStudentsToFile(textFilePath, initialStudents, false);

        System.out.println("\n[Reading records from text file]");
        List<Student> loadedStudents = readStudentsFromFile(textFilePath);
        System.out.println("\n--- Displaying Student Records ---");
        for (Student s : loadedStudents) {
            System.out.println(s);
        }

        System.out.println("\n[Appending a new record using FileWriter]");
        List<Student> additionalStudents = new ArrayList<>();
        additionalStudents.add(new Student(104, "Rohan Verma", "Software Engg", 84.5));
        writeStudentsToFile(textFilePath, additionalStudents, true);

        System.out.println("\n[Reading updated records from text file]");
        List<Student> updatedStudents = readStudentsFromFile(textFilePath);
        System.out.println("\n--- Displaying All Updated Student Records ---");
        for (Student s : updatedStudents) {
            System.out.println(s);
        }

        System.out.println("\n==================================================");
        System.out.println("PART 2: Byte Streams (FileInputStream & FileOutputStream)");
        System.out.println("==================================================");

        String binarySource = resolvePath("student_avatar.bin");
        String binaryDestination = resolvePath("student_avatar_copy.bin");

        createSampleBinaryFile(binarySource);

        System.out.println("\n[Copying binary file using FileInputStream and FileOutputStream]");
        boolean copySuccess = copyBinaryFile(binarySource, binaryDestination);
        System.out.println("Copy operation completed: " + copySuccess);

        System.out.println("\n==================================================");
        System.out.println("PART 3: Exception Handling Demonstration");
        System.out.println("==================================================");

        System.out.println("\n[Attempting to read non-existent text file]");
        readStudentsFromFile(resolvePath("non_existent_file.txt"));

        System.out.println("\n[Attempting to copy non-existent binary file]");
        copyBinaryFile(resolvePath("missing_image.bin"), resolvePath("missing_copy.bin"));
    }
}
