package com.naulian.adeas

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

class BannerView(context: Context, attrs: AttributeSet) :
    MaterialCardView(context, attrs) {

    init { loadAd() }

    private fun loadAd() {
        Adeas.load(AdType.BANNER, context)
        addView(Adeas.adView)
    }
}