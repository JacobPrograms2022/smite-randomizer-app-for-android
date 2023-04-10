package smite.smite_randomizer_3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
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
            val inputStream: InputStream = assets.open("smite_gods_2.json")
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
                {
                    val warSwitch = findViewById<Switch>(R.id.warrior_switch)
                    var randWarrior = warSwitch.isChecked

                    val assSwitch = findViewById<Switch>(R.id.assassin_switch)
                    var randAssassin = assSwitch.isChecked

                    val magSwitch = findViewById<Switch>(R.id.mage_switch)
                    var randMage = magSwitch.isChecked

                    val guaSwitch = findViewById<Switch>(R.id.guradian_switch)
                    var randGuardian = guaSwitch.isChecked

                    val hunSwitch = findViewById<Switch>(R.id.hunter_switch)
                    var randHunter = hunSwitch.isChecked

                    val allSwitch = findViewById<Switch>(R.id.all_switch)
                    var randAll = allSwitch.isChecked

                    allSwitch.setOnCheckedChangeListener { _, isChecked ->
                        if(isChecked){
                            warSwitch.isChecked = true
                            assSwitch.isChecked = true
                            magSwitch.isChecked = true
                            guaSwitch.isChecked = true
                            hunSwitch.isChecked = true
                        }
                    }
                    warSwitch.setOnCheckedChangeListener { _, isChecked ->
                        if (!(isChecked)) {
                            allSwitch.isChecked = false
                        }
                        if (!(warSwitch.isChecked) and !(assSwitch.isChecked) and
                            !(magSwitch.isChecked) and !(guaSwitch.isChecked) and
                            !(hunSwitch.isChecked)
                        ) {
                            allSwitch.isChecked = true
                        }
                    }

                    assSwitch.setOnCheckedChangeListener {_, isChecked ->
                    if (!(isChecked)) {
                            allSwitch.isChecked = false
                        }
                        if (!(warSwitch.isChecked) and !(assSwitch.isChecked) and
                            !(magSwitch.isChecked) and !(guaSwitch.isChecked) and
                            !(hunSwitch.isChecked)
                        ) {
                            allSwitch.isChecked = true
                        }
                    }

                    magSwitch.setOnCheckedChangeListener {_, isChecked ->
                    if (!(isChecked)) {
                            allSwitch.isChecked = false
                        }
                        if (!(warSwitch.isChecked) and !(assSwitch.isChecked) and
                            !(magSwitch.isChecked) and !(guaSwitch.isChecked) and
                            !(hunSwitch.isChecked)
                        ) {
                            allSwitch.isChecked = true
                        }
                    }

                    guaSwitch.setOnCheckedChangeListener {_, isChecked ->
                    if (!(isChecked)) {
                            allSwitch.isChecked = false
                        }
                        if (!(warSwitch.isChecked) and !(assSwitch.isChecked) and
                            !(magSwitch.isChecked) and !(guaSwitch.isChecked) and
                            !(hunSwitch.isChecked)
                        ) {
                            allSwitch.isChecked = true
                        }
                    }

                    hunSwitch.setOnCheckedChangeListener {_, isChecked ->
                    if (!(isChecked)) {
                            allSwitch.isChecked = false
                        }
                        if (!(warSwitch.isChecked) and !(assSwitch.isChecked) and
                            !(magSwitch.isChecked) and !(guaSwitch.isChecked) and
                            !(hunSwitch.isChecked)
                        ) {
                            allSwitch.isChecked = true
                        }
                    }

                    var filtJson = mutableListOf<JSONObject>()
                    for(i in 0 until jsonArr.length()) {
                        var godData = jsonArr.getJSONObject(i)
                        var classGod = godData.getString("class")
                        with(classGod) {
                            when {
                                // loops through JSON and finds data matching arguments
                                contains("Warrior") and randWarrior -> filtJson.add(godData)
                                contains("Assassin") and randAssassin -> filtJson.add(godData)
                                contains("Mage") and randMage -> filtJson.add(godData)
                                contains("Guardian") and randGuardian -> filtJson.add(godData)
                                contains("Hunter") and randHunter -> filtJson.add(godData)
                                else -> {
                                    // this seems to stop the error :P
                                }

                            }
                        }
                    }

                    var randNum = java.util.Random().nextInt(filtJson.size)
                    // above needs editing for custom random searches ie; class, damage type etc.
                    // empty arrays
                    val jsonObj = filtJson[randNum]
                    val godName = arrayListOf<String>()
                    val godRole = arrayListOf<String>()
                    val godDamage = arrayListOf<String>()
                    val godRange = arrayListOf<String>()
                    val godImg = arrayListOf<String>()

                    // arrays add their values from json file
                    godName.add(jsonObj.getString("name"))
                    godRole.add(jsonObj.getString("class"))
                    godDamage.add(jsonObj.getString("power type"))
                    godRange.add(jsonObj.getString("attack type"))
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