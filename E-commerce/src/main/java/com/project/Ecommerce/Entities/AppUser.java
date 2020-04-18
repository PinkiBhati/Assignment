package com.project.Ecommerce.Entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {


   private String username;
    private String password;
    List<GrantAuthorityImpl> grantAuthorities;
    private boolean isEnabled;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;


    public AppUser(String username, String password, List<GrantAuthorityImpl> grantAuthorities,boolean isEnabled,boolean isAccountNonLocked,boolean isAccountNonExpired) {
        this.username= username;
        this.password = password;
        this.grantAuthorities = grantAuthorities;
        this.isEnabled=isEnabled;
        this.isAccountNonLocked= isAccountNonLocked;
        this.isAccountNonExpired= isAccountNonExpired;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }


    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantAuthorityImpl> getGrantAuthorities() {
        return grantAuthorities;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }



}