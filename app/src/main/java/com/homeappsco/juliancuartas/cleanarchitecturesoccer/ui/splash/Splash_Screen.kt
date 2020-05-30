package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash__screen.*

class Splash_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_splash__screen)

        val animationTop = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        textView.startAnimation(animationTop)

        val animationFade = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(animationFade)

        val animationBotton = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        textView2.startAnimation(animationBotton)

        animationBotton.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

    }

}
