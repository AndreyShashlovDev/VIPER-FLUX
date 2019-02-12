package com.sprinter.flux.di

import dagger.Module
import dagger2.sprinter.com.dagger2.repository.source.network.NetworkModule

@Module(includes = [NetworkModule::class])
class SourceModule
