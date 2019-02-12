package dagger2.sprinter.com.dagger2.repository.source.network

import com.sprinter.flux.repository.source.network.NetService
import com.sprinter.flux.repository.source.network.NetServiceImpl
import com.sprinter.flux.repository.source.network.api.GithubApi
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    private val netServiceFactory: NetService = NetServiceImpl()

    @Provides
    fun getGithubApi(): GithubApi = netServiceFactory.getGithubApi()
}
