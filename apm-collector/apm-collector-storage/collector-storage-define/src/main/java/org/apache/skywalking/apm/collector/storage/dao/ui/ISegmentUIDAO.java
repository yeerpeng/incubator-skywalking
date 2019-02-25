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

package org.apache.skywalking.apm.collector.storage.dao.ui;

import org.apache.skywalking.apm.collector.storage.base.dao.DAO;
import org.apache.skywalking.apm.network.proto.TraceSegmentObject;

/**
 * Interface to be implemented for execute database query operation
 * from {@link org.apache.skywalking.apm.collector.storage.table.segment.SegmentTable}.
 *
 * @author peng-yongsheng
 * @see org.apache.skywalking.apm.collector.storage.table.segment.SegmentTable
 * @see org.apache.skywalking.apm.collector.storage.StorageModule
 */
public interface ISegmentUIDAO extends DAO {

    /**
     * <p>SQL as: select DATA_BINARY from SEGMENT where SEGMENT_ID = ${segmentId},
     *
     * @param segmentId argument to bind to the query
     * @return detail of segment which deserialize into proto buffer object,
     * {@link TraceSegmentObject#parseFrom(byte[])}
     */
    TraceSegmentObject load(String segmentId, String[] index);
}
