import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

class Event {
    private final String eventName;
    private final LocalDate eventDate;
    private final LocalTime startTime;
    private final Optional<String> venue;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    public Event(String eventName, LocalDate eventDate, LocalTime startTime, String venueName) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.venue = (venueName != null && !venueName.trim().isEmpty())
                ? Optional.of(venueName.trim())
                : Optional.empty();
    }

    public String getTimingStatus(LocalDate referenceDate) {
        if (eventDate.isEqual(referenceDate)) {
            return "Today";
        } else if (eventDate.isAfter(referenceDate)) {
            return "Future";
        } else {
            return "Past";
        }
    }

    public long getDaysRemaining(LocalDate referenceDate) {
        return ChronoUnit.DAYS.between(referenceDate, eventDate);
    }

    public LocalTime calculateEndTime() {
        return startTime.plusHours(2);
    }

    public void displayEventDetails() {
        LocalDate today = LocalDate.now();
        String status = getTimingStatus(today);
        long days = getDaysRemaining(today);
        LocalTime endTime = calculateEndTime();

        System.out.println("Event Name: " + eventName);
        System.out.println("Event Date: " + eventDate.format(DATE_FORMATTER) + " (" + eventDate + ")");
        System.out.println("Start Time: " + startTime.format(TIME_FORMATTER) + " (" + startTime + ")");
        System.out.println("End Time (2 Hours): " + endTime.format(TIME_FORMATTER) + " (" + endTime + ")");
        System.out.println("Event Status: " + status);

        if ("Today".equalsIgnoreCase(status)) {
            System.out.println("Days Remaining: 0 (Happening today)");
        } else if ("Future".equalsIgnoreCase(status)) {
            System.out.println("Days Remaining: " + days);
        } else {
            System.out.println("Days Remaining: Event was " + Math.abs(days) + " days ago");
        }

        System.out.println("Venue: " + venue.orElse("Venue not specified"));
    }
}

public class Q4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String name = "Tech Conference";
        LocalDate date = LocalDate.now().plusDays(10);
        LocalTime time = LocalTime.of(10, 0);
        String venue = null;

        if (scanner.hasNextLine()) {
            System.out.print("Enter event name: ");
            String inputName = scanner.nextLine().trim();
            if (!inputName.isEmpty()) name = inputName;

            System.out.print("Enter event date (YYYY-MM-DD): ");
            if (scanner.hasNextLine()) {
                String inputDate = scanner.nextLine().trim();
                if (!inputDate.isEmpty()) date = LocalDate.parse(inputDate);
            }

            System.out.print("Enter start time (HH:MM): ");
            if (scanner.hasNextLine()) {
                String inputTime = scanner.nextLine().trim();
                if (!inputTime.isEmpty()) time = LocalTime.parse(inputTime);
            }

            System.out.print("Enter venue (optional, press enter to skip): ");
            if (scanner.hasNextLine()) {
                String inputVenue = scanner.nextLine().trim();
                if (!inputVenue.isEmpty()) venue = inputVenue;
            }
            System.out.println();
        }

        Event event1 = new Event(name, date, time, venue);
        event1.displayEventDetails();
        System.out.println();

        Event eventPast = new Event("Past Workshop", LocalDate.now().minusDays(5), LocalTime.of(14, 0), null);
        eventPast.displayEventDetails();
    }
}
