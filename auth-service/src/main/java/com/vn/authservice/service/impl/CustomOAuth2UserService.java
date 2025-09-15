package com.vn.authservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Dịch vụ tùy chỉnh để xử lý dữ liệu người dùng khi xác thực bằng OAuth2.
 * Kế thừa từ DefaultOAuth2UserService để có thể lấy thông tin từ OAuth2 providers (Google, Facebook, v.v.).
 */
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    /**
     * Phương thức lấy thông tin người dùng từ OAuth2 provider.
     *
     * @param userRequest Yêu cầu xác thực OAuth2.
     * @return Đối tượng OAuth2User chứa thông tin người dùng.
     * @throws OAuth2AuthenticationException Nếu có lỗi xảy ra trong quá trình xác thực.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Gọi phương thức loadUser từ DefaultOAuth2UserService để lấy thông tin người dùng
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Trả về một DefaultOAuth2User với danh sách quyền hạn và thông tin thuộc tính của người dùng
        // "email" được sử dụng làm key chính để nhận dạng người dùng
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "email");
    }
}
