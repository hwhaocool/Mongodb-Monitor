package com.github.hwhaocool.mm.service.threshold;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ThresholdService {
    
    /**
     * 最大文档扫描数
     */
    @Value("${threshold.max-docs}")
    private Integer docsExaminedThreshold;
    
    /**
     * 查询耗时阈值
     */
    @Value("${threshold.min-cost}")
    private Integer minCostThreshold;

    /**
     * <br>最大文档扫描数
     *
     * @return
     * @author YellowTail
     * @since 2019-12-27
     */
    public Integer getDocsExaminedThreshold() {
        return docsExaminedThreshold;
    }

    /**
     * <br>查询耗时阈值， 单位：毫秒
     *
     * @return
     * @author YellowTail
     * @since 2019-12-27
     */
    public Integer getMinCostThreshold() {
        return minCostThreshold;
    }
    

}
