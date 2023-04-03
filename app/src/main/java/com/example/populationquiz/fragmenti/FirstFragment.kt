package com.example.populationquiz.fragmenti

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.populationquiz.R
import com.example.populationquiz.aktivnosti.KvizActivity
import com.example.populationquiz.databinding.FragmentFirstBinding
import com.example.populationquiz.sqlite.ModelPogledaTakmicara
import com.example.populationquiz.sqlite.Takmicar

class FirstFragment : Fragment()
{
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private lateinit var modelPogleda: ModelPogledaTakmicara

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View
    {
        this.modelPogleda = ViewModelProvider(this)[ModelPogledaTakmicara::class.java]

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.Login.setOnClickListener {
            val kontekst = this@FirstFragment.requireContext()

            val nalog = binding.Nalog.text.toString()
            val sifra = binding.Sifra.text.toString()
            if (nalog.isBlank() || sifra.isBlank())
            {
                Toast.makeText(kontekst, "Ne ostavljaj prazna polja!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            this.modelPogleda.takmicari.observe(viewLifecycleOwner) { takmicari ->
                for (takmicar in takmicari)
                {
                    if (nalog != takmicar.nalog || sifra != takmicar.sifra)
                        continue

                    val namera = Intent(this@FirstFragment.activity, KvizActivity::class.java)
                    namera.putExtra("takmicar", Takmicar(nalog, sifra, takmicar.highscore))
                    startActivity(namera)
                    return@observe
                }

                Toast.makeText(kontekst, "Takmicar nije pronadjen", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignUp.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}