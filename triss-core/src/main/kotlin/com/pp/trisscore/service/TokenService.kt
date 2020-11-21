package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.TokenData
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service


@Service
class TokenService {

    fun getEmployeeDataFromToken(token: JwtAuthenticationToken): TokenData = TokenData(
            employeeId = token.tokenAttributes["uid"] as Long,
            name = token.tokenAttributes["gnm"] as String,
            surname = token.tokenAttributes["snm"] as String
    )
}
