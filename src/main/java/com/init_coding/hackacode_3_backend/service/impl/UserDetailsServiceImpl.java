package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.AuthCreateUserRequest;
import com.init_coding.hackacode_3_backend.dto.request.AuthLoginRequest;
import com.init_coding.hackacode_3_backend.dto.response.AuthResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.model.RoleEntity;
import com.init_coding.hackacode_3_backend.model.UserEntity;
import com.init_coding.hackacode_3_backend.repository.IRoleRepository;
import com.init_coding.hackacode_3_backend.repository.IUserRepository;
import com.init_coding.hackacode_3_backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("El usuario ".concat(username).concat(" no existe")));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(username, "Inicio de sesión exitoso", accessToken, true);
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Credenciales inválidas");

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) throws InvalidArgumentException{

        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleEnumList = authCreateUserRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntitySet = roleRepository.findByRoleEnumIn(roleEnumList)
                .stream()
                .collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) throw new InvalidArgumentException("Los roles especificados no existen");

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntitySet)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialNonExpired(true)
                .isEnabled(true)
                .build();
        UserEntity createdUser = userRepository.save(userEntity);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        createdUser.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        createdUser.getRoles()
                .stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getUsername(), createdUser.getPassword(), authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(createdUser.getUsername(), "El usuario se registró con éxito", accessToken, true);
    }

    //public boolean verificarToken(String token){}
}
