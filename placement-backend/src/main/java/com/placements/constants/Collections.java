package com.placements.constants;

/**
 * MongoDB collection name constants.
 *
 * <p>Use these instead of inline strings throughout repositories and services
 * to prevent typos and make refactoring collection names a single-point change.
 *
 * <pre>{@code
 * mongoClient.find(Collections.STUDENTS, query);
 * mongoClient.insert(Collections.APPLICATIONS, doc);
 * }</pre>
 */
public final class Collections {

    private Collections() {}

    public static final String STUDENTS     = "students";
    public static final String BATCHES      = "batches";
    public static final String COMPANIES    = "companies";
    public static final String PLACEMENTS   = "placements";
    public static final String APPLICATIONS = "applications";
}

