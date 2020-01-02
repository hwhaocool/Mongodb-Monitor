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
     * docsExamined 和 keysExamined 差距 阈值
     */
    @Value("${threshold.key-docs-scan-gap}")
    private Integer keyDocsScanGap;
    
    /**
     * 最多查询多少条数据
     */
    @Value("${threshold.max-return-item}")
    private Integer maxReturnItem;
    
    /**
     * 最多查询多长（也许单个就比较长）
     */
    @Value("${threshold.max-return-length}")
    private Integer maxReturnLength;

    /**
     * <br>docsExamined 和 keysExamined 差距 阈值
     *
     * @return
     * @author YellowTail
     * @since 2019-12-27
     */
    public Integer getKeyDocsScanGap() {
        return keyDocsScanGap;
    }

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

    /**
     * <br>最多查询多少条数据
     *
     * @return
     * @author YellowTail
     * @since 2020-01-02
     */
    public Integer getMaxReturnItem() {
        return maxReturnItem;
    }

    /**
     * <br>最多查询多长（也许单个就比较长，单位：字节）
     *
     * @return
     * @author YellowTail
     * @since 2020-01-02
     */
    public Integer getMaxReturnLength() {
        return maxReturnLength;
    }

}
