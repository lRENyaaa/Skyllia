package fr.euphyllia.skyllia.database;

import fr.euphyllia.skyllia.api.InterneAPI;
import fr.euphyllia.skyllia.api.configuration.DatabaseType;
import fr.euphyllia.skyllia.configuration.ConfigToml;
import fr.euphyllia.skyllia.database.mariadb.exec.*;
import fr.euphyllia.skyllia.database.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IslandQuery {

    private final Logger logger = LogManager.getLogger(IslandQuery.class);
    private final InterneAPI api;
    private final String databaseName;
    private IslandDataQuery islandDataQuery;
    private IslandUpdateQuery islandUpdateQuery;
    private IslandWarpQuery islandWarpQuery;
    private IslandMemberQuery islandMemberQuery;
    private IslandPermissionQuery islandPermissionQuery;

    public IslandQuery(InterneAPI api, String databaseName) {
        this.api = api;
        this.databaseName = databaseName;
    }

    private void init() {
        DatabaseType databaseType = ConfigToml.mariaDBConfig.databaseType();
        switch (databaseType) {
            case MARIADB -> {
                this.islandDataQuery = new MariaDBIslandData(api, databaseName);
                this.islandUpdateQuery = new MariaDBIslandUpdate(api, databaseName);
                this.islandWarpQuery = new MariaDBIslandWarp(api, databaseName);
                this.islandMemberQuery = new MariaDBIslandMember(api, databaseName);
                this.islandPermissionQuery = new MariaDBIslandPermission(api, databaseName);
            }
            case SQLITE -> {
            }
        }
    }

    public IslandDataQuery getIslandDataQuery() {
        return this.islandDataQuery;
    }

    public IslandUpdateQuery getIslandUpdateQuery() {
        return this.islandUpdateQuery;
    }

    public IslandWarpQuery getIslandWarpQuery() {
        return this.islandWarpQuery;
    }

    public IslandMemberQuery getIslandMemberQuery() {
        return this.islandMemberQuery;
    }

    public IslandPermissionQuery getIslandPermissionQuery() {
        return this.islandPermissionQuery;
    }
}
