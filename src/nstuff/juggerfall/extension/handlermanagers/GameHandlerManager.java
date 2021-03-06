package nstuff.juggerfall.extension.handlermanagers;

import nstuff.juggerfall.extension.handlers.game.*; /**
 * Created by 804129 on 07.08.14.
 */
public class GameHandlerManager extends AbstractHandlerManager {
    public static final String RequestName_VipSpawned = "vipSpawned";

    public static final String RequestName_BaseSpawned = "baseSpawned";

    public static final String RequestName_NextRoom = "nextRoom";

    public static final String RequestName_NextRoute = "nextRoute";

    public static final String RequestName_GameRuleArrived = "gameRuleArrived";

    public static final String RequestName_GameRuleDamageBase = "gameRuleDamageBase";

    public static final String RequestName_UpdateConquestPoint = "updateConquestPoint";

    public static final String RequestName_BossHit = "bossHit";

    public static final String RequestName_LastWave = "lastWave";

    public static final String RequestName_MapData = "mapData";

    public static final String RequestName_GamePointData = "gamePointData";

@Override
    public void init() {

    extension.addClientHandler(RequestName_VipSpawned, VipSpawnedHandler.class);

    extension.addClientHandler(RequestName_NextRoute, NextRouteHandler.class);

    extension.addClientHandler(RequestName_NextRoom, NextRoomHandler.class);

    extension.addClientHandler(RequestName_GameRuleArrived, GameRuleArrived.class);

    extension.addClientHandler(RequestName_BaseSpawned, BaseSpawnedHandler.class);

    extension.addClientHandler(RequestName_GameRuleDamageBase, GameRuleDamageBase.class);

    extension.addClientHandler(RequestName_UpdateConquestPoint, UpdateConquestPoint.class);

     extension.addClientHandler(RequestName_BossHit, BossHitHandler.class);

    extension.addClientHandler(RequestName_LastWave, LastWaveHandler.class);

    extension.addClientHandler(RequestName_MapData, MapDataHandler.class);

    extension.addClientHandler(RequestName_GamePointData, GamePointData.class);
    }

}
