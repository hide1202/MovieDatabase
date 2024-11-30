package io.viewpoint.moviedatabase.viewmodel

class Command(val action: () -> Unit) {
    operator fun invoke() = action()
}