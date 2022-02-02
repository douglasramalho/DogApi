package br.com.douglasmotta.dogapichallenge.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.douglasmotta.dogapichallenge.R
import br.com.douglasmotta.dogapichallenge.extension.asJsonString
import br.com.douglasmotta.dogapichallenge.framework.di.BaseUrlModule
import br.com.douglasmotta.dogapichallenge.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(BaseUrlModule::class)
@HiltAndroidTest
class DogsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer().apply {
            start(8080)
        }
        launchFragmentInHiltContainer<DogsFragment>()
    }

    @Test
    fun shouldShowCharacters_whenViewIsCreated() {
        server.enqueue(MockResponse().setBody("dogs_p1.json".asJsonString()))

        Espresso.onView(
            withId(R.id.recycler_dogs)
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}