package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.models.PVPHuntGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class HuntGameRule extends  GameRule {

    Map<String, Object> scoreTable;

    private static final String BOSS_DAMAGE = "boss_damage";

    private static final String ROBOT_ENTER = "robot_enter";

    @Override
    public void Kill(int team) {

    }

    @Override
    public void Spawn(int team) {

    }

    @Override
    public void PlayerDeath(int team) {

    }

    @Override
    public void AIDeath(Pawn death) {

    }

    @Override
    public void AIDeath(Pawn dead, int team) {
        team =team-1;
        if(scoreTable.containsKey(dead.type)){
            teamScore[team]+= (Integer)scoreTable.get(dead.type);
        }else{
            teamScore[team]++;
        }

        CheckGameEnd();
    }


    @Override
    public void Init(Room room) {
        super.Init(room);
        int teamCount = room.getVariable("teamCount").getIntValue();

        teamScore = new int[teamCount];
        canUseRobot = true;
        extension.trace("ROOM START");
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        scoreTable = (Map<String, Object>) object.getClass("huntTable");
    }

    @Override
    public void Reload() {
        super.Reload();


    }
    @Override
    protected void  CheckGameEnd(){

        extension.UpdateGame();
    }
    @Override
    public GameRuleModel GetModel(){
        PVPHuntGameRuleModel model = new PVPHuntGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.teamScore = new ArrayList<Integer>();
        for (int aTeamScore : teamScore) {
            model.teamScore.add(aTeamScore);
        }
        return model;
    }

    @Override
    public void RobotEnter(int team) {
        if(scoreTable.containsKey(ROBOT_ENTER)){
            teamScore[team]+= (Integer)scoreTable.get(ROBOT_ENTER);
        }else{
            teamScore[team]++;
        }
        CheckGameEnd();
    }

    public void BossDamage(int team) {
        team =team-1;
        if(scoreTable.containsKey(BOSS_DAMAGE)){
            teamScore[team]+= (Integer)scoreTable.get(BOSS_DAMAGE);
        }else{
            teamScore[team]++;
        }
        CheckGameEnd();
    }

    public void LastWave() {
        GameFinish();
    }
}
