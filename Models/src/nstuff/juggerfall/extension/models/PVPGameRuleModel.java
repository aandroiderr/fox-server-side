package nstuff.juggerfall.extension.models;

import java.util.List;

/**
 * Created by 804129 on 03.08.14.
 */
public class PVPGameRuleModel extends GameRuleModel {
    public boolean isGameEnded;

    public List<Integer> teamScore;

    public List<Integer> teamKill;

    public float time;
}
