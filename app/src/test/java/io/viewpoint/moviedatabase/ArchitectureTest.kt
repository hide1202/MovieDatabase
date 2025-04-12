package io.viewpoint.moviedatabase

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.Test

class ArchitectureTest {
    private val presentationLayer = Layer("presentation", "io.viewpoint.moviedatabase.ui..")
    private val domainLayer = Layer("domain", "io.viewpoint.moviedatabase.domain..")
    private val dataLayer = Layer("data", "io.viewpoint.moviedatabase.core.data..")

    @Test
    fun `architecture layers have dependencies correct`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                presentationLayer.dependsOn(domainLayer)
                presentationLayer.dependsOn(dataLayer)
                dataLayer.dependsOn(domainLayer)

                domainLayer.doesNotDependOn(dataLayer, presentationLayer)
                domainLayer.dependsOnNothing()
            }
    }
}