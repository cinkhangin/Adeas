package com.naulian.adeas

import android.app.Activity
import android.content.Context

fun initializeAdmob(
    context: Context,
    adUnits: AdUnits = AdUnits(),
    debug: Boolean = false
) {
    Adeas.initialize(context, adUnits, debug)
}

fun loadRewardedAd(context: Context){
    Adeas.load(AdType.REWARDED, context)
}

fun loadInterstitialAd(context: Context){
    Adeas.load(AdType.INTERSTITIAL , context)
}

fun loadBannerAd(context: Context){
    Adeas.load(AdType.BANNER, context)
}

fun loadAds(context: Context , adType: AdType){
    Adeas.load(adType , context)
}

fun loadAllAds(context: Context){
    Adeas.loadAll(context)
}

fun showRewardedAd(activity: Activity, action: (result: Boolean) -> Unit){
    Adeas.showRewardedAd(activity, action)
}

fun showInterstitialAd(activity: Activity, action: (result: Boolean) -> Unit){
    Adeas.showInterstitialAd(activity , action)
}

fun showBannerAd(bannerView: BannerView){
    bannerView.loadAd()
}