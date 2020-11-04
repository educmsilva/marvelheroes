package com.example.marvelheroes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.ui.main.adapter.SuperHeroesListAdapter
import com.example.marvelheroes.util.model.Result
import kotlinx.android.synthetic.main.fragment_super_heroes.*

class SuperHeroesFragment : Fragment() {

    private val viewModel: SuperHeroesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_super_heroes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        configureButtons()
        getData()
    }

    private fun getData() {
        viewModel.getSuperHeroes(20, viewModel.getOffSet())
    }

    private fun initObservers() {
        observerSuperHeroes()
        observerErrorMessage()
    }

    private fun observerSuperHeroes() {
        viewModel.getSuperHeroesLive()
            .observe(viewLifecycleOwner, Observer(this::updateSuperHeroes))
    }

    private fun updateSuperHeroes(superHeroes: List<Result>?) {
        if (superHeroes != null) {
            configureList(superHeroes)
        }
    }

    private fun observerErrorMessage() {
        viewModel.getErrorMessageLive()
            .observe(viewLifecycleOwner, Observer(this::updateErrorMessage))
    }

    private fun updateErrorMessage(errorMessage: String?) {
        if (errorMessage != null) {
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun configureList(superHeroes: List<Result>) {
        val recyclerView = super_heroes_list
        recyclerView.adapter =
            context?.let {
                SuperHeroesListAdapter(superHeroes, it) { superHero, position ->
                    val superHeroThumb =
                        superHero.thumbnail.path + "." + superHero.thumbnail.extension
                    getParentFragmentManager().let { it1 ->
                        SuperHeroFragment.newInstance(
                            superHeroThumb,
                            superHero.name,
                            superHero.description
                        ).show(
                            it1, SuperHeroFragment.TAG
                        )
                    }
                }
            }
        val layoutManager = StaggeredGridLayoutManager(
            1, StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = layoutManager
    }

    private fun configureButtons() {
        buttonPrevious.setOnClickListener {
            previousPage()
        }
        buttonNext.setOnClickListener {
            nextPage()
        }
        buttonLoad.setOnClickListener {
            getData()
        }
    }

    private fun nextPage() {
        viewModel.increaseOffSet()
        viewModel.getSuperHeroes(20, viewModel.getOffSet())
    }

    private fun previousPage() {
        viewModel.decreaseOffSet()
        viewModel.getSuperHeroes(20, viewModel.getOffSet())
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): SuperHeroesFragment {
            return SuperHeroesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}