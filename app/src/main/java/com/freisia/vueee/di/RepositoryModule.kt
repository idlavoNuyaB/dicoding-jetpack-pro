package com.freisia.vueee.di

import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.data.repository.TVRepository
import org.koin.dsl.module

val repositoryModule = module{
    single{TVRepository(get())}
    single{MovieRepository(get())}
}