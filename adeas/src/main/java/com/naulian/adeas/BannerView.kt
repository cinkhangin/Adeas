package com.naulian.adeas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.google.android.material.card.MaterialCardView

class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val layoutInflater = LayoutInflater.from(context)

    init { loadAd() }

    private fun loadAd() {
        if (isInEditMode) {
            val preview = loadPreview(R.layout.banner_view)
            addView(preview)
            return
        }

        Adeas.load(AdType.BANNER, context)
        addView(Adeas.adView)
    }

    private fun loadPreview(@LayoutRes layout: Int): View {
        return layoutInflater.inflate(layout, this, true)
    }
}
