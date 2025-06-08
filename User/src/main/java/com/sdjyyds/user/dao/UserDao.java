package com.sdjyyds.user.dao;

import com.sdjyyds.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface UserDao {

    // 插入新用户
    void insert(User user);

    // 更新现有用户信息
    void update(User user);
    void updateAvatarUrl(@Param("userId") Long userId,@Param("avatarUrl") String avatarUrl);
    // 根据ID查找用户，返回可选值
    Optional<User> findById(Long id);

    // 根据用户名查找用户，返回可选值
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);

    // 检查用户名是否存在
    boolean existsByUsername(String username);
    // 检查手机号是否存在
    boolean existsByPhone(String phone);
}

