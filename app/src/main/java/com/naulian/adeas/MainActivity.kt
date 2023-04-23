package com.naulian.adeas

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.materialswitch.MaterialSwitch
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
            initializeAdmob(this@MainActivity, debug = true)
        }
        activityScope {
            loadAllAds(this@MainActivity)

            observe(Adeas.state) {
                adSwitch.isChecked = it
                bannerView.isVisible = it
            }
        }

        interButton.setOnClickListener {
            showInterstitialAd(this) {
                if (!it) showToast("Ad is not ready")
            }
        }

        rewardedButton.setOnClickListener {
            showRewardedAd(this) {
                if (!it) showToast("Ad is not ready")
            }
        }


        adSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                activityScope {
                    if (isChecked) Adeas.enableAds(this@MainActivity)
                    else Adeas.disableAds(this@MainActivity)
                }
            }
        }
    }
}