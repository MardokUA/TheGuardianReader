package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = obtainViewModel(MainViewModel::class.java)

        mainSwapViewButton.setOnClickListener { view -> view.isSelected = !view.isSelected }
    }
}
