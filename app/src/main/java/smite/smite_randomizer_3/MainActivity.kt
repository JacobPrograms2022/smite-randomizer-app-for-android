package smite.smite_randomizer_3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readJson()
    }

    @SuppressLint("DiscouragedApi")
    private fun readJson(){
        lateinit var json: String

        println("running function")

        try {
            // opens json file
            val inputStream: InputStream = assets.open("smite_gods.json")
            // reads the json file
            json = inputStream.bufferedReader().use{it.readText()}

            // turns the json into an array
            val jsonArr = JSONArray(json)

            // accessing application views
            val godNameView = findViewById<TextView>(R.id.god_name)
            val godImage = findViewById<ImageView>(R.id.god_image)
            val randomizeButton = findViewById<Button>(R.id.randomize_button)

            // button initiates random number generation
            randomizeButton.setOnClickListener()
                { val rand = java.util.Random().nextInt(jsonArr.length())

                    // empty arrays
                    val jsonObj = jsonArr.getJSONObject(rand)
                    val godName = arrayListOf<String>()
                    val godRole = arrayListOf<String>()
                    val godDamage = arrayListOf<String>()
                    val godRange = arrayListOf<String>()
                    val godPantheon = arrayListOf<String>()
                    val godImg = arrayListOf<String>()

                    // arrays add their values from json file
                    godName.add(jsonObj.getString("name"))
                    godRole.add(jsonObj.getString("role"))
                    godDamage.add(jsonObj.getString("damage"))
                    godRange.add(jsonObj.getString("range"))
                    godPantheon.add(jsonObj.getString("pantheon"))
                    godImg.add(jsonObj.getString("img"))

                    // searches the drawable folder for file names matching that of the string_name
                    val resID: Int = resources.getIdentifier(godImg[0], "drawable",
                        packageName)
                    godImage.setImageResource(resID) // wants BitMap value

                    godNameView.text = godName[0] // sets textView text to display god name
                    //println(rand.toString())
                    //println(god_img.toString())
                }
        }
        // exception if json file is not found
        catch (e : IOException){
            println("exception")
        }
    }
}