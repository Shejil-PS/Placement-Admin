package com.placements.handler.application;

import com.placements.dto.response.ApplicationResponse;
import com.placements.service.ApplicationService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ExportApplicationsHandler {

    private final ApplicationService service;

    public ExportApplicationsHandler(ApplicationService service) {
        this.service = service;
    }

    /**
     * GET /applications/export
     *
     * Required query param : placementId
     * Optional query params : jobId, status
     * Optional query param  : format=csv (default) | json
     *
     * Returns a CSV file download or JSON array depending on format param.
     */
    public void handle(RoutingContext ctx) {
        String placementId = ctx.queryParam("placementId").stream().findFirst().orElse(null);
        if (placementId == null || placementId.isBlank()) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(ApplicationResponse.error("placementId query param is required").encode());
            return;
        }

        String jobId  = ctx.queryParam("jobId").stream().findFirst().orElse(null);
        String status = ctx.queryParam("status").stream().findFirst().orElse(null);
        String format = ctx.queryParam("format").stream().findFirst().orElse("csv");

        service.exportApplications(placementId, jobId, status)
                .onSuccess(data -> {
                    if ("json".equalsIgnoreCase(format)) {
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.success(data).encode());
                    } else {
                        String csv = toCsv(data);
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "text/csv")
                                .putHeader("Content-Disposition",
                                        "attachment; filename=\"applications_" + placementId + ".csv\"")
                                .end(csv);
                    }
                })
                .onFailure(err ->
                        ctx.response()
                                .setStatusCode(500)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.error(err.getMessage()).encode()));
    }

    // ── CSV serializer ─────────────────────────────────────────────────────────

    private String toCsv(JsonArray data) {
        StringBuilder sb = new StringBuilder();

        // Header row — mirrors the document fields (formAnswers excluded for readability)
        sb.append("applicationId,studentId,rollNo,studentName,placementId,jobId,")
          .append("companyId,companyName,appliedDate,status,resumeUrl\n");

        for (int i = 0; i < data.size(); i++) {
            JsonObject doc = data.getJsonObject(i);
            sb.append(csvValue(doc.getString("applicationId"))).append(',')
              .append(csvValue(doc.getString("studentId"))).append(',')
              .append(csvValue(doc.getString("rollNo"))).append(',')
              .append(csvValue(doc.getString("studentName"))).append(',')
              .append(csvValue(doc.getString("placementId"))).append(',')
              .append(csvValue(doc.getString("jobId"))).append(',')
              .append(csvValue(doc.getString("companyId"))).append(',')
              .append(csvValue(doc.getString("companyName"))).append(',')
              .append(csvValue(doc.getString("appliedDate"))).append(',')
              .append(csvValue(doc.getString("status"))).append(',')
              .append(csvValue(doc.getString("resumeUrl")))
              .append('\n');
        }

        return sb.toString();
    }

    /** Wraps a cell value in double-quotes and escapes embedded quotes per RFC 4180. */
    private String csvValue(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}

