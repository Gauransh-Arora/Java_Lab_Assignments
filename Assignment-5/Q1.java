import java.util.Arrays;

public class Q1 {

    public static <T extends Number> double calculateSum(T[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0.0;
        }

        double sum = 0.0;
        for (T number : numbers) {
            if (number != null) {
                sum += number.doubleValue();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Integer[] integerArray = { 10, 25, 40, 55, 70 };
        double intSum = calculateSum(integerArray);
        System.out.println("Integer array: " + Arrays.toString(integerArray));
        System.out.printf("Sum: %.2f\n\n", intSum);

        Float[] floatArray = { 12.5f, 7.25f, 3.75f, 21.5f };
        double floatSum = calculateSum(floatArray);
        System.out.println("Float array: " + Arrays.toString(floatArray));
        System.out.printf("Sum: %.2f\n\n", floatSum);

        Double[] doubleArray = { 10.99, 20.49, 30.25, 45.75, 5.50 };
        double doubleSum = calculateSum(doubleArray);
        System.out.println("Double array: " + Arrays.toString(doubleArray));
        System.out.printf("Sum: %.2f\n", doubleSum);
    }
}
