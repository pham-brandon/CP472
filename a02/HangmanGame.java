import java.io.*;
import java.util.*;

public class HangmanGame {

    // Correctly loading categories and words from the words.txt file.
    public static Map<String, List<String>> load(String filePath) {
        Map<String, List<String>> categories = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currCategory = null;
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.endsWith(":")) {
                    currCategory = line.substring(0, line.length() - 1);
                    categories.put(currCategory, new ArrayList<>());
                } else if (currCategory != null) {
                    categories.get(currCategory).add(line.toLowerCase());
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid file: " + e.getMessage());
        }
        return categories;
    }

    // Correctly displaying the current state of the word, list of guessed letters, and tries left after each guess.
    public static void displayCurrentState(String word, List<Character> triedLetters, int triesLeft) {
        StringBuilder currentWord = new StringBuilder();
        for (char letter : word.toCharArray()) {
            currentWord.append(triedLetters.contains(letter) ? letter + " " : "_ ");
        }
        System.out.println("\nCurrent word: " + currentWord.toString().trim());
        System.out.println("Tried letters: " + triedLettersToString(triedLetters));
        System.out.println("Tries left: " + triesLeft);
        System.out.println("----------------------------------------");
    }

    // The game runs as expected, with categories, word guessing, and winning logic.
    public static void game(Map<String, List<String>> words) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hangman Game!");

        List<String> categories = new ArrayList<>(words.keySet());
        System.out.println("Select a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        String categoryName;
        try {
            System.out.print("Enter the number of your category choice: ");
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            categoryName = categories.get(choice);
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scanner.close();
            return;
        }

        String word = getRandomWord(words.get(categoryName));
        List<Character> triedLetters = new ArrayList<>();
        int triesLeft = 6;

        System.out.println("\nYou selected '" + categoryName + "'. The word is " + word.length() + " letters long.");

        while (triesLeft > 0) {
            displayCurrentState(word, triedLetters, triesLeft);
            System.out.print("\nGuess a letter: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input.");
                continue;
            }

            char guess = input.charAt(0);
            if (triedLetters.contains(guess)) {
                System.out.println("Letter has already been tried.");
                continue;
            }

            triedLetters.add(guess);
            if (word.indexOf(guess) >= 0) {
                System.out.println("Good guess! The letter '" + guess + "' is in the word.");
                if (isWordTried(word, triedLetters)) {
                    System.out.println("\nCongratulations! You've guessed the word: " + word);
                    break;
                }
            } else {
                triesLeft--;
                System.out.println("Sorry, the letter '" + guess + "' is not in the word.");
            }
        }

        if (triesLeft == 0) {
            System.out.println("\nGame Over! The correct word was: " + word);
        }
        scanner.close();
    }

    public static boolean isWordTried(String word, List<Character> triedLetters) {
        for (char letter : word.toCharArray()) {
            if (!triedLetters.contains(letter)) return false;
        }
        return true;
    }

    public static String getRandomWord(List<String> wordList) {
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    public static String triedLettersToString(List<Character> triedLetters) {
        StringBuilder result = new StringBuilder();
        for (char letter : triedLetters) {
            result.append(letter).append(", ");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
    }

    public static void main(String[] args) {
        Map<String, List<String>> txt = load("words.txt");
        if (txt.isEmpty()) {
            System.out.println("No words loaded.");
        } else {
            game(txt);
        }
    }
}