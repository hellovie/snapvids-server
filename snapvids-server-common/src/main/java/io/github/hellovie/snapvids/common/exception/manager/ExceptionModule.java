package io.github.hellovie.snapvids.common.exception.manager;

/**
 * 异常模块接口。
 * <p>「异常模块接口」需要被一个枚举类实现。在这个枚举类中，需要定义以下几点：</p>
 * <ul>
 *     <li>每个模块的编号（唯一）。</li>
 *     <li>每个模块的描述，描述该模块具体是做什么的。</li>
 *     <li>每个模块的所对应的异常码枚举类（实现至 {@link ExceptionCode}）。</li>
 * </ul>
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface ExceptionModule {

    /**
     * 获取异常模块编号。
     *
     * @return 异常模块编号
     */
    int getNumber();

    /**
     * 获取异常模块名称。
     *
     * @return 异常模块名称
     */
    String getName();

    /**
     * 获取异常模块描述。
     *
     * @return 异常模块描述
     */
    String getDescription();

    /**
     * 获取异常模块绑定的异常状态码枚举的 class。
     *
     * @return 异常模块绑定的异常状态码枚举的 class
     */
    Class<? extends ExceptionCode> getExceptionCodeClazz();
}
