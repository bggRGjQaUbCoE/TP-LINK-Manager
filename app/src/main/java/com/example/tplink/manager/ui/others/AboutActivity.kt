package com.example.tplink.manager.ui.others

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.AbsAboutActivity
import com.drakeet.about.Card
import com.drakeet.about.Category
import com.drakeet.about.Contributor
import com.drakeet.about.License
import com.example.tplink.manager.BuildConfig
import com.example.tplink.manager.R

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class AboutActivity : AbsAboutActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = applicationInfo.loadLabel(packageManager)
        version.text = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.add(Category(getString(R.string.about)))
        items.add(Card("TP-LINK Manager"))

        items.add(Category(getString(R.string.about_developer)))
        items.add(
            Contributor(
                R.drawable.cont_author,
                "bggRGjQaUbCoE",
                "Developer & Designer",
                "https://github.com/bggRGjQaUbCoE"
            )
        )

        items.add(Category(getString(R.string.feedback)))
        items.add(Card("Github\nhttps://github.com/bggRGjQaUbCoE/TP-LINK-Manager"))

        items.add(Category(getString(R.string.about_open_source)))
        items.add(
            License(
                "kotlin",
                "JetBrains",
                License.APACHE_2,
                "https://github.com/JetBrains/kotlin"
            )
        )
        items.add(License("AndroidX", "Google", License.APACHE_2, "https://source.google.com"))
        items.add(
            License(
                "material-components-android",
                "Google",
                License.APACHE_2,
                "https://github.com/material-components/material-components-android"
            )
        )
        items.add(
            License(
                "about-page",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/about-page"
            )
        )
        items.add(License("okhttp", "square", License.APACHE_2, "https://github.com/square/okhttp"))
        items.add(
            License(
                "retrofit",
                "square",
                License.APACHE_2,
                "https://github.com/square/retrofit"
            )
        )
        items.add(
            License(
                "coil",
                "coil-kt",
                License.APACHE_2,
                "https://github.com/coil-kt/coil"
            )
        )
    }

}