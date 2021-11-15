package com.freisia.vueee.di

import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.data.remote.repository.TVRepository
import org.koin.dsl.module

val repositoryModule = module{
    single{TVRepository(get())}
    single{MovieRepository(get())}
    single{MovieLocalRepository(get())}
    single{TVLocalRepository(get())}
}