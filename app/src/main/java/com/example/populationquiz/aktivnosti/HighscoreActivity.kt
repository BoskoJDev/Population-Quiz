package com.example.populationquiz.aktivnosti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.populationquiz.R
import com.example.populationquiz.sqlite.ModelPogledaTakmicara
import com.example.populationquiz.sqlite.Takmicar

class HighscoreActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        val takmicar = this.intent.getParcelableExtra<Takmicar>("takmicar")!!
        if (takmicar.poeni > takmicar.highscore)
        {
            takmicar.highscore = takmicar.poeni
            ViewModelProvider(this)[ModelPogledaTakmicara::class.java].azurirajTakmicara(takmicar)
        }

        findViewById<TextView>(R.id.PrikazPoena).text = "Score: ${takmicar.poeni}"
        findViewById<TextView>(R.id.HighscorePrikaz).text = "Highscore: ${takmicar.highscore}"

        findViewById<Button>(R.id.DugmePonovnogPokusaja).setOnClickListener {
            val namera = Intent(this@HighscoreActivity, KvizActivity::class.java)
            namera.putExtra("takmicar", takmicar)
            startActivity(namera)
        }

        findViewById<Button>(R.id.DugmeIzlaska).setOnClickListener {
            startActivity(Intent(this@HighscoreActivity, MainActivity::class.java))
        }
    }
}