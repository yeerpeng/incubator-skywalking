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

package org.apache.skywalking.apm.collector.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ye-erpeng
 */
public class EsIndexSuffixUtil {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");

    public static String[] getEsIndexByDate(String tableName, Long startTime, Long endTime) {
        try {
            Date starts = FORMATTER.parse(startTime.toString().substring(0, 8));
            Date ends = FORMATTER.parse(endTime.toString().substring(0, 8));
            long length = (ends.getTime() - starts.getTime()) / (24 * 60 * 60 * 1000);
            if (length < 1) {
                length = 0;
            }
            String[] rs = new String[(int)length + 1];
            int i = 0;
            Date tmp = FORMATTER.parse(endTime.toString().substring(0, 8));
            while (tmp.getTime() - starts.getTime() >= 0) {
                rs[rs.length - 1 - i] = tableName + "_" + FORMATTER.format(tmp);
                tmp = new Date(tmp.getTime() - 24 * 60 * 60 * 1000);
                i++;
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
