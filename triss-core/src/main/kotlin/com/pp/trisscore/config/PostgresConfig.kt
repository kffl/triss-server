package com.pp.trisscore.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class PostgresConfig : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory {
        TODO("Not yet implemented")
    }
}
