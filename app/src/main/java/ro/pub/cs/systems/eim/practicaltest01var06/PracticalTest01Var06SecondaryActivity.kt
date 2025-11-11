package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var06SecondaryActivity : AppCompatActivity() {

    var gained = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var06_secondary)

        val okButton = findViewById<Button>(R.id.okButton)
        val gainedText = findViewById<TextView>(R.id.gainedText)

        val val1 = intent.getStringExtra("v1")
        val val2 = intent.getStringExtra("v2")
        val val3 = intent.getStringExtra("v3")
        val pressed = intent.getIntExtra("checks", 0)


        if (
            (val1 == val2 || val1 == "*" || val2 == "*") &&
            (val2 == val3 || val2 == "*" || val3 == "*") &&
            (val1 == val3 || val1 == "*" || val3 == "*")
        ) {
            when (pressed) {
                0 -> {
                    gainedText.text = "Gained: 100"
                    gained = 100
                }
                1 -> {
                    gainedText.text = "Gained: 50"
                    gained = 50
                }
                2 -> {
                    gainedText.text = "Gained: 10"
                    gained = 10
                }
            }
        } else {
            gainedText.text = "Gained: 0"
            gained = 0
        }


        okButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("resultKey", gained)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
