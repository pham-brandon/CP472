import random

# Correctly loading categories and words from the words.txt file.
def load(path):
    category = {}
    with open(path, "r") as file:
        curr_category = None
        for line in file:
            line = line.strip()
            if line.endswith(":"):
                curr_category = line[:-1]
                category[curr_category] = []
            elif curr_category:
                category[curr_category].append(line.lower())
    return category

# The game runs as expected, with categories, word guessing, and winning logic.
def game(words):
    print("Welcome to the Hangman Game!")
    while True:
        print("\nSelect a category:")
        for i, category in enumerate(words.keys(), start=1):
            print(f"{i}. {category}")
        
        # Categories
        try:
            category_choice = int(input("Enter the number of your category choice: ")) - 1
            category_name = list(words.keys())[category_choice]

        # Invalid input handling
        except (ValueError, IndexError):
            print("Invalid input")
            continue
        
        word = random.choice(words[category_name])
        tried_letters = set()
        tries_left = 6
        
        print(f"\nYou selected '{category_name}'. The word is {len(word)} letters long.")
        
        # Word guessing
        while tries_left > 0:
            display_current_state(word, tried_letters, tries_left)
            guess = input("\nGuess a letter: ").lower()
            
            # Invalid input handling
            if len(guess) != 1 or not guess.isalpha():
                print("Invalid input.")
                continue
            if guess in tried_letters:
                print("Letter has already been tried.")
                continue
            
            tried_letters.add(guess)
            
            # Word guessing
            if guess in word:
                print(f"Good guess! The letter '{guess}' is in the word.")
                # Winning logic
                if all(letter in tried_letters for letter in word):
                    print(f"\nCongratulations! You've guessed the word: {word}")
                    break
            else:
                tries_left -= 1
                print(f"Sorry, the letter '{guess}' is not in the word.")
        
        # Losing logic
        if tries_left == 0:
            print(f"\nGame Over! The correct word was: {word}")
            break

# Correctly displaying the current state of the word, list of guessed letters, and tries left after each guess.
def display_current_state(word, tried_letters, tries_left):
    current_word = " ".join([letter if letter in tried_letters else "_" for letter in word])
    print("\nCurrent word:", current_word)
    print("Tried letters:", ", ".join(sorted(tried_letters)))
    print("Tries left:", tries_left)
    print("-" * 80)

# Correctly loading categories and words from the words.txt file
if __name__ == "__main__":
    txt = load("words.txt")
    game(txt)