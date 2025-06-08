package com.sdjyyds.user.service;

import com.sdjyyds.user.dto.*;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 用户服务接口
 *
 * <p>该接口定义了用户相关的基础业务操作，包括注册、登录、获取当前用户信息、更新用户资料和修改密码。</p>
 *
 * <p>所有方法均以数据传输对象（DTO）作为参数，并返回封装后的用户视图对象（VO）或操作结果。</p>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 用户注册
     *
     * <p>根据传入的用户注册信息创建新用户。</p>
     *
     * @param dto 包含用户注册信息的数据传输对象
     * @return 返回注册成功的用户视图对象
     */
    UserVO register(UserRegisterDTO dto);

    /**
     * 用户登录
     *
     * <p>根据提供的登录凭证验证用户身份，并返回登录成功后的认证令牌。</p>
     *
     * @param dto 包含用户名和密码的登录数据传输对象
     * @return 登录成功后生成的 JWT 认证令牌字符串
     */
    String login(UserLoginDTO dto, HttpServletResponse response);

    /**
     * 获取当前登录用户的信息
     *
     * <p>从安全上下文中获取当前已认证用户的详细信息。</p>
     *
     * @return 当前用户的视图对象
     */
    UserDetailVO getCurrentUser();

    /**
     * 更新用户信息
     *
     * <p>根据提供的用户更新数据传输对象更新当前用户的个人信息。</p>
     *
     * @param dto 包含需要更新的用户字段信息的数据传输对象
     * @return 返回更新后的用户视图对象
     */
    UserVO updateUser(UserUpdateDTO dto);

    /**
     * 修改用户密码
     *
     * <p>允许当前用户更改自己的登录密码。</p>
     *
     * @param dto 包含旧密码和新密码的数据传输对象
     */
    void changePassword(PasswordChangeDTO dto);

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param avatarUrl 新的头像链接
     */
    void updateAvatarUrl(Long userId, String avatarUrl);
}

