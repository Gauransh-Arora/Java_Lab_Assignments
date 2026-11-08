import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-18s | Department: %-16s | Salary: $%,10.2f",
                id, name, department, salary);
    }
}

public class Q3 {

    public static String resolvePath(String fileName) {
        File dir = new File("Assignment-6");
        if (dir.exists() && dir.isDirectory()) {
            return "Assignment-6" + File.separator + fileName;
        }
        return fileName;
    }

    public static void serializeEmployees(String filePath, List<Employee> employees) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        System.out.println("Serializing employee records to: " + filePath);
        try {
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);

            oos.writeInt(employees.size());
            for (Employee emp : employees) {
                oos.writeObject(emp);
            }

            oos.flush();
            System.out.println("Successfully serialized " + employees.size() + " employee object(s).");
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException during serialization: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException during serialization: " + e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    System.out.println("ObjectOutputStream closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close ObjectOutputStream: " + e.getMessage());
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

    public static List<Employee> deserializeEmployees(String filePath) {
        List<Employee> employees = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        System.out.println("Deserializing employee records from: " + filePath);
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("Serialization file not found: " + filePath);
            }

            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            int count = ois.readInt();
            System.out.println("Reading " + count + " employee record(s) from serialized stream...");

            for (int i = 0; i < count; i++) {
                Object obj = ois.readObject();
                if (obj instanceof Employee) {
                    employees.add((Employee) obj);
                }
            }

            System.out.println("Deserialization completed successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (EOFException e) {
            System.err.println("Reached unexpected end of file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: Employee class definition not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException during deserialization: " + e.getMessage());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                    System.out.println("ObjectInputStream closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close ObjectInputStream: " + e.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                    System.out.println("FileInputStream closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close FileInputStream: " + e.getMessage());
                }
            }
        }
        return employees;
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   QUESTION 3: OBJECT SERIALIZATION & DESERIALIZATION");
        System.out.println("==================================================");

        String serializedFile = resolvePath("employees.ser");

        List<Employee> originalEmployees = new ArrayList<>();
        originalEmployees.add(new Employee(1001, "Gauransh Arora", "Software Engg", 95000.00));
        originalEmployees.add(new Employee(1002, "Sophia Patel", "Human Resources", 72000.50));
        originalEmployees.add(new Employee(1003, "Ethan Reynolds", "Cloud Architecture", 115000.00));
        originalEmployees.add(new Employee(1004, "Priya Sharma", "Product Management", 102500.00));
        originalEmployees.add(new Employee(1005, "Liam Chen", "Cybersecurity", 98000.75));

        System.out.println("\n[Original Employee Records Before Serialization]:");
        for (Employee emp : originalEmployees) {
            System.out.println("  " + emp);
        }

        System.out.println("\n--------------------------------------------------");
        System.out.println("[Step 1: Serializing Employee Objects]");
        serializeEmployees(serializedFile, originalEmployees);

        System.out.println("\n--------------------------------------------------");
        System.out.println("[Step 2: Deserializing Employee Objects]");
        List<Employee> retrievedEmployees = deserializeEmployees(serializedFile);

        System.out.println("\n--------------------------------------------------");
        System.out.println("[Retrieved Employee Records After Deserialization]:");
        for (Employee emp : retrievedEmployees) {
            System.out.println("  " + emp);
        }

        System.out.println("\n--------------------------------------------------");
        System.out.println("[Verification Result]:");
        boolean matched = (originalEmployees.size() == retrievedEmployees.size());
        if (matched) {
            for (int i = 0; i < originalEmployees.size(); i++) {
                Employee orig = originalEmployees.get(i);
                Employee retr = retrievedEmployees.get(i);
                if (orig.getId() != retr.getId() ||
                    !orig.getName().equals(retr.getName()) ||
                    !orig.getDepartment().equals(retr.getDepartment()) ||
                    orig.getSalary() != retr.getSalary()) {
                    matched = false;
                    break;
                }
            }
        }
        System.out.println("Data integrity verified: " + (matched ? "PASSED (All records match exactly)" : "FAILED"));

        System.out.println("\n==================================================");
        System.out.println("        EXCEPTION HANDLING DEMONSTRATION          ");
        System.out.println("==================================================");
        System.out.println("[Attempting to deserialize from a non-existent file]");
        deserializeEmployees(resolvePath("non_existent_employees.ser"));
    }
}
