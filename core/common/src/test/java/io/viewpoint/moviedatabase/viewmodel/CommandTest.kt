package io.viewpoint.moviedatabase.viewmodel

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isTrue

class CommandTest {
    @Test
    fun commandSynchronousTest() {
        var invoked = false
        val command = Command {
            invoked = true
        }
        command()

        expectThat(invoked).isTrue()
    }
}