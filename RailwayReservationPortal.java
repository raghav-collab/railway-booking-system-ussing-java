import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class RailwayReservationPortal {

    // Global variable to track the total number of booked tickets across all types
    private static int totalBookedSeats = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Seat availability data: Array of integers representing available seats in 5 coaches (compartments)
        // For demonstration, we'll initialize with some random seat counts (0-20)
        int[] sleeperSeats = initializeSeats();
        int[] acSeats = initializeSeats();
        int[] generalSeats = initializeSeats();

        System.out.println("🚂 Welcome to the Railway Ticket Reservation Portal! 🎫");
        System.out.println("-------------------------------------------------------");

        do {
            // 1. Show ticket types using switch
            System.out.println("\n## 🎟️ Select a Ticket Type:");
            System.out.println("1. Sleeper Class");
            System.out.println("2. AC Class");
            System.out.println("3. General Class");
            System.out.println("4. Exit Portal");
            System.out.print("Enter your choice (1-4): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("\n❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                choice = 0;
                continue;
            }

            switch (choice) {
                case 1:
                    handleBooking("Sleeper Class", sleeperSeats, scanner);
                    break;
                case 2:
                    handleBooking("AC Class", acSeats, scanner);
                    break;
                case 3:
                    handleBooking("General Class", generalSeats, scanner);
                    break;
                case 4:
                    System.out.println("\n👋 Thank you for using the Railway Reservation Portal. Goodbye!");
                    System.out.println("📊 Total tickets booked today: " + totalBookedSeats);
                    break;
                default:
                    System.out.println("\n⚠️ Invalid choice. Please select from 1 to 4.");
            }
        } while (choice != 4);

        scanner.close();
    }

    /**
     * Initializes an array of seats with random values for demonstration.
     * @return An array of 5 integers representing seat availability in 5 coaches.
     */
    private static int[] initializeSeats() {
        int[] seats = new int[5];
        for (int i = 0; i < seats.length; i++) {
            // Random number between 0 and 20 (inclusive)
            seats[i] = ThreadLocalRandom.current().nextInt(0, 21);
        }
        return seats;
    }

    /**
     * Handles the seat availability check and booking process.
     * @param ticketType The name of the class (e.g., "Sleeper Class").
     * @param seats The array representing seats in coaches for this class.
     * @param scanner The Scanner object for user input.
     */
    private static void handleBooking(String ticketType, int[] seats, Scanner scanner) {
        System.out.println("\n### 🔎 Checking " + ticketType + " Seat Availability...");
        
        // 2. Use for-each loop to display availability and 5. Use continue
        int coachIndex = 1;
        int totalAvailableSeats = 0;
        
        System.out.println("\nAvailable Seats per Coach:");
        for (int available : seats) {
            // Simulate that the 3rd coach (index 2) is under maintenance
            if (coachIndex == 3) {
                // 5. Use continue to skip compartments under maintenance
                System.out.println("Coach " + coachIndex + ": 🚧 Under Maintenance (Skipping check)");
                coachIndex++;
                continue; 
            }
            
            System.out.println("Coach " + coachIndex + ": " + available + " seats");
            totalAvailableSeats += available;
            coachIndex++;
        }
        
        System.out.println("---");
        System.out.println("Total Available Seats in " + ticketType + ": **" + totalAvailableSeats + "**");
        System.out.println("---");


        // 3. Use if...else to allow/deny booking
        if (totalAvailableSeats > 0) {
            System.out.println("✅ Seats are available! Proceeding to booking...");
            
            System.out.print("How many tickets do you want to book (Max 6)? ");
            int requestedTickets;
            
            if (scanner.hasNextInt()) {
                requestedTickets = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("\n❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                return;
            }

            // 6. If user tries to book more than 6 tickets -> break reservation process.
            if (requestedTickets > 6) {
                System.out.println("\n🛑 Booking limit exceeded! You can book a maximum of 6 tickets at a time.");
                // We use 'return' here, which acts like a 'break' for the entire reservation attempt process
                // for this ticket type.
                return; 
            }

            if (requestedTickets <= totalAvailableSeats) {
                System.out.println("\n🌟 Successfully booked **" + requestedTickets + "** " + ticketType + " tickets!");
                
                // Update seats in the array and total count
                totalBookedSeats += requestedTickets;
                distributeBookings(seats, requestedTickets);
            } else {
                System.out.println("\n😔 Insufficient seats. You requested " + requestedTickets + " but only " + totalAvailableSeats + " are available.");
            }

        } else {
            // 3. Else -> “No seats available”
            System.out.println("🚫 No seats available in " + ticketType + ".");
        }
        
        // 4. Use for loop to count booked seats (in a specific class for demonstration)
        // In this context, we will count the *remaining* available seats after booking.
        System.out.println("\n--- Post-Booking Status Check ---");
        int remainingAvailable = 0;
        for (int i = 0; i < seats.length; i++) {
            remainingAvailable += seats[i];
        }
        int totalSeatsInClass = seats.length * 20; // Assuming max 20 seats per coach
        int bookedInThisClass = totalSeatsInClass - remainingAvailable;
        System.out.println("Total seats booked in " + ticketType + " since the start of the program: **" + (totalSeatsInClass - remainingAvailable) + "**");
        System.out.println("---------------------------------");
    }
    
    /**
     * Helper function to simulate reducing seats from coaches after a successful booking.
     * @param seats The array of available seats.
     * @param count The number of tickets booked.
     */
    private static void distributeBookings(int[] seats, int count) {
        for (int i = 0; i < seats.length && count > 0; i++) {
            // Skip the coach that is under maintenance (index 2 / Coach 3)
            if (i == 2) { 
                continue;
            }
            
            if (seats[i] >= count) {
                seats[i] -= count;
                count = 0;
            } else {
                count -= seats[i];
                seats[i] = 0;
            }
        }
    }
}