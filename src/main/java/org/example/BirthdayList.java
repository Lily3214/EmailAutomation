package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class BirthdayList {
    static final List<BirthdayDate> birthdayDates = new ArrayList<>();
    private static final String FILE_NAME = "Birthday.csv";
    private static final String DATE_FORMAT = "M/d/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static void main(String[] args){
        loadBirthdayDate(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Ask user for the option, menu runs until user choose to exit
        while (running) {
            System.out.println("Welcome to Birthday Printer App");
            System.out.println("Choose an option:");
            System.out.println("A) Print previous week from Sunday to Saturday birthday");
            System.out.println("B) Print previous week from Sunday to Saturday birthday");
            System.out.println("C) Print previous week from Sunday to Saturday Wedding Anniversary");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    printAllBirthdayLists();
                    break;
                case "B":
                    printPreviousWeekBirthdayLists();
                    break;
                case "C":
                    printPreviousWeekWeddingAnniversaryLists();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }

    // loadBirthdayDate loads birthday data from a CSV file
    public static void loadBirthdayDate(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int number = 1;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false; // Skip the first row (header)
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 17) {
                    String membershipEmail = parts[0];
                    int memberId = Integer.parseInt(parts[1]);
                    String relationshipToHeadOfHouseHold = parts[2];
                    String firstName = parts[3];
                    String lastName = parts[4];
                    String email = parts[5];
                    String cellNumber = parts[6];
                    String alternativeNumber = parts[7];
                    LocalDate dob = LocalDate.parse(parts[8], DateTimeFormatter.ofPattern(DATE_FORMAT));
                    int yearOfBirth = Integer.parseInt(parts[9]);
                    String gender = parts[10];
                    String maritalStatus = parts[11];
                    LocalDate weddingAnniversary = LocalDate.parse(parts[12], DateTimeFormatter.ofPattern(DATE_FORMAT));
                    int yearOfWedding = Integer.parseInt(parts[13]);
                    LocalDate baptismDate = LocalDate.parse(parts[14], DateTimeFormatter.ofPattern(DATE_FORMAT));
                    int yearOfBaptism = Integer.parseInt(parts[15]);
                    String notes = parts[16];

                    BirthdayDate birthdayDate = new BirthdayDate(membershipEmail, memberId, relationshipToHeadOfHouseHold, firstName, lastName, email, cellNumber, alternativeNumber, dob, yearOfBirth, gender, maritalStatus, weddingAnniversary, yearOfWedding, baptismDate, yearOfBaptism, notes);
                    birthdayDates.add(birthdayDate);
                }
            }
        } catch (IOException | DateTimeParseException e) {
            System.out.println("Error loading birthday data: " + e.getMessage());
        }
    }
    private static void printAllBirthdayLists() {
        System.out.println("Birthday List:");
        for (BirthdayDate birthdayDate : birthdayDates) {
            System.out.println(birthdayDate);
        }
    }

    public static void printPreviousWeekBirthdayLists() {
        LocalDate currentDate = LocalDate.now();
        // Calculate the date for the most recent Sunday
        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());

        // Calculate the date for the previous Saturday
        LocalDate previousSaturday = previousSunday.minusDays(1);

        // Calculate the date for the previous Sunday
        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

        System.out.println("Birthday List for the Previous Week from Saturday to Sunday:");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (BirthdayDate birthdayDate : birthdayDates) {
            LocalDate date = birthdayDate.getDob();
            if (!date.isBefore(startOfPreviousWeek) && !date.isAfter(previousSaturday)) {
                System.out.println("Membership Email: " + birthdayDate.getMembershipEmail() + "Member ID: " + birthdayDate.getMemberId() + "First Name: " + birthdayDate.getFirstName() + "Last Name: " + birthdayDate.getLastName() + "Email: " + birthdayDate.getEmail() + "Cell Number: " + birthdayDate.getCellNumber() + "Alternative Number: " + birthdayDate.getAlternativeNumber() + "Date of Birth: " + date.format(dateFormat) + "Year of Birth: " + birthdayDate.getYearOfBirth() + "Gender: " + birthdayDate.getGender() + "Marital Status: " + birthdayDate.getMaritalStatus() + "Wedding Anniversary: " + birthdayDate.getWeddingAnniversary().format(dateFormat) + "Year of Wedding: " + birthdayDate.getYearOfWedding() + "Baptism Date: " + birthdayDate.getBaptismDate().format(dateFormat) + "Year of Baptism: " + birthdayDate.getYearOfBaptism() + "Notes: " + birthdayDate.getNotes());
            }
        }
    }
    public static void printPreviousWeekWeddingAnniversaryLists() {
        LocalDate currentDate = LocalDate.now();
        // Calculate the date for the most recent Sunday
        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());

        // Calculate the date for the previous Saturday
        LocalDate previousSaturday = previousSunday.minusDays(1);

        // Calculate the date for the previous Sunday
        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

        System.out.println("Wedding Anniversary List for the Previous Week from Sunday to Saturday:");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (BirthdayDate birthdayDate : birthdayDates) {
            LocalDate date = birthdayDate.getWeddingAnniversary();
            if (!date.isBefore(startOfPreviousWeek) && !date.isAfter(previousSaturday)) {
                System.out.println("Membership Email: " + birthdayDate.getMembershipEmail() + "Member ID: " + birthdayDate.getMemberId() + "First Name: " + birthdayDate.getFirstName() + "Last Name: " + birthdayDate.getLastName() + "Email: " + birthdayDate.getEmail() + "Cell Number: " + birthdayDate.getCellNumber() + "Alternative Number: " + birthdayDate.getAlternativeNumber() + "Date of Birth: " + date.format(dateFormat) + "Year of Birth: " + birthdayDate.getYearOfBirth() + "Gender: " + birthdayDate.getGender() + "Marital Status: " + birthdayDate.getMaritalStatus() + "Wedding Anniversary: " + birthdayDate.getWeddingAnniversary().format(dateFormat) + "Year of Wedding: " + birthdayDate.getYearOfWedding() + "Baptism Date: " + birthdayDate.getBaptismDate().format(dateFormat) + "Year of Baptism: " + birthdayDate.getYearOfBaptism() + "Notes: " + birthdayDate.getNotes());
            }
        }
    }
}
