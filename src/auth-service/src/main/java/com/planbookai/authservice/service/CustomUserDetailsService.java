package com.planbookai.authservice.service;

import com.planbookai.authservice.entity.NguoiDung;
import com.planbookai.authservice.repository.KhoNguoiDung;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KhoNguoiDung khoNguoiDung;
    

    public CustomUserDetailsService(KhoNguoiDung khoNguoiDung) {
        this.khoNguoiDung = khoNguoiDung;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = khoNguoiDung.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email));

        return User.builder()
                .username(nguoiDung.getEmail())
                .password(nguoiDung.getMatKhauMaHoa())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + nguoiDung.getVaiTro().name())))
                .accountLocked(!nguoiDung.getTrangThaiHoatDong())
                .build();
    }
}
