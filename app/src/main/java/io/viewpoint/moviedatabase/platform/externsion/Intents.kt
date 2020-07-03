package io.viewpoint.moviedatabase.platform.externsion

import android.content.Intent

inline fun <reified T> Intent.getSerializable(name: String): T? = getSerializableExtra(name) as? T