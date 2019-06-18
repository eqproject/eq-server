package org.eq.modules.log;

import java.util.List;

/**
 * @author : gb 2019/6/18
 */
public interface CommonLogService<T> {
    public void save(T t);

    public List<T> list(long orderId);
}
