package io.sample.task

import io.sample.task.home.*
import org.koin.androidx.viewmodel.dsl.*
import org.koin.dsl.*

val appModule = module {
    viewModel { MainActivityViewModel() }
}