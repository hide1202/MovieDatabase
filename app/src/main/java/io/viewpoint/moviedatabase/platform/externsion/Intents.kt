package io.viewpoint.moviedatabase.platform.externsion

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> intentToActivity(context: Context): Intent =
    Intent(context, T::class.java)

inline fun <reified T> Intent.getSerializable(name: String): T? = getSerializableExtra(name) as? T