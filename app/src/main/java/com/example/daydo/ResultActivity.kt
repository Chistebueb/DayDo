package com.example.daydo

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONArray
import org.json.JSONObject

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "daydo_prefs"
        private const val KEY_STREAK = "current_streak"
        private const val KEY_COMPLETED = "completed_challenges"

        // In-memory data, done partly by ChatGPT
        var currentStreak: Int = 0
        var completedChallenges: Array<Challenge?> = arrayOfNulls(5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        window.statusBarColor = Color.parseColor("#191919")

        loadData()

        val name = intent.getStringExtra("CHALLENGE_NAME") ?: ""
        val desc = intent.getStringExtra("CHALLENGE_DESCRIPTION") ?: ""
        val date = intent.getStringExtra("CHALLENGE_DATE") ?: ""

        if (name.isNotEmpty()) {
            val newChallenge = Challenge(name, desc, date)
            for (i in completedChallenges.size - 1 downTo 1) {
                completedChallenges[i] = completedChallenges[i - 1]
            }
            completedChallenges[0] = newChallenge
            currentStreak++
            saveData()
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#191919"))
            setPadding(dpToPx(16), dpToPx(46), dpToPx(16), dpToPx(16))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val titleTextView = TextView(this).apply {
            text = "your progress"
            textSize = 24f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        layout.addView(titleTextView)

        addVerticalSpace(layout, 16)

        val streakCard = CardView(this).apply {
            radius = dpToPx(5).toFloat()
            setCardBackgroundColor(Color.parseColor("#232223"))
            cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val streakHorizontalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val streakLeftBar = View(this).apply {
            setBackgroundColor(Color.parseColor("#BB86FC"))
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(6),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }
        val streakTextLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        val currentStreakLabel = TextView(this).apply {
            text = "current streak"
            textSize = 18f
            setTextColor(Color.parseColor("#888888"))
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        streakTextLayout.addView(currentStreakLabel)
        addVerticalSpace(streakTextLayout, 8)
        val streakNumber = TextView(this).apply {
            text = currentStreak.toString()
            textSize = 72f
            setTextColor(Color.parseColor("#FFFFFF"))
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
        }
        streakTextLayout.addView(streakNumber)
        streakHorizontalLayout.addView(streakLeftBar)
        streakHorizontalLayout.addView(streakTextLayout)
        streakCard.addView(streakHorizontalLayout)
        layout.addView(streakCard)

        addVerticalSpace(layout, 24)
        val recentTasksLabel = TextView(this).apply {
            text = "recent tasks"
            textSize = 14f
            setTextColor(Color.parseColor("#777777"))
            typeface = Typeface.create("sans-serif-regular", Typeface.NORMAL)
        }
        layout.addView(recentTasksLabel)

        addVerticalSpace(layout, 16)
        completedChallenges.forEach { challenge ->
            if (challenge != null) {
                val smallCard = CardView(this).apply {
                    radius = dpToPx(5).toFloat()
                    setCardBackgroundColor(Color.parseColor("#232223"))
                    cardElevation = 0f
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                layout.addView(smallCard)


                val smallHorizontalLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                smallCard.addView(smallHorizontalLayout)


                val smallLeftBar = View(this).apply {
                    setBackgroundColor(Color.parseColor("#9B70CF"))
                    layoutParams = LinearLayout.LayoutParams(
                        dpToPx(3),
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
                smallHorizontalLayout.addView(smallLeftBar)


                val smallTextLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }
                smallHorizontalLayout.addView(smallTextLayout)


                val taskTextView = TextView(this).apply {
                    text = challenge.name
                    textSize = 16f
                    setTextColor(Color.parseColor("#888888"))
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                }
                smallTextLayout.addView(taskTextView)


                val dateTextView = TextView(this).apply {
                    text = challenge.date
                    textSize = 12f
                    setTextColor(Color.parseColor("#555555"))
                    typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
                }
                smallTextLayout.addView(dateTextView)

                addVerticalSpace(layout, 8)
            }
        }

        setContentView(layout)
    }

    // generated by ChatGPT
    private fun loadData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        currentStreak = prefs.getInt(KEY_STREAK, 0)
        val jsonString = prefs.getString(KEY_COMPLETED, "") ?: ""
        if (jsonString.isNotEmpty()) {
            try {
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val challengeJson = jsonArray.optJSONObject(i)
                    if (challengeJson != null) {
                        completedChallenges[i] = challengeFromJson(challengeJson)
                    } else {
                        completedChallenges[i] = null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                completedChallenges = arrayOfNulls(5)
            }
        }
    }

    // generated by ChatGPT
    private fun saveData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_STREAK, currentStreak)
        val jsonArray = JSONArray()
        completedChallenges.forEach { challenge ->
            if (challenge != null) {
                jsonArray.put(challengeToJson(challenge))
            }
        }
        editor.putString(KEY_COMPLETED, jsonArray.toString())
        editor.apply()
    }

    private fun challengeToJson(challenge: Challenge): JSONObject {
        return JSONObject().apply {
            put("name", challenge.name)
            put("description", challenge.description)
            put("date", challenge.date)
        }
    }

    private fun challengeFromJson(json: JSONObject): Challenge {
        val name = json.optString("name", "")
        val description = json.optString("description", "")
        val date = json.optString("date", "")
        return Challenge(name, description, date)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun addVerticalSpace(parent: LinearLayout, heightDp: Int) {
        val space = View(this)
        space.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(heightDp)
        )
        parent.addView(space)
    }
}
