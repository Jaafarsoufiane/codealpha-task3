import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class TravelItineraryPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> destinations = new ArrayList<>();
        ArrayList<String> dateRanges = new ArrayList<>();
        ArrayList<String> preferences = new ArrayList<>();

        int numDestinations;
        System.out.print("Enter the number of destinations: ");
        numDestinations = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numDestinations; i++) {
            System.out.print("Enter destination " + (i + 1) + ": ");
            destinations.add(scanner.nextLine());

            System.out.print("Enter dates for destination " + (i + 1) + " (e.g., 15/1/2025 20/1/2025 or X days): ");
            dateRanges.add(scanner.nextLine());

            System.out.print("Enter preferences for destination " + (i + 1) + ": ");
            preferences.add(scanner.nextLine());
        }

        // Generate itinerary
        System.out.println("\nYour Travel Itinerary:");
        for (int i = 0; i < numDestinations; i++) {
            System.out.println("\nDestination: " + destinations.get(i));
            System.out.println("Dates: " + dateRanges.get(i));
            System.out.println("Preferences: " + preferences.get(i));

            // Map integration using Google Maps API (replace with your API key)
            String destination = destinations.get(i);
            String apiKey = "YOUR_GOOGLE_MAPS_API_KEY";
            String mapUrl = "https://www.google.com/maps/embed/v1/place?key=" + apiKey + "&q=" + destination;
            System.out.println("Map: " + mapUrl);

            // Calculate days from date range
            int days = calculateDays(dateRanges.get(i));

            // Budget calculation with input validation
            double dailyBudget = 0;
            while (true) {
                try {
                    System.out.print("Enter your daily budget for " + destination + ": ");
                    dailyBudget = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number for your daily budget.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
            double totalBudget = dailyBudget * days;
            System.out.println("Estimated total budget for " + destination + ": $" + totalBudget);
        }
    }

    private static int calculateDays(String dateRange) {
        try {
            // Handle "X days" format
            if (dateRange.endsWith(" days")) {
                return Integer.parseInt(dateRange.split("\\s+")[0]);
            }

            // Handle date range format (e.g., "15/1/2025 20/1/2025")
            String[] dates = dateRange.split("\\s+");
            LocalDate startDate = LocalDate.parse(dates[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate endDate = LocalDate.parse(dates[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // Include both start and end dates
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter dates in a valid format (e.g., 15/1/2025 20/1/2025 or X days).");
            return 0;
        }
    }
}