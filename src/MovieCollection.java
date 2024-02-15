import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    ArrayList<Movie> movies;
    Scanner scanner;
    public MovieCollection() {
        movies = new ArrayList<Movie>();
        scanner = new Scanner(System.in);
        readData();
        start();
    }

    private void readData() {
        try {
            File myFile = new File("src//movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            boolean firstLineChecked = false;
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                if (firstLineChecked) {
                    Movie item = new Movie(splitData[0], splitData[1], splitData[2], splitData[3], Integer.parseInt(splitData[4]), Double.parseDouble(splitData[5]));
                    movies.add(item);
                } else {
                    firstLineChecked = true;
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void start() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                //searchTitles();
            } else if (menuOption.equals("c")) {
                //searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }
}
