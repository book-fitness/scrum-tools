package net.scrumtools.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

@RestController
public class StompStats {
    @Autowired
    private WebSocketMessageBrokerStats stats;

    @GetMapping(value = "/stomp-stats", produces = "text/html")
    public String stompStats() {
        StringBuilder out = new StringBuilder();

        out.append("<h3>WebSocket + Stomp statistics</h3>");

        out.append("<table border=1>");

        addRow(out, "WebSocketSessionStatsInfo", stats.getWebSocketSessionStatsInfo());
        addRow(out, "StompSubProtocolStatsInfo", stats.getStompSubProtocolStatsInfo());
        addRow(out, "StompBrokerRelayStatsInfo", stats.getStompBrokerRelayStatsInfo());
        addRow(out, "SockJsTaskSchedulerStatsInfo", stats.getSockJsTaskSchedulerStatsInfo());
        addRow(out, "ClientInboundExecutorStatusInfo", stats.getClientInboundExecutorStatsInfo());
        addRow(out, "ClientOutboundExecutorStatsInfo", stats.getClientOutboundExecutorStatsInfo());
        addRow(out, "LoggingPeriod", stats.getLoggingPeriod());

        out.append("</table>");

        return out.toString();
    }

    private <T> void addRow(StringBuilder out, String title, T value) {
        out.append("<tr>")
                .append("<td>").append(title).append("</td>")
                .append("<td>").append(value).append("</td>")
           .append("</tr>");
    }
}
