package org.example.JWT;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

        User username = userService.findByUser(user);
        return new JwtUserDetails(username);
    }

    public JwtToken getTokenAuthenticated(String user) {
        User.Role role = userService.findRoleByUser(user);
        return JwtUtils.createToken(user, role.name().substring("ROLE_".length()));
    }

}
