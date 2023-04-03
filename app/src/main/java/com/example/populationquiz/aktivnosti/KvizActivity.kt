package com.example.populationquiz.aktivnosti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.populationquiz.Drzava
import com.example.populationquiz.R
import com.example.populationquiz.sqlite.Takmicar
import com.squareup.picasso.Picasso
import java.io.BufferedReader
import java.io.InputStreamReader

class KvizActivity : AppCompatActivity()
{
    private val listaZemalja = ArrayList<Drzava>()
    private lateinit var slikaPrethodneDrzave: ImageView
    private lateinit var slikaTrenutneDrzave: ImageView
    private lateinit var natpisPrethodneDrzave: TextView
    private lateinit var natpisTrenutneDrzave: TextView
    private lateinit var dugmePrethodneDrzave: Button
    private lateinit var dugmeTrenutneDrzave: Button

    private lateinit var pikaso: Picasso

    private lateinit var takmicar: Takmicar

    private var prethodnaDrzava: Drzava? = null
    private lateinit var trenutnaDrzava: Drzava

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kviz)

        this.takmicar = this.intent.getParcelableExtra("takmicar")!!

        this.pikaso = Picasso.get()
        this.izvuciDrzave()

        this.slikaPrethodneDrzave = findViewById(R.id.SlikaPrethodneDrzave)
        this.slikaTrenutneDrzave = findViewById(R.id.SlikaTrenutneDrzave)
        this.natpisPrethodneDrzave = findViewById(R.id.NatpisPrethodneDrzave)
        this.natpisTrenutneDrzave = findViewById(R.id.NatpisTrenutneDrzave)
        this.dugmePrethodneDrzave = findViewById(R.id.DugmePrethodneDrzave)
        this.dugmeTrenutneDrzave = findViewById(R.id.DugmeTrenutneDrzave)

        this.nasumicanOdabir()

        this.dugmePrethodneDrzave.setOnClickListener {
            if ((this.prethodnaDrzava?.populacija as Int) < this.trenutnaDrzava.populacija)
            {
                this.nasumicanOdabir()
                return@setOnClickListener
            }

            this.takmicar.poeni += 1
            this.nasumicanOdabir()
        }

        this.dugmeTrenutneDrzave.setOnClickListener {
            if ((this.prethodnaDrzava?.populacija as Int) > this.trenutnaDrzava.populacija)
            {
                this.nasumicanOdabir()
                return@setOnClickListener
            }

            this.takmicar.poeni += 1
            this.nasumicanOdabir()
        }
    }

    private fun nasumicanOdabir()
    {
        if (this.listaZemalja.isEmpty())
        {
            startActivity(Intent(this, HighscoreActivity::class.java).putExtra("takmicar", this.takmicar))
            return
        }

        this.title = "Current score: ${this.takmicar.poeni} | Highscore: ${this.takmicar.highscore}"

        if (this.prethodnaDrzava == null)
            this.prethodnaDrzava = this.listaZemalja.removeAt((0 until this.listaZemalja.size).random())
        else
            this.prethodnaDrzava = this.trenutnaDrzava

        this.trenutnaDrzava = this.listaZemalja.removeAt((0 until this.listaZemalja.size).random())

        pikaso.load(this.prethodnaDrzava?.urlSlikeZastave).into(this.slikaPrethodneDrzave)
        pikaso.load(this.trenutnaDrzava.urlSlikeZastave).into(this.slikaTrenutneDrzave)

        this.natpisPrethodneDrzave.text = this.prethodnaDrzava?.naziv
        this.natpisTrenutneDrzave.text = this.trenutnaDrzava.naziv

        this.dugmePrethodneDrzave.text = this.prethodnaDrzava?.naziv
        this.dugmeTrenutneDrzave.text = this.trenutnaDrzava.naziv
    }

    private fun izvuciDrzave()
    {
        var linija: String?
        val citac = BufferedReader(InputStreamReader(assets.open("population_csv.csv")))
        while (citac.readLine().also { linija = it } != null)
        {
            val str = linija?.split(',') as List<String>
            this.listaZemalja.add(Drzava(str[0], str[3].toInt(), "https://flagcdn.com/256x192/${str[1]}.png"))
        }
        citac.close()
    }
}