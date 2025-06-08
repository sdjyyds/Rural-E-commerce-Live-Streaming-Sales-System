package com.sdjyyds.user.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果封装类
 *
 * <p>该类用于封装接口返回给前端的统一格式数据，包含状态码、消息正文和业务数据。</p>
 *
 * <p>使用 Lombok 注解简化 POJO 类定义：</p>
 * <ul>
 *   <li>{@link Data} 自动生成 getter/setter/toString 等方法</li>
 *   <li>{@link NoArgsConstructor} 无参构造函数</li>
 *   <li>{@link AllArgsConstructor} 全参构造函数</li>
 * </ul>
 *
 * @param <T> 泛型参数，表示具体返回的数据类型
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    /**
     * 响应状态码
     * 通常使用 HTTP 标准状态码，如 200 表示成功，400 表示客户端错误等
     */
    private int code;

    /**
     * 响应消息描述
     * 用于返回操作结果的简要信息，如“成功”、“用户名已存在”等
     */
    private String message;

    /**
     * 响应数据体
     * 可为任意类型 T 的业务数据对象或集合
     */
    private T data;

    /**
     * 构建一个成功状态的响应结果（无数据）
     *
     * @param <T> 泛型类型
     * @return 返回默认成功的空数据响应对象
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(200, "成功", null);
    }

    /**
     * 构建一个成功状态的响应结果（含数据）
     *
     * @param data 成功时返回的业务数据
     * @param <T>  泛型类型
     * @return 返回携带数据的成功响应对象
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "成功", data);
    }

    /**
     * 构建一个错误状态的响应结果
     *
     * @param code    错误状态码
     * @param message 错误描述信息
     * @param <T>     泛型类型
     * @return 返回指定错误码和消息的响应对象
     */
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, message, null);
    }
}

