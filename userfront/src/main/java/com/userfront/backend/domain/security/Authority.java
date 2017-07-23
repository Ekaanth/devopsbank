package com.userfront.backend.domain.security;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by root on 01/07/17.
 */
public class Authority implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
