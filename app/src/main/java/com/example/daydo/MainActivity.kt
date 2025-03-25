package com.example.daydo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONObject
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    // Generated by ChatGPT
    companion object {
        private const val PREFS_NAME = "daydo_prefs"
        private const val KEY_STREAK = "current_streak"
        private const val KEY_CURRENT_CHALLENGE_COMPLETED = "challenge_completed"
        private const val KEY_LAST_DATE = "last_challenge_date"
        private const val KEY_COMPLETED_CHALLENGES = "completed_challenges"
        private const val KEY_CURRENT_CHALLENGE = "current_challenge"
    }

    private lateinit var dailyChallenge: Challenge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        window.statusBarColor = Color.parseColor("#191919")

        // Generated by ChatGPT until line 74
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val storedDate = prefs.getString(KEY_LAST_DATE, "") ?: ""
        val challengeCompleted = prefs.getBoolean(KEY_CURRENT_CHALLENGE_COMPLETED, false)
        val streak = prefs.getInt(KEY_STREAK, 0)
        val currentDate = getCurrentDateString()

        if (storedDate.isEmpty()) {
            dailyChallenge = ChallengePicker.getRandomChallenge().apply {
                date = currentDate
            }
            saveCurrentChallenge(prefs, dailyChallenge)
            prefs.edit()
                .putString(KEY_LAST_DATE, currentDate)
                .putBoolean(KEY_CURRENT_CHALLENGE_COMPLETED, false)
                .apply()
        } else if (storedDate == currentDate) {
            if (challengeCompleted) {
                startActivity(Intent(this, ResultActivity::class.java))
                finish()
                return
            } else {
                dailyChallenge = getCurrentChallenge(prefs)
                    ?: ChallengePicker.getRandomChallenge().apply { date = currentDate }
            }
        } else {
            val daysPassed = getDaysBetween(storedDate, currentDate)
            val previousChallenge = getCurrentChallenge(prefs)
            if (previousChallenge != null) {
                addChallengeToCompleted(prefs, previousChallenge)
            }
            when {
                daysPassed == 1 -> {
                    if (challengeCompleted) {
                        prefs.edit().putInt(KEY_STREAK, streak + 1).apply()
                    } else {
                        prefs.edit().putInt(KEY_STREAK, 0).apply()
                    }
                }
                daysPassed > 1 -> {
                    prefs.edit().putInt(KEY_STREAK, 0).apply()
                    for (i in 1 until daysPassed) {
                        val filler = ChallengePicker.getRandomChallenge().apply {
                            date = currentDate
                            isCompleted = false
                        }
                        addChallengeToCompleted(prefs, filler)
                    }
                }
            }

            dailyChallenge = ChallengePicker.getRandomChallenge().apply {
                date = currentDate
                isCompleted = false
            }
            saveCurrentChallenge(prefs, dailyChallenge)
            prefs.edit()
                .putString(KEY_LAST_DATE, currentDate)
                .putBoolean(KEY_CURRENT_CHALLENGE_COMPLETED, false)
                .apply()
        }

        // Build the UI for the challenge screen.
        val screenHeight = resources.displayMetrics.heightPixels
        val screenWidth = resources.displayMetrics.widthPixels

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#191919"))
            setPadding(dpToPx(16), dpToPx(46), dpToPx(16), dpToPx(16))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // Title
        val titleTextView = TextView(this).apply {
            text = "dayDo"
            textSize = 24f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { leftMargin = dpToPx(8) }
        }
        layout.addView(titleTextView)

        // Date display
        val dateTextView = TextView(this).apply {
            text = dailyChallenge.date
            textSize = 14f
            setTextColor(Color.parseColor("#777777"))
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { leftMargin = dpToPx(8) }
        }
        layout.addView(dateTextView)

        // Card for today's challenge
        val challengeCard = CardView(this).apply {
            radius = dpToPx(5).toFloat()
            setCardBackgroundColor(Color.parseColor("#232223"))
            cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (screenHeight * 0.4).toInt()
            ).apply {
                topMargin = dpToPx(56)
                bottomMargin = dpToPx(32)
            }
        }

        // Left colored bar on the card
        val leftCardLine = View(this).apply {
            setBackgroundColor(Color.parseColor("#BB86FC"))
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(6),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val cardTextLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(30), dpToPx(16), dpToPx(24), dpToPx(24))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // “today” label
        val todayLabel = TextView(this).apply {
            text = "today"
            textSize = 14f
            setTextColor(Color.parseColor("#777777"))
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        }
        cardTextLayout.addView(todayLabel)
        addVerticalSpace(cardTextLayout, 4)

        // Challenge title
        val challengeTitle = TextView(this).apply {
            text = dailyChallenge.name
            textSize = 20f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        }
        cardTextLayout.addView(challengeTitle)
        addVerticalSpace(cardTextLayout, 8)

        // Challenge description
        val challengeDescription = TextView(this).apply {
            text = dailyChallenge.description
            textSize = 16f
            setTextColor(Color.parseColor("#888888"))
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        }
        cardTextLayout.addView(challengeDescription)

        challengeCard.addView(leftCardLine)
        challengeCard.addView(cardTextLayout)
        layout.addView(challengeCard)

        // Complete button
        val completeButton = Button(this).apply {
            text = "complete"
            isAllCaps = false
            textSize = 16f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = dpToPx(50).toFloat()
                setStroke(dpToPx(1), Color.parseColor("#9B70CF"))
                setColor(Color.TRANSPARENT)
            }
            minHeight = dpToPx(60)
            layoutParams = LinearLayout.LayoutParams(
                (screenWidth * 0.7).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dpToPx(32)
            }
            setOnClickListener {
                dailyChallenge.isCompleted = true
                prefs.edit().putBoolean(KEY_CURRENT_CHALLENGE_COMPLETED, true).apply()
                saveCurrentChallenge(prefs, dailyChallenge)
                startActivity(Intent(this@MainActivity, ResultActivity::class.java))
                finish()
            }
        }
        layout.addView(completeButton)

        val line = View(this).apply {
            setBackgroundColor(Color.parseColor("#9B70CF"))
            layoutParams = LinearLayout.LayoutParams(
                (screenWidth * 0.35).toInt(),
                dpToPx(1)
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dpToPx(16)
            }
        }
        layout.addView(line)

        setContentView(layout)
    }

    private fun getCurrentDateString(): String {
        return LocalDate.now().toString()
    }


    private fun getDaysBetween(startDateStr: String, endDateStr: String): Int {
        if (startDateStr.isBlank() || endDateStr.isBlank()) return 0
        return try {
            val startDate = LocalDate.parse(startDateStr)
            val endDate = LocalDate.parse(endDateStr)
            ChronoUnit.DAYS.between(startDate, endDate).toInt()
        } catch (e: DateTimeParseException) {
            0
        }
    }

    // Generated by ChatGPT
    private fun addChallengeToCompleted(prefs: android.content.SharedPreferences, challenge: Challenge) {
        val jsonString = prefs.getString(KEY_COMPLETED_CHALLENGES, "[]")
        val jsonArray = JSONArray(jsonString)
        val challengeJson = JSONObject().apply {
            put("name", challenge.name)
            put("description", challenge.description)
            put("date", challenge.date)
            put("isCompleted", challenge.isCompleted)
        }
        jsonArray.put(challengeJson)
        prefs.edit().putString(KEY_COMPLETED_CHALLENGES, jsonArray.toString()).apply()
    }

    // Generated by ChatGPT
    private fun saveCurrentChallenge(prefs: android.content.SharedPreferences, challenge: Challenge) {
        val challengeJson = JSONObject().apply {
            put("name", challenge.name)
            put("description", challenge.description)
            put("date", challenge.date)
            put("isCompleted", challenge.isCompleted)
        }
        prefs.edit().putString(KEY_CURRENT_CHALLENGE, challengeJson.toString()).apply()
    }

    // Generated by ChatGPT
    private fun getCurrentChallenge(prefs: android.content.SharedPreferences): Challenge? {
        val jsonString = prefs.getString(KEY_CURRENT_CHALLENGE, "") ?: ""
        if (jsonString.isBlank()) return null
        return try {
            val json = JSONObject(jsonString)
            Challenge(
                name = json.optString("name", ""),
                description = json.optString("description", ""),
                date = json.optString("date", ""),
                isCompleted = json.optBoolean("isCompleted", false)
            )
        } catch (e: Exception) {
            null
        }
    }

    // Generated by ChatGPT
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    // Generated by ChatGPT
    private fun addVerticalSpace(parent: LinearLayout, heightDp: Int) {
        val space = View(this)
        space.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(heightDp)
        )
        parent.addView(space)
    }
}
