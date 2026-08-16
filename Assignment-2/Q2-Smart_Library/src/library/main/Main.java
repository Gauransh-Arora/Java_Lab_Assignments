package library.main;

import library.model.Book;
import library.model.DigitalResource;
import library.model.LibraryResource;
import library.service.Printable;
import library.util.InputValidator;

public class Main {

    public static void main(String[] args) {

        LibraryResource.setLibraryName("Central University Library");

        LibraryResource[] resources = new LibraryResource[5];

        resources[0] = new Book(
                101,
                "The Alchemist",
                "Paulo Coelho"
        );

        resources[1] = new DigitalResource(
                102,
                "Clean Code",
                "Robert C. Martin"
        );

        resources[2] = new Book(
                103,
                "Java: The Complete Reference",
                "Herbert Schildt"
        );

        resources[3] = new DigitalResource(
                104,
                "Design Patterns",
                "Erich Gamma"
        );

        resources[4] = new Book(
                105,
                "Effective Java",
                "Joshua Bloch"
        );

        int[] overdueDays = {5, 10, 0, 7, 3};

        double totalFine = 0;

        System.out.println("==========================================");
        System.out.println("      " + LibraryResource.getLibraryName());
        System.out.println("==========================================");

        for (int i = 0; i < resources.length; i++) {

            LibraryResource resource = resources[i];

            System.out.println("\n------------------------------------------");

            if (!InputValidator.validateResourceId(
                    resource.getResourceId())) {

                System.out.println("Invalid Resource ID.");
                continue;
            }

            if (!InputValidator.validateFineDays(overdueDays[i])) {

                System.out.println("Invalid overdue days.");
                continue;
            }

            Printable printable = (Printable) resource;
            printable.printDetails();

            double fine = resource.calculateFine(overdueDays[i]);

            System.out.println("Overdue Days  : " + overdueDays[i]);
            System.out.println("Fine          : Rs. " + fine);

            totalFine += fine;
        }

        System.out.println("------------------------------------------");
        System.out.println("Total Fine    : Rs. " + totalFine);
        System.out.println("------------------------------------------");

        LibraryResource.displayTotalResources();
    }
}
