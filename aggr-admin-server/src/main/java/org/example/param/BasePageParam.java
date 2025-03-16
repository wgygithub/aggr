package org.example.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Optional;

@Data
public class BasePageParam {
    private Integer pageNum;
    private Integer pageSize;

    public <T> Page<T> toPage() {
        return new Page<>(
                Optional.ofNullable(pageNum).orElse(1),
                Optional.ofNullable(pageSize).orElse(20),
                true
        );
    }
}
