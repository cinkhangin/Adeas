package com.naulian.adeas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView

class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        if (isInEditMode) loadPreview()
        else loadAd()
    }

    private fun loadPreview() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.banner_view, this, true)
    }

    private fun loadAd() {
        addView(Adeas.createBanner(context))
    }
}
