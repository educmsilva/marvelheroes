package com.example.marvelheroes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import kotlinx.android.synthetic.main.fragment_super_hero.view.*

class SuperHeroFragment : DialogFragment() {

    companion object {

        const val TAG = "SimpleDialog"

        private const val HERO_THUMB = "HERO_THUMB"
        private const val HERO_NAME = "HERO_NAME"
        private const val HERO_DESCRIPTION = "HERO_DESCRIPTION"

        fun newInstance(heroThumb: String, heroName: String, heroDesc: String): SuperHeroFragment {
            val args = Bundle()
            args.putString(HERO_THUMB, heroThumb)
            args.putString(HERO_NAME, heroName)
            args.putString(HERO_DESCRIPTION, heroDesc)
            val fragment = SuperHeroFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_super_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        view.textView_heroName.text = arguments?.getString(HERO_NAME)
        view.textView_Description.text = arguments?.getString(HERO_DESCRIPTION)
        val heroThumb = arguments?.getString(HERO_THUMB)
        Glide.with(view.imageView_Hero.context)
            .load(heroThumb)
            .into(view.imageView_Hero)
    }

    private fun setupClickListeners(view: View) {
        view.buttonClose.setOnClickListener {
            dismiss()
        }
    }

}