package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminUserService;
import com.khomsi.backend.main.utils.email.service.EmailService;
import com.khomsi.backend.main.user.mapper.UserInfoMapper;
import com.khomsi.backend.main.user.model.dto.ShortUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.repository.UserInfoRepository;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.khomsi.backend.main.utils.Utils.createSorting;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final EmailService emailService;

    @Override
    public AdminModelResponse getAllUsers(EntityModelRequest entityModelRequest) {
        int page = entityModelRequest.getPage();
        Pageable pageable = PageRequest.of(page, entityModelRequest.getSize(),
                createSorting(entityModelRequest.getSort(), "externalId"));
        Page<UserInfo> userInfoPage = userInfoRepository.findAll(pageable);

        List<ShortUserInfoDTO> users = userInfoPage.getContent().stream()
                .map(userInfoMapper::toShortUserInfoDTO)
                .toList();

        return AdminModelResponse.builder()
                .entities(users)
                .totalItems(userInfoPage.getTotalElements())
                .totalPages(userInfoPage.getTotalPages())
                .currentPage(page)
                .build();
    }

    @Override
    public AdminResponse updateUserBalance(String userId, BigDecimal newBalance) {
        UserInfo user = userInfoService.getExistingUser(userId);
        BigDecimal oldBalance = user.getBalance();
        user.setBalance(newBalance);
        userInfoRepository.save(user);
        emailService.sendBalanceUpdateNotification(user.getEmail(), oldBalance, newBalance);
        return new AdminResponse("User balance updated successfully");
    }
}
