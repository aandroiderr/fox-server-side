package nstuff.juggerfall.extension.view;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.gameplay.SimpleDestroyableView;
import nstuff.juggerfall.extension.other.SimpleNetView;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ViewManager {
	private Map<Integer ,NetView> allView = new ConcurrentHashMap<Integer ,NetView>();

    public List<Integer> deleteSceneView = new ArrayList<Integer>();

    public MainExtension extension;



    public void deleteView(Integer id) {
      deleteView(null, id);

    }
    public void deleteView(User sender, Integer id) {
        NetView view  = allView.get(id);
        if(view!=null) {
            view.delete();
            if (view instanceof SimpleNetView) {
                SimpleNetView simple = (SimpleNetView) view;
                switch (simple.prefType) {
                    case EGG:
                        extension.gameRule.director.RemoveEgg((QueenEgg) simple);
                        break;
                }
            }
            allView.remove(id);
        }
        extension.deleteView(sender, id);

    }

    public void addView(NetView view) {
        extension.trace(ExtensionLogLevel.INFO,view.getClass()+" "+view.id);
        allView.put(view.id,view);
        view.manager = this;
    }
    public NetView getView(int id){
        return allView.get(id);
    }

    public void reload(){
        for(NetView view : allView.values()){
            view.clearRef();
        }
        allView.clear();
        deleteSceneView.clear();

    }

    public void deleteViewLocal(int id) {
        NetView view  = allView.get(id);
        if(view==null) {
            view.deleteLocal();
        }
        allView.remove(id);
    }


    public ISFSArray getAllViewForStart() {
        SFSArray sfsa = new SFSArray();
        for(NetView view : allView.values()){
            ISFSObject res = new SFSObject();
            switch(view.viewType){
                case  NET_VIEW_TYPE_PAWN:
                    Pawn pawn =(Pawn)view;
                    res.putClass("pawn",pawn.sirPawn);
                    res.putIntArray("stims",  pawn.stims );
                    if(pawn.owner!=null){
                        res.putInt("ownerId",pawn.owner.getId());
                    }
                    res.putBool("isAI", pawn.isAi);
                    if(pawn.isAi&&pawn.owner==null){
                        res.putInt("group",pawn.aiSwarmId);
                        res.putInt("home",pawn.aihome);
                    }
                    ISFSArray weapons = new SFSArray();
                    for(Weapon weapon : pawn.weapons){
                        weapons.addClass(weapon.sirWeapon);
                    }
                    res.putSFSArray("weapons",weapons);
                    res.putSFSArray("armors",pawn.armors);
                    break;

                case  NET_VIEW_TYPE_SIMPLE:
                    SimpleNetView simpelView =(SimpleNetView)view;
                    res.putClass("model",simpelView.model);
                    simpelView.addData(res);
                    break;
                case NET_VIEW_TYPE_SIMPLE_DESTROYABLE:
                    SimpleDestroyableView simpelDestView =(SimpleDestroyableView)view;
                    res.putClass("model",simpelDestView.model);

                    break;
            }

            sfsa.addSFSObject(res);

        }
        return sfsa;
    }

    public  SFSArray removePlayerView(int ownerId){
        SFSArray sfsa = new SFSArray();
        Iterator<Map.Entry<Integer ,NetView>> iterator = allView.entrySet().iterator();
        while(iterator.hasNext()){

            Map.Entry<Integer ,NetView> entry = iterator.next();
            try {
                NetView view =entry.getValue();
                if(view.needDelete(ownerId)){

                    view.deleteLocal();
                    sfsa.addInt(view.id);
                    iterator.remove();
                }

            }catch (Exception e){
               extension.trace("removePlayerView Exception",e);
            }

        }
        return sfsa;
    }

    public boolean hasView(int id) {

       return  allView.containsKey(id);
    }

    public void deleteSceneView(User sender, int id) {
        deleteView(sender, id);
        deleteSceneView.add(id);
    }

    public List<Integer> getAllDeleteSceneViewForStart() {
       return deleteSceneView;
    }


}
