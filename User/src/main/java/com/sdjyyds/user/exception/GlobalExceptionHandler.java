package com.sdjyyds.user.exception;

import com.sdjyyds.user.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * <p>该类用于统一处理应用程序中抛出的各种异常，并返回结构化的错误响应。
 * 使用 {@link RestControllerAdvice} 注解表明这是一个全局异常处理类，
 * 并结合 {@link ExceptionHandler} 定义针对不同异常类型的处理逻辑。</p>
 *
 * <p>主要功能包括：</p>
 * <ul>
 *   <li>捕获业务异常（BusinessException）并返回指定错误码与信息</li>
 *   <li>处理参数校验失败异常（MethodArgumentNotValidException）并聚合错误信息</li>
 *   <li>兜底处理所有其他未被捕获的异常，返回通用系统错误信息</li>
 * </ul>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常（BusinessException）
     *
     * <p>当控制器方法抛出 {@link BusinessException} 异常时，
     * 会调用此方法返回封装后的错误响应结果。</p>
     *
     * @param e 捕获到的业务异常对象
     * @return 响应结果，包含错误码和错误信息
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验失败异常（MethodArgumentNotValidException）
     *
     * <p>当请求参数不符合 {@link @Valid} 注解定义的规则时，
     * Spring 会抛出 {@link MethodArgumentNotValidException}，
     * 此方法会提取所有错误信息并拼接返回。</p>
     *
     * @param e 参数校验失败的异常对象
     * @return 响应结果，包含错误码400和拼接后的错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.error("参数校验失败：{}", message, e);
        return ResponseResult.error(400, message);
    }

    /**
     * 处理未知异常（兜底异常处理）
     *
     * <p>当发生未被上述方法捕获的异常时，会进入此方法处理，
     * 返回统一的系统繁忙提示信息。</p>
     *
     * @param e 任意未被捕获的异常对象
     * @return 响应结果，包含错误码500和通用错误提示
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return ResponseResult.error(500, "系统繁忙，请稍后再试");
    }
}
