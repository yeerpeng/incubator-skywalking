/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.ui.service;

import java.util.List;
import org.apache.skywalking.apm.collector.core.module.ModuleManager;
import org.apache.skywalking.apm.collector.core.util.*;
import org.apache.skywalking.apm.collector.storage.StorageModule;
import org.apache.skywalking.apm.collector.storage.dao.ui.*;
import org.apache.skywalking.apm.collector.storage.table.global.GlobalTraceTable;
import org.apache.skywalking.apm.collector.storage.table.segment.SegmentDurationTable;
import org.apache.skywalking.apm.collector.storage.ui.trace.*;
import org.apache.skywalking.apm.collector.ui.utils.EsIndexSuffixUtil;
import org.slf4j.*;

/**
 * @author peng-yongsheng
 */
public class SegmentTopService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentTopService.class);

    private final ISegmentDurationUIDAO segmentDurationUIDAO;
    private final IGlobalTraceUIDAO globalTraceUIDAO;

    public SegmentTopService(ModuleManager moduleManager) {
        this.segmentDurationUIDAO = moduleManager.find(StorageModule.NAME).getService(ISegmentDurationUIDAO.class);
        this.globalTraceUIDAO = moduleManager.find(StorageModule.NAME).getService(IGlobalTraceUIDAO.class);
    }

    public TraceBrief loadTop(long startSecondTimeBucket, long endSecondTimeBucket, long minDuration, long maxDuration,
        String operationName,
        String traceId, int applicationId, int limit, int from, TraceState traceState, QueryOrder queryOrder) {
        logger.debug("startSecondTimeBucket: {}, endSecondTimeBucket: {}, minDuration: {}, " +
                "maxDuration: {}, operationName: {}, traceId: {}, applicationId: {}, limit: {}, from: {}, traceState: {}, queryOrder: {}",
            startSecondTimeBucket, endSecondTimeBucket, minDuration,
            maxDuration, operationName, traceId, applicationId, limit, from, traceState, queryOrder);

        String[] globalTraceIndex = EsIndexSuffixUtil.getEsIndexByDate(GlobalTraceTable.TABLE, startSecondTimeBucket, endSecondTimeBucket);
        String[] segmentDurationIndex = EsIndexSuffixUtil.getEsIndexByDate(SegmentDurationTable.TABLE, startSecondTimeBucket, endSecondTimeBucket);

        TraceBrief traceBrief;
        if (StringUtils.isNotEmpty(traceId)) {
            List<String> segmentIds = globalTraceUIDAO.getSegmentIds(traceId, globalTraceIndex);
            if (CollectionUtils.isEmpty(segmentIds)) {
                return new TraceBrief();
            }
            traceBrief = segmentDurationUIDAO.loadTop(segmentDurationIndex, startSecondTimeBucket, endSecondTimeBucket, minDuration, maxDuration, operationName, applicationId, limit, from, traceState, queryOrder, segmentIds.toArray(new String[0]));
        } else {
            traceBrief = segmentDurationUIDAO.loadTop(segmentDurationIndex, startSecondTimeBucket, endSecondTimeBucket, minDuration, maxDuration, operationName, applicationId, limit, from, traceState, queryOrder);
        }

        traceBrief.getTraces().forEach(trace -> {
            List<String> traceIds = globalTraceUIDAO.getGlobalTraceId(trace.getSegmentId(), globalTraceIndex);
            trace.getTraceIds().addAll(traceIds);
        });

        return traceBrief;
    }
}
