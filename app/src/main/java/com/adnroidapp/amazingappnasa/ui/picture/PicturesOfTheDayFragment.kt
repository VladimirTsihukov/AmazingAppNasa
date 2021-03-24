package com.adnroidapp.amazingappnasa.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.adnroidapp.amazingappnasa.MainActivity
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import com.adnroidapp.amazingappnasa.ui.fragment.SettingFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class PicturesOfTheDayFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: PictureOfTheDayViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var serverResponseData: NasaImageResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favorite")
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                serverResponseData = data.serverResponse
                bottom_sheet_description.text = serverResponseData.explanation
                bottom_sheet_description_header.text = serverResponseData.title

                serverResponseData.url?.let {
                    image_view.load(it.toUri()) {
                        lifecycle(this@PicturesOfTheDayFragment)
                        error(R.drawable.ic_baseline_photo_64)
                        placeholder(R.drawable.ic_baseline_photo_64)
                    }
                }
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
            is PictureOfTheDayData.Loading -> {

            }
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
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_two_screen)
            } else {
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PicturesOfTheDayFragment()
        private var isMain = true
    }
}