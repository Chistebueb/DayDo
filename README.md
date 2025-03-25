# dayDo

**dayDo** is a simple Android application designed to help you tackle daily challenges. The app presents you with a daily task, tracks your streak, and logs your progress, Sallowing you to see both completed and incomplete challenges.

## Features

- **Daily Challenge:** Displays a new challenge each day.
- **Completion Tracking:** Mark challenges as complete or incomplete.
- **Streak Counter:** Tracks your current streak and updates it based on your performance.
- **Progress Log:** Saves and displays the latest challenges (only the last 5 are shown) with the most recent at the top.
- **Simple Persistence:** Uses SharedPreferences to store challenge data, streaks, and progress.

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest version recommended)
- Android SDK (API level 21 or higher)
- Gradle

## Project Structure

- **MainActivity.kt:**  
  Displays today's challenge and handles the logic for generating new challenges or handling incomplete tasks across days.

- **ResultActivity.kt:**  
  Shows your progress including the current streak and the last five challenges. Incomplete challenges are marked with a red "(incomplete)" label.

- **Challenge.kt:**  
  Data class representing a challenge with attributes such as name, description, date, and completion status.

- **ChallengePicker.kt:**  
  Provides a daily challenge. Currently returns a fixed challenge, but can be extended to return random tasks.

## License

This project is open-source and available under the [MIT License](LICENSE).

