package com.naulian.adeas

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object Adeas {
    private var rewardedAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd? = null
    private var debugMode: Boolean = true

    @Suppress("unused")
    private val appId = "ca-app-pub-8780587618161459~6948608347"

    private val testUnits = AdUnits()
    private var adUnits = AdUnits()

    var adView: AdView? = null

    fun initialize(context: Context, adUnits: AdUnits, isDebugMode: Boolean) {
        MobileAds.initialize(context)

        debugMode = isDebugMode
        adView = AdView(context)
        this.adUnits = adUnits
    }

    fun load(adType: AdType, context: Context) {
        val adRequest = AdRequest.Builder().build()

        when (adType) {
            AdType.REWARDED -> loadRewarded(adRequest, context)
            AdType.BANNER -> loadBanner(adRequest, context)
            AdType.INTERSTITIAL -> loadInterstitial(adRequest, context)
        }
    }

    fun loadAll(context: Context) {
        load(AdType.BANNER, context)
        load(AdType.INTERSTITIAL, context)
        load(AdType.REWARDED, context)
    }

    private fun loadInterstitial(adRequest: AdRequest, context: Context) {
        val addLoadCallback = object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("Interstitial Ad", adError.message)
                interstitialAd = null
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d("Interstitial Ad", "Ad was loaded")
                interstitialAd = ad
            }
        }

        val id = getAdString(AdType.INTERSTITIAL)
        InterstitialAd.load(context, id, adRequest, addLoadCallback)
    }

    private fun loadBanner(adRequest: AdRequest, context: Context) {
        adView = AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = getAdString(AdType.BANNER)
            loadAd(adRequest)
        }
    }

    private fun loadRewarded(adRequest: AdRequest, context: Context) {

        val rewardedAdLoadCallback = object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                super.onAdLoaded(ad)
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                rewardedAd = null
            }
        }

        val id = getAdString(AdType.REWARDED)
        RewardedAd.load(context, id, adRequest, rewardedAdLoadCallback)

    }

    private fun getAdString(adType: AdType): String {
        return when (adType) {
            AdType.REWARDED -> if (debugMode) testUnits.rewarded
            else adUnits.rewarded

            AdType.INTERSTITIAL -> if (debugMode) testUnits.interstitial
            else adUnits.interstitial

            AdType.BANNER -> if (debugMode) testUnits.banner
            else adUnits.banner
        }
    }

    fun showRewardedAd(activity: Activity, action: (result: Boolean) -> Unit) {
        if (rewardedAd == null) {
            action(false)
            return
        }

        rewardedAd?.let {
            it.show(activity) {
                action(true)
                load(AdType.REWARDED, activity)
            }
        }
    }

    fun showInterstitialAd(activity: Activity, action: (result: Boolean) -> Unit) {
        if (interstitialAd == null) {
            action(false)
            return
        }

        interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {}
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    action(true)
                }

                override fun onAdImpression() {}
                override fun onAdShowedFullScreenContent() {}
            }
            it.show(activity)
        }
    }

    fun clearAdView() {
        adView = null
    }
}