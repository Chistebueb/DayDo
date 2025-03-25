package com.example.daydo

object ChallengePicker {
    private val challenges = listOf(
        Challenge(
            name = "Contact an old friend",
            description = "Call or text someone you haven't heard from in a while.\nCatch up with them, maybe even plan an activity together.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Go for a walk",
            description = "Take a walk in your neighborhood or park and enjoy the fresh air.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Push ups",
            description = "Do 20 push ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Push ups",
            description = "Do 30 push ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Push ups",
            description = "Do 40 push ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Push ups (Hardcore)",
            description = "Do 80 push ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Sit ups",
            description = "Do 30 sit ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Sit ups",
            description = "Do 40 sit ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Sit ups (Hardcore)",
            description = "Do 80 sit ups.\n You can divide into multiple sessions",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Meditate",
            description = "Spend 10 minutes meditating to clear your mind and relax.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Write a journal entry",
            description = "Write down your thoughts about the day.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Jog",
            description = "Go for a 20 minute jog.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Jog",
            description = "Go for a 20 minute jog.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Ingwer shot",
            description = "Drink an ingwer shot.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Banana",
            description = "Peel a banana and enjoy it mindfully.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Ice bath (hardcore)",
            description = "Take an ice bath. Refresh, reset, and feel alive!",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Compliment 5 Strangers",
            description = "Go outside and complement 5 complete strangers",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Learn a New Word",
            description = "Find a new word and try using it in conversation.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Declutter",
            description = "Organize and declutter a small area in your home.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Call a Family Member",
            description = "Connect with a family member via phone or video call.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Read a Book Chapter",
            description = "Read at least one chapter of a book that interests you.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Random Act of Kindness",
            description = "Perform a small act of kindness for a stranger.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Write a Short Poem",
            description = "Compose a short poem or a few lines about your day.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Hydrate Well",
            description = "Drink at least 8 glasses of water throughout the day.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Practice Deep Breathing",
            description = "Spend 5 minutes practicing deep breathing exercises.",
            date = "",
            isCompleted = false
        ),
        Challenge(
            name = "Sketch Something",
            description = "Take 10 minutes to sketch or doodle something from your surroundings.",
            date = "",
            isCompleted = false
        )
    )

    fun getRandomChallenge(): Challenge {
        return challenges.random()
    }
}
