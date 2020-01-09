/*
 * Copyright 2020 Winfried Klum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wkl.copper.full.engine;

import org.copperengine.core.persistent.PostgreSQLDialect;
import org.copperengine.management.model.AuditTrailInfo;
import org.copperengine.management.model.AuditTrailInstanceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FIX for wrong audit trail message type
 */
public class PostgreSQLDialectFix extends PostgreSQLDialect {

    private static final Logger logger = LoggerFactory.getLogger(PostgreSQLDialectFix.class);

    public PostgreSQLDialectFix() {
        super();
    }

    @Override
    public List<AuditTrailInfo> queryAuditTrailInstances(AuditTrailInstanceFilter filter, Connection con) throws SQLException {
        logger.debug("queryAuditTrailInstances started with filter={}", filter);

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT SEQ_ID, TRANSACTION_ID, CONVERSATION_ID, CORRELATION_ID, OCCURRENCE, LOGLEVEL, CONTEXT, INSTANCE_ID, MESSAGE_TYPE ");

        if (filter.isIncludeMessages()) {
            sql.append(", LONG_MESSAGE");
        }

        final List<Object> params = new ArrayList<>();
        appendAuditTrailQueryBase(sql, params, filter);

        if (filter.getOffset() > 0 && filter.getMax() > 0) {
            addLimitationAndOffset(sql, filter.getMax(), filter.getOffset());
        } else if (filter.getMax() > 0) {
            addLimitation(sql, filter.getMax());
        }

        logger.debug("queryAuditTrailInstances: sql={}, params={}", sql, params);

        return processAuditResult(sql.toString(), params, con, filter.isIncludeMessages());
    }

    private static List<AuditTrailInfo> processAuditResult(String sql, List<Object> params, Connection con, boolean loadMessage) throws SQLException {
        List<AuditTrailInfo> result = new ArrayList();
        PreparedStatement pStmtQueryWFIs = con.prepareStatement(sql.toString());

        try {
            for (int i = 1; i <= params.size(); ++i) {
                pStmtQueryWFIs.setObject(i, params.get(i - 1));
            }

            ResultSet rs = pStmtQueryWFIs.executeQuery();

            while (rs.next()) {
                try {
                    AuditTrailInfo auditTrailInfo = new AuditTrailInfo(rs.getLong("SEQ_ID"), rs.getString("TRANSACTION_ID"), rs.getString("CONVERSATION_ID"), rs.getString("CORRELATION_ID"), rs.getTimestamp("OCCURRENCE").getTime(), rs.getInt("LOGLEVEL"), rs.getString("CONTEXT"), rs.getString("INSTANCE_ID"), rs.getString("MESSAGE_TYPE"));
                    if (loadMessage) {
                        try {
                            //FIX this needs to extract a String and not a CLOB
                            String message = rs.getString("LONG_MESSAGE");
                            if ((int) message.length() > 0) {
                                auditTrailInfo.setMessage(message);
                            }
                        } catch (Exception var10) {
                            logger.info("Failed to parse AuditTrail Message");
                        }
                    }

                    result.add(auditTrailInfo);
                } catch (Exception var11) {
                    logger.error("decoding of '" + rs.getString("ID") + "' failed: " + var11.toString(), var11);
                }
            }
        } catch (Throwable var12) {
            if (pStmtQueryWFIs != null) {
                try {
                    pStmtQueryWFIs.close();
                } catch (Throwable var9) {
                    var12.addSuppressed(var9);
                }
            }

            throw var12;
        }

        if (pStmtQueryWFIs != null) {
            pStmtQueryWFIs.close();
        }

        return result;
    }

    private StringBuilder appendAuditTrailQueryBase(StringBuilder sql, List<Object> params, AuditTrailInstanceFilter filter) {
        sql.append(" FROM COP_AUDIT_TRAIL_EVENT WHERE 1=1 ");

        if (filter.getLevel() != null && filter.getLevel() > 0) {
            sql.append(" AND LOGLEVEL >= ? ");
            params.add(filter.getLevel());
        }
        if (!isBlank(filter.getCorrelationId())) {
            sql.append(" AND CORRELATION_ID = ? ");
            params.add(filter.getCorrelationId());
        }

        if (!isBlank(filter.getInstanceId())) {
            sql.append(" AND INSTANCE_ID = ? ");
            params.add(filter.getInstanceId());
        }

        if (!isBlank(filter.getConversationId())) {
            sql.append(" AND CONVERSATION_ID = ? ");
            params.add(filter.getConversationId());
        }

        if (!isBlank(filter.getTransactionId())) {
            sql.append(" AND TRANSACTION_ID = ? ");
            params.add(filter.getTransactionId());
        }

        if (filter.getOccurredFrom() != null) {
            sql.append(" AND OCCURRENCE >= ? ");
            params.add(new java.sql.Date(filter.getOccurredFrom().getTime()));
        }

        if (filter.getOccurredTo() != null) {
            sql.append(" AND OCCURRENCE <= ? ");
            params.add(new java.sql.Date(filter.getOccurredTo().getTime()));
        }

        return sql;
    }

}
