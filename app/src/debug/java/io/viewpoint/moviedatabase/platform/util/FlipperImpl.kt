package io.viewpoint.moviedatabase.platform.util

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import io.viewpoint.moviedatabase.BuildConfig
import okhttp3.Interceptor

class FlipperImpl : Flippers {

    override fun initialize(context: Context) {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)

            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(NavigationFlipperPlugin.getInstance())
            client.addPlugin(NETWORK_PLUGIN)

            client.start()
        }
    }

    override fun networkInterceptor(): Interceptor = FlipperOkhttpInterceptor(NETWORK_PLUGIN)

    companion object {
        /**
         * The network plugin of flipper.
         * Define as top-level property because the network plugin will be added as interceptor to OkHttp.
         */
        private val NETWORK_PLUGIN by lazy { NetworkFlipperPlugin() }
    }
}