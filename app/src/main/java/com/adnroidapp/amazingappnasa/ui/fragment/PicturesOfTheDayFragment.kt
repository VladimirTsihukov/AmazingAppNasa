package com.adnroidapp.amazingappnasa.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.adnroidapp.amazingappnasa.ClassKey.SEARCH_WIKI
import com.adnroidapp.amazingappnasa.ClassKey.TODAY
import com.adnroidapp.amazingappnasa.ClassKey.TWO_DAY_AGO
import com.adnroidapp.amazingappnasa.ClassKey.YESTERDAY
import com.adnroidapp.amazingappnasa.MainActivity
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import com.adnroidapp.amazingappnasa.toast
import com.adnroidapp.amazingappnasa.ui.PictureOfTheDayData
import com.adnroidapp.amazingappnasa.ui.viewModel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_start.*
import kotlinx.android.synthetic.main.fragment_main_start.bottom_app_bar
import kotlinx.android.synthetic.main.fragment_main_start.fab
import kotlinx.android.synthetic.main.fragment_main_start.input_edit_text
import kotlinx.android.synthetic.main.fragment_main_start.input_layout
import kotlinx.android.synthetic.main.view_show_image_web_view.*
import java.util.*
import kotlinx.android.synthetic.main.fragment_main_start.layout_load_image as layout_load_image1

class PicturesOfTheDayFragment : Fragment(R.layout.fragment_main_start) {

    private val viewModel: PictureOfTheDayViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var serverResponseData: NasaImageResponse
    private lateinit var tableLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setTabLayout(view)
    }

    private fun setTabLayout(view: View) {
        tableLayout = view.findViewById(R.id.table_layout_main)
        tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.getPhotoOfTheDatInServer(TODAY)
                    1 -> viewModel.getPhotoOfTheDatInServer(YESTERDAY)
                    2 -> viewModel.getPhotoOfTheDatInServer(TWO_DAY_AGO)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchInWiki()
        viewModel.liveDataForViewToObserver.observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    private fun searchInWiki() {
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$SEARCH_WIKI${input_edit_text.text.toString()}")
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_setting -> {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .add(R.id.container, SettingFragment.newInstance(), SettingFragment.TAG)
                        .addToBackStack(SettingFragment.TAG).commit()
                }
            }
            R.id.app_bar_search -> toast("Search")
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(
                        it.supportFragmentManager,
                        BottomNavigationDrawerFragment.TAG
                    )
                }
            }
            R.id.app_bar_image -> {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .add(R.id.container, ImageFragment.newInstance(), ImageFragment.TAG)
                        .addToBackStack(ImageFragment.TAG)
                        .commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                serverResponseData = data.serverResponse
                bottom_sheet_description.text = serverResponseData.explanation
                bottom_sheet_description_header.text = serverResponseData.title

                serverResponseData.url?.let {
                    if (serverResponseData.mediaType == "video") {
                        loadWebView(it)
                    } else {
                        imageLoad(it)
                    }
                }
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message.toString())
            }
            is PictureOfTheDayData.Loading -> {
            }
        }
    }

    private fun loadWebView(it: String) {
        image_view.visibility = View.INVISIBLE
        web_view.visibility = View.VISIBLE
        web_view.apply {
            clearCache(true)
            clearHistory()
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically
            loadUrl(it)
        }
    }

    private fun imageLoad(it: String) {
        web_view.visibility = View.INVISIBLE
        image_view.visibility = View.VISIBLE
        image_view.load(it) {
            lifecycle(this@PicturesOfTheDayFragment)
            error(R.drawable.ic_baseline_photo_64)
            placeholder(R.drawable.ic_baseline_photo_64)
        }
    }


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> visibilityItemBottomSheet(true)
                    BottomSheetBehavior.STATE_COLLAPSED -> visibilityItemBottomSheet(false)
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun visibilityItemBottomSheet(visibility: Boolean) {
        if (visibility) {
            bottom_sheet_description.visibility = View.VISIBLE
            bottom_sheet_description_header.visibility = View.VISIBLE
            bottom_sheet_description_text.visibility = View.INVISIBLE
        } else {
            bottom_sheet_description.visibility = View.INVISIBLE
            bottom_sheet_description_header.visibility = View.INVISIBLE
            bottom_sheet_description_text.visibility = View.VISIBLE
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)

        fab.setOnClickListener {
            if (isMain) {
                darkensLayoutPicture()
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_two_screen)
            } else {
                lighterLayoutPicture()
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }
    }

    private fun darkensLayoutPicture() {
        tableLayout.animate().alpha(0.2f).duration = 300
        layout_load_image.animate().alpha(0.2f).duration = 300
    }

    private fun lighterLayoutPicture() {
        tableLayout.animate().alpha(1f).duration = 300
        layout_load_image.animate().alpha(1f).duration = 300
    }

    companion object {
        fun newInstance() = PicturesOfTheDayFragment()
        private var isMain = true
    }
}