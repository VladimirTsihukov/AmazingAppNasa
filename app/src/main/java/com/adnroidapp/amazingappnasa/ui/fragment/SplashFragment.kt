package com.adnroidapp.amazingappnasa.ui.fragment

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.R
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.img_fragment_splash.animate()
            .rotationBy(540f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(3000)

            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(p0: Animator?) {
                    activity?.let {
                        it.supportFragmentManager.popBackStack()
                    }
                }
                override fun onAnimationStart(p0: Animator?) {}
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
    }

    companion object{
        const val TAG = "SplashFragment"
        fun newInstance() = SplashFragment()
    }
}