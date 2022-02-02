package br.com.douglasmotta.dogapichallenge.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.douglasmotta.dogapichallenge.framework.di.BaseUrlModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(BaseUrlModule::class)
@HiltAndroidTest
class DogsFragmentTest