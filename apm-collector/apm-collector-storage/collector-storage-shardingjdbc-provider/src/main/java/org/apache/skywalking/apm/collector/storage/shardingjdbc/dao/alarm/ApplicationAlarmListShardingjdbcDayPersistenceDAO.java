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

package org.apache.skywalking.apm.collector.storage.shardingjdbc.dao.alarm;

import org.apache.skywalking.apm.collector.client.shardingjdbc.ShardingjdbcClient;
import org.apache.skywalking.apm.collector.core.storage.TimePyramid;
import org.apache.skywalking.apm.collector.core.util.Const;
import org.apache.skywalking.apm.collector.storage.dao.alarm.IApplicationAlarmListDayPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.shardingjdbc.base.define.ShardingjdbcSqlEntity;
import org.apache.skywalking.apm.collector.storage.table.alarm.ApplicationAlarmList;
import org.apache.skywalking.apm.collector.storage.table.alarm.ApplicationAlarmListTable;

/**
 * @author linjiaqi
 */
public class ApplicationAlarmListShardingjdbcDayPersistenceDAO extends AbstractApplicationAlarmListShardingjdbcPersistenceDAO implements IApplicationAlarmListDayPersistenceDAO<ShardingjdbcSqlEntity, ShardingjdbcSqlEntity, ApplicationAlarmList> {

    public ApplicationAlarmListShardingjdbcDayPersistenceDAO(ShardingjdbcClient client) {
        super(client);
    }

    @Override protected String tableName() {
        return ApplicationAlarmListTable.TABLE + Const.ID_SPLIT + TimePyramid.Day.getName();
    }

    @Override
    public String getTableSuffix(ApplicationAlarmList data) {
        return "";
    }
}
