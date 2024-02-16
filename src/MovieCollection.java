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
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String titleTerm = scanner.nextLine();
        ArrayList<Movie> moviesMatchingTerm = new ArrayList<Movie>();
        for (Movie movie : movies) {
            for (int i = 0; i < movie.getTitle().length() - titleTerm.length() + 1; i++) {
                if (movie.getTitle().substring(i, i + titleTerm.length()).equalsIgnoreCase(titleTerm)) {
                    moviesMatchingTerm.add(movie);
                    break;
                }
            }
        }
        sortMovies(moviesMatchingTerm);
        for (int i = 0; i < moviesMatchingTerm.size(); i++) {
            System.out.println((i + 1) + ". " + moviesMatchingTerm.get(i).getTitle());
        }
        displayMovieDetails(moviesMatchingTerm);
    }

    private void searchCast() {
        System.out.print("Enter a person to search for (first or last name): ");
        String castName = scanner.nextLine();
        ArrayList<String> moviesMatchingCastName = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] cast = movie.getCast().split("\\|");
            for (int i = 0; i < movie.getTitle().length() - castName.length() + 1; i++) {
                if (cast[i].substring(i, i + castName.length()).equalsIgnoreCase(castName)) {
                    boolean castFound = false;
                    for (String castNameInList : moviesMatchingCastName) {
                        if (castNameInList.equals(cast[i])) {
                            castFound = true;
                            break;
                        }
                    }
                    if (!castFound) {
                        moviesMatchingCastName.add(cast[i]);
                    }
                    break;
                }
            }
        }
        sortCast(moviesMatchingCastName);
        for (int i = 0; i < moviesMatchingCastName.size(); i++) {
            System.out.println((i + 1) + ". " + moviesMatchingCastName.get(i));
        }
        System.out.println("Which would you like to see all movies for? ");
        System.out.print("Enter number: ");
        int castNumber = scanner.nextInt();
        scanner.nextLine();
        String chosenCast = moviesMatchingCastName.get(castNumber - 1);
        ArrayList<Movie> moviesMatchingCast = new ArrayList<Movie>();
        for (Movie movie : movies) {
            String[] cast = movie.getCast().split("\\|");
            for (String individualCast : cast) {
                if (individualCast.equalsIgnoreCase(chosenCast)) {
                    moviesMatchingCast.add(movie);
                }
            }
        }
        sortMovies(moviesMatchingCast);
        for (int i = 0; i < moviesMatchingCast.size(); i++) {
            System.out.println((i + 1) + ". " + moviesMatchingCast.get(i).getTitle());
        }
        displayMovieDetails(moviesMatchingCast);
    }

    private static void sortMovies(ArrayList<Movie> movies) {
        for (int i = 1; i < movies.size(); i++) {
            int j = i;
            Movie movie = movies.get(i);
            while (j != 0 && movies.get(j - 1).getTitle().compareTo(movies.get(j).getTitle()) > 0) {
                movies.set(j, movies.get(j - 1));
                movies.set(j - 1, movie);
                j--;
            }
        }
    }

    public static void sortCast(ArrayList<String> castNames) {
        for (int i = 1; i < castNames.size(); i++) {
            int j = i;
            String element = castNames.get(i);
            while (j != 0 && castNames.get(j - 1).compareTo(castNames.get(j)) > 0) {
                castNames.set(j, castNames.get(j - 1));
                castNames.set(j - 1, element);
                j--;
            }
        }
    }


    private void displayMovieDetails(ArrayList<Movie> movieList) {
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int movieNumber = scanner.nextInt();
        scanner.nextLine();
        Movie chosenMovie = movieList.get(movieNumber - 1);
        System.out.println();
        System.out.println("Title: " + chosenMovie.getTitle());
        System.out.println("Runtime: " + chosenMovie.getRuntime());
        System.out.println("Directed by: " + chosenMovie.getDirector());
        System.out.println("Cast: " + chosenMovie.getCast());
        System.out.println("Overview: " + chosenMovie.getOverview());
        System.out.println("User rating: " + chosenMovie.getUserRating());
        System.out.println();
    }
}
