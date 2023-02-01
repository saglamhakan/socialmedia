package com.example.demo.security;

import com.example.demo.entities.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Data
public class JwtUserDetails implements UserDetails {

    Long userId;
    String username;
    String password;
    private Collection<? extends GrantedAuthority> authorities;


   private JwtUserDetails (Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities ){
       this.userId=userId;
       this.username=username;
       this.password=password;
       this.authorities=authorities;

   }

   public static JwtUserDetails create(User user){
       List<GrantedAuthority> authoriesList=new ArrayList<>();
       authoriesList.add(new SimpleGrantedAuthority("user"));
       return new JwtUserDetails(user.getUserId(), user.getUserName(), user.getPassword(),authoriesList);
   }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
