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

package org.apache.skywalking.apm.collector.storage.es.define;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.skywalking.apm.collector.storage.es.base.define.ElasticSearchColumnDefine;
import org.apache.skywalking.apm.collector.storage.es.base.define.ElasticSearchTableDefine;
import org.apache.skywalking.apm.collector.storage.table.segment.SegmentDurationTable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author peng-yongsheng
 */
public class SegmentDurationEsTableAfterOneDefine extends ElasticSearchTableDefine {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public SegmentDurationEsTableAfterOneDefine() {
        super(SegmentDurationTable.TABLE + "_" + DATE_FORMAT.format(DateUtils.addDays(new Date(), 1)));
    }

    @Override public int refreshInterval() {
        return 10;
    }

    @Override public void initialize() {
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.SEGMENT_ID, ElasticSearchColumnDefine.Type.Keyword.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.APPLICATION_ID, ElasticSearchColumnDefine.Type.Integer.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.SERVICE_NAME, ElasticSearchColumnDefine.Type.Text.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.TRACE_ID, ElasticSearchColumnDefine.Type.Keyword.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.DURATION, ElasticSearchColumnDefine.Type.Long.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.START_TIME, ElasticSearchColumnDefine.Type.Long.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.END_TIME, ElasticSearchColumnDefine.Type.Long.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.IS_ERROR, ElasticSearchColumnDefine.Type.Integer.name()));
        addColumn(new ElasticSearchColumnDefine(SegmentDurationTable.TIME_BUCKET, ElasticSearchColumnDefine.Type.Long.name()));
    }
}
