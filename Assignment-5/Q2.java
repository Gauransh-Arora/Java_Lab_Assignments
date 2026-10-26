import java.util.NoSuchElementException;
import java.util.Optional;

public class Q2 {

    public static void main(String[] args) {
        String studentEmail = "gauransh.arora@thapar.edu";
        Optional<String> presentEmailOpt = Optional.of(studentEmail);
        System.out.println("Optional with email: " + presentEmailOpt);

        Optional<String> emptyEmailOpt = Optional.empty();
        System.out.println("Empty Optional: " + emptyEmailOpt);

        System.out.print("Email using ifPresent(): ");
        presentEmailOpt.ifPresent(System.out::println);

        emptyEmailOpt.ifPresent(System.out::println);

        String defaultEmail = "no-reply@thapar.edu";
        System.out.println("presentEmailOpt with orElse(): " + presentEmailOpt.orElse(defaultEmail));
        System.out.println("emptyEmailOpt with orElse(): " + emptyEmailOpt.orElse(defaultEmail));

        try {
            String email = presentEmailOpt.orElseThrow(() -> new NoSuchElementException("Email not found"));
            System.out.println("presentEmailOpt with orElseThrow(): " + email);
        } catch (NoSuchElementException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        try {
            String email = emptyEmailOpt.orElseThrow(() -> new NoSuchElementException("Email not found"));
            System.out.println("emptyEmailOpt with orElseThrow(): " + email);
        } catch (NoSuchElementException e) {
            System.out.println("emptyEmailOpt with orElseThrow() caught exception: " + e.getMessage());
        }
    }
}
