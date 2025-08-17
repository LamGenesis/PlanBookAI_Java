package com.planbookai.authservice.service;

import com.planbookai.authservice.dto.PhanHoiDangNhap;
import com.planbookai.authservice.dto.YeuCauDangNhap;

public interface DichVuXacThuc {
    PhanHoiDangNhap dangNhap(YeuCauDangNhap yeuCau);
    void dangXuat(String bearerToken);
    PhanHoiDangNhap lamMoiToken(String refreshToken);
    void validateToken(String bearerToken);
}
