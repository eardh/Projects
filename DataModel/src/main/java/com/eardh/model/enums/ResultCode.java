package com.eardh.model.enums;

/**
 * 返回码/业务码 枚举
 * @author eardh
 * @date 2022/6/13 15:35
 */
public enum ResultCode implements IEnum{
    /**
     * 失败
     */
    fail(-1),

    /**
     * 成功
     */
    ok(0),

    /**
     * 操作被取消
     */
    cancelled(1),

    /**
     * 未知错误
     */
    unknown(2),

    /**
     * 无效参数
     */
    invalid_argument(3),

    /**
     * 截止日期在操作完成之前到期
     */
    deadline_exceeded(4),

    /**
     * 未找到
     */
    not_found(5),

    /**
     * 已存在
     */
    already_exists(6),

    /**
     * 没有权限
     */
    permission_denied(7),

    /**
     * 未验证
     */
    unauthenticated(16),

    /**
     * 资源已用尽
     */
    resource_exhausted(8),

    /**
     * 由于系统未处于状态，操作被拒绝
     */
    failed_precondition(9),

    /**
     * 操作被中止，通常是由于并发问题，例如 排序器检查失败或事务中止。
     */
    aborted(10),

    /**
     * 尝试超出有效范围的操作
     */
    out_of_range(11),

    /**
     * 该操作未实现或不支持在此启用服务
     */
    unimplemented(12),
    /**
     * 内部错误
     */
    internal(13),

    /**
     * 该服务目前不可用
     */
    unavailable(14),

    /**
     * 不可恢复的数据丢失或损坏
     */
    data_loss(15);
    
    private final Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}