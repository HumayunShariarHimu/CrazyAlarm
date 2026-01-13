package com.example.smartalarm

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ChallengeActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var challengeDisplay: TextView
    private lateinit var userInput: EditText
    private lateinit var submitBtn: Button
    private var correctAnswer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        challengeDisplay = findViewById(R.id.challengeDisplay)
        userInput = findViewById(R.id.userInput)
        submitBtn = findViewById(R.id.submitBtn)

        startAlarmSound()
        loadRandomTask()

        submitBtn.setOnClickListener {
            if (userInput.text.toString().trim().lowercase() == correctAnswer.lowercase()) {
                stopAlarmAndExit()
            } else {
                Toast.makeText(this, "ভুল! আবার চেষ্টা করুন।", Toast.LENGTH_SHORT).show()
                if (currentTaskType == "typing") userInput.text.clear() // টাইপিং চ্যালেঞ্জের জন্য রিসেট
            }
        }
    }

    private var currentTaskType = ""

    private fun loadRandomTask() {
        val taskIndex = (1..11).random()
        when (taskIndex) {
            1 -> { // Math
                val a = (20..50).random(); val b = (10..30).random()
                correctAnswer = (a + b).toString()
                challengeDisplay.text = "$a + $b = ?"
            }
            3 -> { // Typing
                currentTaskType = "typing"
                correctAnswer = "Focus on your goals"
                challengeDisplay.text = "হুবহু টাইপ করুন:\n'$correctAnswer'"
            }
            4 -> { // Stroop Effect
                challengeDisplay.text = "লাল"
                challengeDisplay.setTextColor(Color.BLUE)
                correctAnswer = "নীল" 
                // নোট: এখানে বাটনের মাধ্যমে ইনপুট নিলে ভালো হয়
            }
            9 -> { // Shake (সংক্ষিপ্ত লজিক)
                challengeDisplay.text = "ফোনটি জোরে ঝাকান!"
                // সেন্সর লজিক এখানে যুক্ত হবে
            }
            else -> { // Default to Simple Math for other stubs
                correctAnswer = "5"
                challengeDisplay.text = "2 + 3 = ?"
            }
        }
    }

    private fun startAlarmSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_tone) // res/raw ফোল্ডারে ফাইল থাকতে হবে
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    private fun stopAlarmAndExit() {
        if (::mediaPlayer.isInitialized) mediaPlayer.stop()
        Toast.makeText(this, "অ্যালার্ম বন্ধ হয়েছে। শুভ সকাল!", Toast.LENGTH_LONG).show()
        finish()
    }
}
