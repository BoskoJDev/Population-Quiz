package com.example.populationquiz.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.populationquiz.R
import com.example.populationquiz.databinding.FragmentSecondBinding
import com.example.populationquiz.sqlite.ModelPogledaTakmicara
import com.example.populationquiz.sqlite.Takmicar

class SecondFragment : Fragment()
{
    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private lateinit var modelPogleda: ModelPogledaTakmicara

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View
    {
        this.modelPogleda = ViewModelProvider(this)[ModelPogledaTakmicara::class.java]

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.KreirajNalog.setOnClickListener {
            val kontekst = this.requireContext()

            val nalog = binding.NoviNalog.text.toString()
            val sifra = binding.NovaSifra.text.toString()
            if (nalog.isBlank() || sifra.isBlank())
            {
                Toast.makeText(kontekst, "Ne ostavljaj prazna polja!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            this.modelPogleda.takmicari.observe(viewLifecycleOwner) { takmicari ->
                if (takmicari.isEmpty())
                {
                    this.modelPogleda.ubaciTakmicara(Takmicar(nalog, sifra, 0))
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    return@observe
                }

                for (takmicar in takmicari)
                {
                    if (nalog == takmicar.nalog)
                    {
                        Toast.makeText(kontekst, "Takav takmicar vec postoji!", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    this.modelPogleda.ubaciTakmicara(Takmicar(nalog, sifra, takmicar.highscore))
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }

            println("SSDD")
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}