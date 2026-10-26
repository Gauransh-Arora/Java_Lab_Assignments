import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

class GenericUtils {

    public static <T> boolean isEqual(T a, T b) {
        return Objects.equals(a, b);
    }

    public static <T> T getDefault(Optional<T> value, T defaultValue) {
        return (value != null) ? value.orElse(defaultValue) : defaultValue;
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return (a.compareTo(b) >= 0) ? a : b;
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }
}

class StudentRecord implements Comparable<StudentRecord> {
    private final String id;
    private final String name;
    private final double score;

    public StudentRecord(String id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getScore() { return score; }

    @Override
    public int compareTo(StudentRecord other) {
        if (other == null) return 1;
        return Double.compare(this.score, other.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentRecord)) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + id + ", Score: " + score + ")";
    }
}

public class Q5 {

    public static void main(String[] args) {
        System.out.println("String Demonstration:");
        String str1 = "Java";
        String str2 = "Java";
        String str3 = "Python";
        System.out.println("isEqual(\"Java\", \"Java\"): " + GenericUtils.isEqual(str1, str2));
        System.out.println("isEqual(\"Java\", \"Python\"): " + GenericUtils.isEqual(str1, str3));
        System.out.println("isEqual(null, null): " + GenericUtils.isEqual(null, null));
        System.out.println("getDefault(present, \"Off\"): " + GenericUtils.getDefault(Optional.of("Connected"), "Off"));
        System.out.println("getDefault(empty, \"Off\"): " + GenericUtils.getDefault(Optional.empty(), "Off"));
        System.out.println("max(\"Apple\", \"Banana\"): " + GenericUtils.max("Apple", "Banana"));
        System.out.println("isNull(\"Java\"): " + GenericUtils.isNull("Java"));
        System.out.println("isNull(null): " + GenericUtils.isNull(null));
        System.out.println();

        System.out.println("Integer Demonstration:");
        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = 250;
        System.out.println("isEqual(100, 100): " + GenericUtils.isEqual(num1, num2));
        System.out.println("isEqual(100, 250): " + GenericUtils.isEqual(num1, num3));
        System.out.println("getDefault(present, 0): " + GenericUtils.getDefault(Optional.of(42), 0));
        System.out.println("getDefault(empty, 0): " + GenericUtils.getDefault(Optional.empty(), 0));
        System.out.println("max(150, 99): " + GenericUtils.max(150, 99));
        System.out.println("isNull(100): " + GenericUtils.isNull(num1));
        System.out.println("isNull(null): " + GenericUtils.isNull((Integer) null));
        System.out.println();

        System.out.println("LocalDate Demonstration:");
        LocalDate d1 = LocalDate.of(2026, 1, 15);
        LocalDate d2 = LocalDate.of(2026, 1, 15);
        LocalDate d3 = LocalDate.of(2026, 12, 31);
        System.out.println("isEqual(2026-01-15, 2026-01-15): " + GenericUtils.isEqual(d1, d2));
        System.out.println("isEqual(2026-01-15, 2026-12-31): " + GenericUtils.isEqual(d1, d3));
        System.out.println("max(2026-01-15, 2026-12-31): " + GenericUtils.max(d1, d3));
        System.out.println("getDefault(emptyDate, today): " + GenericUtils.getDefault(Optional.empty(), LocalDate.now()));
        System.out.println("isNull(today): " + GenericUtils.isNull(LocalDate.now()));
        System.out.println();

        System.out.println("Custom Object Demonstration:");
        StudentRecord s1 = new StudentRecord("S101", "Aarav", 89.5);
        StudentRecord s2 = new StudentRecord("S101", "Aarav", 89.5);
        StudentRecord s3 = new StudentRecord("S102", "Ishaan", 95.0);
        System.out.println("Student 1: " + s1);
        System.out.println("Student 2: " + s2);
        System.out.println("Student 3: " + s3);
        System.out.println("isEqual(s1, s2): " + GenericUtils.isEqual(s1, s2));
        System.out.println("isEqual(s1, s3): " + GenericUtils.isEqual(s1, s3));
        System.out.println("max(s1, s3): " + GenericUtils.max(s1, s3));
        StudentRecord defaultStudent = new StudentRecord("S000", "Guest", 0.0);
        System.out.println("getDefault(empty, default): " + GenericUtils.getDefault(Optional.empty(), defaultStudent));
        System.out.println("isNull(s1): " + GenericUtils.isNull(s1));
        System.out.println("isNull(null): " + GenericUtils.isNull((StudentRecord) null));
    }
}
