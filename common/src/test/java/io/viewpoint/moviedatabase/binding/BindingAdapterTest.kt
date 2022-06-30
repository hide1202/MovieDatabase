package io.viewpoint.moviedatabase.binding

import android.app.Activity
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.viewmodel.Command
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@RunWith(RobolectricTestRunner::class)
class BindingAdapterTest {
    @Test
    fun setGoneTest() {
        val controller: ActivityController<Activity> =
            Robolectric.buildActivity(Activity::class.java)

        val view = View(controller.get())

        expectThat(view.isVisible).isTrue()
        view.setGone(true)
        expectThat(view.isGone).isTrue()
    }

    @Test
    fun setCommandTest() {
        val controller: ActivityController<Activity> =
            Robolectric.buildActivity(Activity::class.java)

        val view = View(controller.get())

        expectThat(view.hasOnClickListeners()).isFalse()

        var isClicked = false
        view.setCommand(Command {
            isClicked = true
        })
        expectThat(view.hasOnClickListeners()).isTrue()
        view.performClick()
        expectThat(isClicked).isTrue()
    }

    @Test
    fun setVerticalDividerDecorationTest() {
        val controller: ActivityController<Activity> =
            Robolectric.buildActivity(Activity::class.java)

        val recyclerView = RecyclerView(controller.get())
        recyclerView.setVerticalDividerDecoration(isAdded = true)

        expectThat(recyclerView.itemDecorationCount).isEqualTo(1)
        expectThat(recyclerView.getItemDecorationAt(0)).isA<DividerItemDecoration>()
    }
}