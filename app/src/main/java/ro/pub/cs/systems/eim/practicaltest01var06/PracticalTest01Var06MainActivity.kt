package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var06MainActivity : AppCompatActivity() {
    val possible = listOf("1", "2", "3", "*")
    var score = 0
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val returnValue = data?.getIntExtra("resultKey", 0)

            Toast.makeText(this, "Gained: $returnValue", Toast.LENGTH_SHORT).show()

            if (returnValue != null) { score += returnValue }

            Toast.makeText(this, "Total score: $score", Toast.LENGTH_SHORT).show()

            if(score > 20) { startServiceIfConditionIsMet() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var06_main)

        val playButton = findViewById<Button>(R.id.playButton)
        val computeButton = findViewById<Button>(R.id.compute)

        val check1 = findViewById<CheckBox>(R.id.check1)
        val check2 = findViewById<CheckBox>(R.id.check2)
        val check3 = findViewById<CheckBox>(R.id.check3)

        val text1 = findViewById<TextView>(R.id.text1)
        val text2 = findViewById<TextView>(R.id.text2)
        val text3 = findViewById<TextView>(R.id.text3)

     playButton.setOnClickListener {
            val t1 = possible.random()
            val t2 = possible.random()
            val t3 = possible.random()

            if (!check1.isChecked) { text1.text = t1 }
            if (!check2.isChecked) { text2.text = t2 }
            if (!check3.isChecked) { text3.text = t3 }
        }

     computeButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01Var06SecondaryActivity::class.java)
            intent.putExtra("v1", text1.text.toString())
            intent.putExtra("v2", text2.text.toString())
            intent.putExtra("v3", text3.text.toString())
            intent.putExtra("checks", countCheckedCheckBoxes(check1, check2, check3))

            startForResult.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("scoreKey", score)
        outState.putString("text1Key", findViewById<TextView>(R.id.text1).text.toString())
        outState.putString("text2Key", findViewById<TextView>(R.id.text2).text.toString())
        outState.putString("text3Key", findViewById<TextView>(R.id.text3).text.toString())
        outState.putBoolean("check1Key", findViewById<CheckBox>(R.id.check1).isChecked)
        outState.putBoolean("check2Key", findViewById<CheckBox>(R.id.check2).isChecked)
        outState.putBoolean("check3Key", findViewById<CheckBox>(R.id.check3).isChecked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        score = savedInstanceState.getInt("scoreKey", 0)
        findViewById<TextView>(R.id.text1).text = savedInstanceState.getString("text1Key", "")
        findViewById<TextView>(R.id.text2).text = savedInstanceState.getString("text2Key", "")
        findViewById<TextView>(R.id.text3).text = savedInstanceState.getString("text3Key", "")

        findViewById<CheckBox>(R.id.check1).isChecked = savedInstanceState.getBoolean("check1Key", false)
        findViewById<CheckBox>(R.id.check2).isChecked = savedInstanceState.getBoolean("check2Key", false)
        findViewById<CheckBox>(R.id.check3).isChecked = savedInstanceState.getBoolean("check3Key", false)

        Toast.makeText(this, "Total score: $score", Toast.LENGTH_SHORT).show()
    }

    fun countCheckedCheckBoxes(checkBox1: CheckBox, checkBox2: CheckBox, checkBox3: CheckBox): Int {
        var count = 0
        if (checkBox1.isChecked) count++
        if (checkBox2.isChecked) count++
        if (checkBox3.isChecked) count++
        return count
    }
    private fun startServiceIfConditionIsMet() {
        val intent = Intent(applicationContext, PracticalTest01Var06Service::class.java).apply {
            putExtra("score", score.toString())
        }
        applicationContext.startService(intent)
    }

}
