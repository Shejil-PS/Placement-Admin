package com.placements.config;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.Objects;

/**
 * Singleton factory for the Vert.x {@link MongoClient}.
 *
 * <p>Vert.x {@link MongoClient#createShared} ties the underlying connection pool
 * to a named data-source key. All calls with the same data-source name reuse the
 * same pool, making it safe to call {@link #getClient()} from multiple verticles
 * or classes without creating duplicate pools.
 *
 * <p>Lifecycle:
 * <ol>
 *   <li>Call {@link #init(Vertx, AppConfig)} once at application startup
 *       (typically from {@code MainVerticle.start}).</li>
 *   <li>Inject or call {@link #getClient()} wherever a {@link MongoClient} is needed.</li>
 *   <li>Call {@link #close()} during graceful shutdown.</li>
 * </ol>
 *
 * <p>Thread safety: {@code init} and {@code close} are intended to be called from
 * the Vert.x event-loop context during startup/shutdown only. {@code getClient()}
 * is safe to call from any thread after {@code init} completes.
 *
 * <p>Required Maven dependency (add to pom.xml):
 * <pre>
 *   &lt;dependency&gt;
 *     &lt;groupId&gt;io.vertx&lt;/groupId&gt;
 *     &lt;artifactId&gt;vertx-mongo-client&lt;/artifactId&gt;
 *     &lt;version&gt;4.5.13&lt;/version&gt;
 *   &lt;/dependency&gt;
 * &lt;/pre&gt;
 */
public final class MongoConfig {

    /** Shared pool name — all verticles referencing this name share one connection pool. */
    private static final String DATA_SOURCE_NAME = "placement-mongo-pool";

    private static volatile MongoClient instance;

    // Prevent instantiation
    private MongoConfig() {}

    // ── Lifecycle ──────────────────────────────────────────────────────────────

    /**
     * Initialises the shared {@link MongoClient} from the provided {@link AppConfig}.
     * Idempotent — calling this more than once with the same Vert.x instance is safe
     * because Vert.x's shared-pool mechanism deduplicates by data-source name.
     *
     * @param vertx  the Vert.x instance
     * @param config the parsed application configuration
     * @return the shared {@link MongoClient}
     * @throws IllegalStateException if called after {@link #close()} on the same JVM run
     */
    public static synchronized MongoClient init(Vertx vertx, AppConfig config) {
        Objects.requireNonNull(vertx,  "vertx must not be null");
        Objects.requireNonNull(config, "config must not be null");

        if (instance != null) {
            return instance;      // already initialised — return existing pool
        }

        JsonObject mongoConfig = buildMongoConfig(config);
        instance = MongoClient.createShared(vertx, mongoConfig, DATA_SOURCE_NAME);
        return instance;
    }

    /**
     * Returns the initialised {@link MongoClient}.
     *
     * @throws IllegalStateException if {@link #init} has not been called yet
     */
    public static MongoClient getClient() {
        MongoClient client = instance;
        if (client == null) {
            throw new IllegalStateException(
                    "MongoConfig has not been initialised. Call MongoConfig.init() at startup.");
        }
        return client;
    }

    /**
     * Closes the shared client and releases all pooled connections.
     * Call this during graceful shutdown before the JVM exits.
     */
    public static synchronized void close() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

    // ── Config builder ─────────────────────────────────────────────────────────

    /**
     * Builds the {@link JsonObject} that Vert.x MongoClient expects.
     *
     * <p>Vert.x MongoClient passes these values to the underlying MongoDB Java
     * driver via the connection string and additional options. Connection-string
     * options take precedence over individual fields for most driver parameters,
     * but the explicit fields below are still applied where the driver supports them.
     *
     * @see <a href="https://vertx.io/docs/vertx-mongo-client/java/">Vert.x MongoClient docs</a>
     */
    private static JsonObject buildMongoConfig(AppConfig config) {
        JsonObject appMongo = AppConfig.getMongoConfig();
        return new JsonObject()
                // Primary connection
                .put("connection_string",             appMongo.getString("connection_string", "mongodb://localhost:27017"))
                .put("db_name",                       appMongo.getString("db_name", "placement_db"))

                // Connection pool
                .put("maxPoolSize",                   appMongo.getInteger("maxPoolSize", 50))
                .put("minPoolSize",                   appMongo.getInteger("minPoolSize", 5))
                .put("maxConnectionIdleTime",         appMongo.getInteger("maxConnectionIdleTime", 30000))

                // Timeouts (milliseconds)
                .put("connectTimeoutMS",              appMongo.getInteger("connectTimeoutMS", 10000))
                .put("socketTimeoutMS",               appMongo.getInteger("socketTimeoutMS", 10000))
                .put("serverSelectionTimeoutMS",      appMongo.getInteger("serverSelectionTimeoutMS", 10000))

                // Write concern — "majority" guarantees acknowledged writes across replica sets
                .put("writeConcern",                  "MAJORITY")

                // Read preference — "primaryPreferred" falls back to secondary under primary failure
                .put("readPreference",                "primaryPreferred")

                // Keep-alive prevents silent TCP drops in cloud environments
                .put("socketKeepAlive",               true);
    }
}
