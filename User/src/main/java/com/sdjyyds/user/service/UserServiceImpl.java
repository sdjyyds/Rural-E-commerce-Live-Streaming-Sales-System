package com.sdjyyds.user.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sdjyyds.user.dao.UserDao;
import com.sdjyyds.user.dto.*;
import com.sdjyyds.user.entity.User;
import com.sdjyyds.user.exception.BusinessException;
import com.sdjyyds.user.jwt.JwtTokenProvider;
import com.sdjyyds.user.util.PasswordEncryptor;
import com.sdjyyds.user.util.SecurityUtils;
import com.sdjyyds.user.util.SnowflakeIdGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务实现类
 *
 * <p>该类实现了 {@link UserService} 接口中定义的所有用户相关业务逻辑，
 * 包括注册、登录、获取当前用户信息、更新用户资料和修改密码等功能。</p>
 *
 * <p>依赖于以下组件：</p>
 * <ul>
 *   <li>{@link UserDao}：用于操作用户数据表</li>
 *   <li>{@link JwtTokenProvider}：用于生成和解析 JWT 认证令牌</li>
 * </ul>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JwtTokenProvider tokenProvider;
    private final SnowflakeIdGenerator idGenerator;

    /**
     * 用户注册
     *
     * <p>根据传入的注册信息创建新用户。会校验用户名和手机号是否已存在。
     * 使用盐值加密存储用户密码，并设置默认角色为“consumer”。</p>
     *
     * @param dto 包含用户注册信息的数据传输对象
     * @return 返回注册成功的用户视图对象（UserVO）
     * @throws BusinessException 若用户名或手机号已存在时抛出异常
     */
    @Override
    public UserVO register(UserRegisterDTO dto) {
        if (userDao.existsByPhone(dto.getPhone())) {
            log.error("手机号已注册");
            throw new BusinessException("手机号已注册");
        }
        if (dto.getUsername() != null && userDao.existsByUsername(dto.getUsername())) {
            log.error("用户名已存在");
            throw new BusinessException("用户名已存在");
        }
        // 生成用户ID
        String salt = PasswordEncryptor.generateSalt();
        String encryptedPassword = PasswordEncryptor.encryptPassword(dto.getPassword(), salt);
        User user = new User();
        user.setPasswordHash(encryptedPassword);
        user.setSalt(salt);
        user.setId(idGenerator.nextId());
        user.setPhone(dto.getPhone());
        user.setRole("CONSUMER");//默认为消费者
        user.setUsername(dto.getUsername());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userDao.insert(user);
        return convertToVO(user);
    }

    /**
     * 用户登录
     *
     * <p>根据提供的用户名和密码进行身份验证，并返回生成的 JWT 认证令牌。</p>
     *
     * @param dto 包含用户名和密码的登录数据传输对象
     * @return 登录成功后生成的 JWT 令牌字符串
     * @throws BusinessException 若用户名不存在或密码错误时抛出异常
     */
    @Override
    public String login(UserLoginDTO dto, HttpServletResponse response) {
        log.info("用户登录：{}", dto);
        User user = null;
        if (dto.getType().equals("username")) {
            user = userDao.findByUsername(dto.getUsername())
                    .orElseThrow(() -> new BusinessException("用户名或密码错误"));
        } else if (dto.getType().equals("phone")) {
            user = userDao.findByPhone(dto.getUsername())
                    .orElseThrow(() -> new BusinessException("用户名或密码错误"));
        }
        if (user == null) {
            log.error("用户不存在");
            throw new BusinessException("用户不存在");
        }
        if (!PasswordEncryptor.verifyPassword(dto.getPassword(), user.getSalt(), user.getPasswordHash())) {
            log.error("用户名或密码错误");
            throw new BusinessException("用户名或密码错误");
        }
        // Set username cookie
        Cookie usernameCookie = new Cookie("username", user.getUsername());
        usernameCookie.setDomain("localhost"); // 或不设置 domain，默认当前域
        usernameCookie.setHttpOnly(true);
        usernameCookie.setPath("/");
        usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(usernameCookie);
        // Set role cookie
        Cookie roleCookie = new Cookie("userRole", user.getRole().toString());
        roleCookie.setPath("/");
        roleCookie.setDomain("localhost");
        roleCookie.setHttpOnly(true);
        roleCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(roleCookie);
        // Set user_id cookie
        Cookie userId = new Cookie("userId", user.getId().toString());
        userId.setPath("/");
        userId.setDomain("localhost");
        userId.setHttpOnly(true);
        userId.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(userId);
        log.info("用户登录成功");
        return tokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());
    }
    /**
     * 获取当前登录用户的信息
     *
     * <p>从安全上下文中获取当前用户的唯一标识，并查询数据库返回其详细信息。</p>
     *
     * @return 当前用户的视图对象（UserVO）
     * @throws BusinessException 若用户不存在时抛出异常
     */
    @Override
    public UserDetailVO getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userDao.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        return convertToDetailVO(user);
    }

    /**
     * 更新用户信息
     *
     * <p>根据传入的更新数据传输对象修改当前用户的部分字段，如真实姓名和头像链接。</p>
     *
     * @param dto 包含需要更新的用户字段信息的数据传输对象
     * @return 返回更新后的用户视图对象（UserVO）
     * @throws BusinessException 若用户不存在时抛出异常
     */
    @Override
    public UserVO updateUser(UserUpdateDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userDao.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        if (StringUtils.isNotBlank(dto.getRealName())) {
            user.setRealName(dto.getRealName());
        }
        if(StringUtils.isNotBlank(dto.getPhone())){
            user.setPhone(dto.getPhone());
        }
        if (StringUtils.isNotBlank(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }
        user.setUpdatedAt(new Date());
        userDao.update(user);

        return convertToVO(user);
    }

    /**
     * 修改用户密码
     *
     * <p>允许当前用户更改自己的登录密码。需提供正确的旧密码才能进行更新。</p>
     *
     * @param dto 包含旧密码和新密码的数据传输对象
     * @throws BusinessException 若用户不存在或旧密码不正确时抛出异常
     */
    @Override
    public void changePassword(PasswordChangeDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userDao.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        if (!PasswordEncryptor.verifyPassword(dto.getOldPassword(), user.getSalt(), user.getPasswordHash())) {
            throw new BusinessException("旧密码不正确");
        }
        log.info("修改密码: " + dto);
        String newSalt = PasswordEncryptor.generateSalt();
        String newEncryptedPassword = PasswordEncryptor.encryptPassword(dto.getNewPassword(), newSalt);

        user.setPasswordHash(newEncryptedPassword);
        user.setSalt(newSalt);
        userDao.update(user);
    }

    @Override
    public void updateAvatarUrl(Long userId, String avatarUrl) {
        userDao.updateAvatarUrl(userId, avatarUrl);
    }

    /**
     * 将用户实体对象转换为视图对象
     *
     * <p>用于将数据库查询到的用户实体（User）转换为前端展示用的视图对象（UserVO）。</p>
     *
     * @param user 数据库中的用户实体
     * @return 对应的用户视图对象
     */
    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        vo.setRole(user.getRole());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setPhone(user.getPhone());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setVerified(user.getIsVerified());
        return vo;
    }

    private UserDetailVO convertToDetailVO(User user) {
        UserDetailVO vo = new UserDetailVO();
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());// 拼接完整 URL
        if (user.getAvatarUrl() != null) {
            vo.setAvatarUrl("http://localhost:8080" + user.getAvatarUrl()); // 拼接域名或IP
        }
        vo.setVerified(user.getIsVerified());
        vo.setCreatedAt(user.getCreatedAt());
        vo.setUpdatedAt(user.getUpdatedAt());
        return vo;
    }
}
