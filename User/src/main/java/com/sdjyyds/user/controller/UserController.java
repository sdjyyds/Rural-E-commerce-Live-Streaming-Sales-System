package com.sdjyyds.user.controller;

import com.sdjyyds.user.dto.*;
import com.sdjyyds.user.entity.User;
import com.sdjyyds.user.service.FileUploadService;
import com.sdjyyds.user.service.UserService;
import com.sdjyyds.user.util.ResponseResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.sdjyyds.user.util.SecurityUtils.getCurrentUserId;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileUploadService fileUploadService;
    // 用户注册接口，接收UserRegisterDTO参数并返回包含用户信息的ResponseResult
    @PostMapping("/register")
    public ResponseResult<UserVO> register(@RequestBody @Valid UserRegisterDTO dto) {
        return ResponseResult.success(userService.register(dto));
    }

    // 用户登录接口，接收用户名和密码参数并返回包含token的ResponseResult
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody @Valid UserLoginDTO dto,
                                        HttpServletResponse response) {
        if(!dto.isValidLogin()) {
            return ResponseResult.error(400, "用户名或手机号必须提供一个");
        }
        log.info("用户登录：{}", dto);

        // Get the token from the service
        String token = userService.login(dto,response);

        return ResponseResult.success(token);
    }

    // 获取当前用户信息接口，返回包含用户信息的ResponseResult
    @GetMapping("/me")
    public ResponseResult<UserDetailVO> getCurrentUser() {
        return ResponseResult.success(userService.getCurrentUser());
    }

    // 更新用户信息接口，接收更新后的用户数据UserUpdateDTO，并返回更新后的用户信息
    @PutMapping("/me")
    public ResponseResult<UserVO> updateUser(@RequestBody @Valid UserUpdateDTO dto) {
        return ResponseResult.success(userService.updateUser(dto));
    }

    // 修改密码接口，接收旧密码和新密码参数，修改成功返回空数据的ResponseResult
    @PutMapping("/me/password")
    public ResponseResult<Void> changePassword(@RequestBody @Valid PasswordChangeDTO dto) {
        log.info("修改密码：{}", dto);
        userService.changePassword(dto);
        return ResponseResult.success();
    }

    @PostMapping("/avatar")
    public ResponseResult<String> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        try {
            // 上传文件并获取文件名
            String fileName = fileUploadService.uploadFile(file);

            // 构建完整URL
            String fileUrl = "/uploads/" + fileName;

            // 保存头像URL到数据库
            userService.updateAvatarUrl(getCurrentUserId(), fileUrl);
            log.info("用户上传头像：{}", fileUrl);
            return ResponseResult.success(fileUrl);
        } catch (IllegalArgumentException e) {
            return ResponseResult.error(400, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error(500, "上传失败");
        }
    }
}
