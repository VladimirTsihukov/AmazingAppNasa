package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.PlanetsEnum
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.toast
import com.adnroidapp.amazingappnasa.ui.adapter.AdapterImage
import com.adnroidapp.amazingappnasa.ui.viewModel.ImageViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.content_scrolling_image.*
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment(R.layout.fragment_image) {

    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapterImage by lazy { AdapterImage() }
    private lateinit var bottomNav: BottomNavigationView
    private var namePlanets = PlanetsEnum.EARTH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        recyclerView = view.findViewById(R.id.res_view_image)
        recyclerView.adapter = adapterImage

        initBottomNavigation()

        imageViewModel.liveDataImage.observe(viewLifecycleOwner, {
            adapterImage.bindImage(it, namePlanets)
        })

        nested_scroll_view.setOnScrollChangeListener { _, _, _, _, _ ->
            toolbar_image.isSelected = nested_scroll_view.canScrollVertically(-1)
        }
    }

    private fun setValuesToolbar() {
        when (namePlanets) {
            PlanetsEnum.EARTH -> {
                ic_image_toolbar.setImageResource(R.drawable.ic_earth_for_toolbar)
                text_title_toolbar.setText(R.string.earth)
                text_message_toolbar.setText(R.string.message_in_toolbar_for_earth)
            }
            PlanetsEnum.MARS -> {
                ic_image_toolbar.setImageResource(R.drawable.ic_mars_for_toolbar)
                text_title_toolbar.setText(R.string.mars)
                text_message_toolbar.setText(R.string.message_in_toolbar_for_mars)
            }
            PlanetsEnum.MOON -> {
                ic_image_toolbar.setImageResource(R.drawable.ic_moon_for_toolbar)
                text_title_toolbar.setText(R.string.moon)
                text_message_toolbar.setText(R.string.message_in_toolbar_for_moon)
            }
        }

    }

    private fun initToolbar() {
        toolbar_image.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_image.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_nav_view)
            bottomNav.getOrCreateBadge(R.id.item_bottom_nav_earth)
            val badge = bottomNav.getBadge(R.id.item_bottom_nav_earth)
            badge?.apply {
                maxCharacterCount = 2
                badgeGravity = BadgeDrawable.TOP_END
                number = 8
            }
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_bottom_nav_earth -> {
                        namePlanets = PlanetsEnum.EARTH
                        imageViewModel.getImagesEarthInServer()
                        setValuesToolbar()
                        true
                    }
                    R.id.item_bottom_nav_mars -> {
                        namePlanets = PlanetsEnum.MARS
                        imageViewModel.getPhotoMarsInServer()
                        setValuesToolbar()
                        true
                    }
                    R.id.item_bottom_nav_moon -> {
                        namePlanets = PlanetsEnum.MOON
                        toast("Moon")
                        setValuesToolbar()
                        true
                    }
                    else -> true
                }
            }
        }
    }

    companion object {
        fun newInstance() = ImageFragment()
        const val TAG = "ImageFragment"
    }
}