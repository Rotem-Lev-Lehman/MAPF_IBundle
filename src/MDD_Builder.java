import java.util.*;

public class MDD_Builder {

    /*
    public static MDD Build(Graph graph, Vertex start, List<Path> paths) {
        if (paths.size() == 0)
            return null;
        MDD mdd = new MDD(graph);
        MDD_Vertex mdd_start = new MDD_Vertex(start, mdd, 0, 0);
        HashMap<Vertex, MDD_Vertex> prev_vertexes = new HashMap<>();
        prev_vertexes.put(start, mdd_start);

        mdd.addMDD_Vertex(mdd_start);
        mdd.setStart_MDD_vertex(mdd_start);

        int totalTime = paths.get(0).size();

        for (Path path : paths) {
            if (path.size() != totalTime)
                throw new InputMismatchException("All paths must have the same size");
        }

        for (int i = 1; i < totalTime; i++) {
            HashMap<Vertex, MDD_Vertex> curr_vertexes = new HashMap<>();
            for (Path curr_path : paths) {
                Vertex prev_vertex = curr_path.get(i - 1);
                MDD_Vertex prev;// = new MDD_Vertex(prev_vertex,mdd,i-1,-1);
                Vertex curr_vertex = curr_path.get(i);
                //MDD_Vertex curr = new MDD_Vertex(curr_vertex, mdd, i, -1);
                prev = prev_vertexes.get(prev_vertex);
                if (prev != null) {
                    if (!curr_vertexes.containsKey(curr_vertex)) {
                        double cost_until_me = prev.getCost_until_me();
                        if (curr_vertex.equals(prev_vertex)) {
                            //stayed
                            cost_until_me += curr_vertex.getStay_cost();
                        } else {
                            Edge edge = prev_vertex.getEdgeTo(curr_vertex);
                            if (edge == null)
                                throw new UnsupportedOperationException();
                            cost_until_me += edge.getTravel_cost();
                        }

                        MDD_Vertex curr_mdd_vertex = new MDD_Vertex(curr_vertex, mdd, i, cost_until_me);
                        MDD_Edge mdd_edge = new MDD_Edge(prev, curr_mdd_vertex, mdd);
                        prev.addMDD_Edge(mdd_edge);
                        mdd.addMDD_Vertex(curr_mdd_vertex);
                        mdd.addMDD_Edge(mdd_edge);
                        curr_vertexes.put(curr_vertex, curr_mdd_vertex);
                    }
                    else{
                        MDD_Vertex curr_mdd_vertex = curr_vertexes.get(curr_vertex);
                        if(!prev.checkIfNeighbor(curr_mdd_vertex)){
                            MDD_Edge mdd_edge = new MDD_Edge(prev, curr_mdd_vertex, mdd);
                            prev.addMDD_Edge(mdd_edge);
                            mdd.addMDD_Edge(mdd_edge);
                        }
                    }
                } else
                    throw new UnsupportedOperationException();
            }
            prev_vertexes = curr_vertexes;
        }

        if (prev_vertexes.size() != 1) {
            throw new UnsupportedOperationException("There can be only 1 goal state");
        }
        mdd.setGoal_MDD_vertex((MDD_Vertex)prev_vertexes.values().toArray()[0]);
        mdd.setTotal_time(totalTime);
        return mdd;
    }
    */

    public static MDD Build(Graph graph, Vertex goal, List<SearchingVertex> goalVertexes) {
        if (goalVertexes.size() == 0)
            return null;
        MDD mdd = new MDD(graph);

        SearchingVertex tempGoal = goalVertexes.get(0);
        double costForGoal = tempGoal.getG();
        int totalTime = 0;
        while (tempGoal != null){
            totalTime++;
            tempGoal = tempGoal.getPrev();
        }

        MDD_Vertex mdd_goal = new MDD_Vertex(goal, mdd, totalTime - 1, costForGoal);
        HashMap<Vertex, MDD_Vertex> next_vertexes = new HashMap<>();
        next_vertexes.put(goal, mdd_goal);

        mdd.addMDD_Vertex(mdd_goal);
        mdd.setGoal_MDD_vertex(mdd_goal);

        for (int i = 0; i < goalVertexes.size(); i++){
            SearchingVertex curr = goalVertexes.get(i);
            if (curr.getG() != costForGoal)
                throw new InputMismatchException("All goals must have the same G");
            //goalVertexes.set(i, curr.getPrev());
        }


        for (int i = totalTime - 2; i >= 0; i--) {
            HashMap<Vertex, MDD_Vertex> curr_vertexes = new HashMap<>();

            for (int j = 0; j < goalVertexes.size(); j++) {
                SearchingVertex next_searching_vertex = goalVertexes.get(j);
                Vertex next_vertex = next_searching_vertex.getVertex();
                MDD_Vertex next;// = new MDD_Vertex(prev_vertex,mdd,i-1,-1);
                SearchingVertex curr_searching_vertex = next_searching_vertex.getPrev();
                Vertex curr_vertex = curr_searching_vertex.getVertex();

                next = next_vertexes.get(next_vertex);
                if (next != null) {
                    MDD_Vertex curr_mdd_vertex = curr_vertexes.get(curr_vertex);
                    if (curr_mdd_vertex == null) {
                        double cost_until_me = curr_searching_vertex.getG();

                        curr_mdd_vertex = new MDD_Vertex(curr_vertex, mdd, i, cost_until_me);
                        MDD_Edge mdd_edge = new MDD_Edge(curr_mdd_vertex, next, mdd);
                        curr_mdd_vertex.addMDD_Edge(mdd_edge);
                        mdd.addMDD_Vertex(curr_mdd_vertex);
                        mdd.addMDD_Edge(mdd_edge);
                        curr_vertexes.put(curr_vertex, curr_mdd_vertex);
                    }
                    else{
                        if(!curr_mdd_vertex.checkIfNeighbor(next)){
                            MDD_Edge mdd_edge = new MDD_Edge(curr_mdd_vertex, next, mdd);
                            curr_mdd_vertex.addMDD_Edge(mdd_edge);
                            mdd.addMDD_Edge(mdd_edge);
                        }
                    }
                } else
                    throw new UnsupportedOperationException();

                goalVertexes.set(j, curr_searching_vertex);
            }

            next_vertexes = curr_vertexes;
        }

        if (next_vertexes.size() != 1) {
            throw new UnsupportedOperationException("There can be only 1 start state");
        }
        mdd.setStart_MDD_vertex((MDD_Vertex)next_vertexes.values().toArray()[0]);
        mdd.setTotal_time(totalTime);
        return mdd;
    }

    public static void normalizeTotalTime(MDD mdd, int new_total_time) {
        int current_mdd_time = mdd.getTotal_time();
        if (current_mdd_time > new_total_time)
            throw new UnsupportedOperationException("The new time must be more then the existing total time");

        MDD_Vertex prev = mdd.getGoal_MDD_vertex();
        Vertex goal = prev.getOriginal_vertex();
        for (int i = current_mdd_time; i < new_total_time; i++) {
            MDD_Vertex curr = new MDD_Vertex(goal, mdd, prev.getTime() + 1, prev.getCost_until_me());
            MDD_Edge edge = new MDD_Edge(prev, curr, mdd);
            prev.addMDD_Edge(edge);
            mdd.addMDD_Vertex(curr);
            mdd.addMDD_Edge(edge);

            prev = curr;
        }
        mdd.setGoal_MDD_vertex(prev);
        mdd.setTotal_time(new_total_time);
    }

}
