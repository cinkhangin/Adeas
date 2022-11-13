package com.naulian.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.naulian.adeas.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAdmob(this)
        loadAllAds(this)

        val bannerView = findViewById<BannerView>(R.id.bannerView)
        showBannerAd(bannerView)

        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
           showInterstitialAd(this){}
        }

        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            showRewardedAd(this){}
        }
    }
}