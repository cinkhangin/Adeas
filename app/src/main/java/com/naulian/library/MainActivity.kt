package com.naulian.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import com.google.android.material.materialswitch.MaterialSwitch
import com.naulian.adeas.*
import com.naulian.anhance.activityScope
import com.naulian.anhance.observe
import com.naulian.anhance.showToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val interButton = findViewById<Button>(R.id.btnInterstitial)
        val rewardedButton = findViewById<Button>(R.id.btnRewarded)
        val adSwitch = findViewById<MaterialSwitch>(R.id.switchAds)
        val bannerView = findViewById<BannerView>(R.id.bannerView)

        activityScope {
            initializeAdmob(this@MainActivity)
            loadAllAds(this@MainActivity)
        }

        interButton.setOnClickListener {
            showInterstitialAd(this) {}
        }

        rewardedButton.setOnClickListener {
            showRewardedAd(this) {}
        }


        adSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                activityScope {
                    if (isChecked) Adeas.enableAds(this@MainActivity)
                    else Adeas.disableAds(this@MainActivity)
                }
            }
        }

        activityScope {
            observe(Adeas.state) {
                adSwitch.isChecked = it
                bannerView.isVisible = it
            }
        }
    }
}