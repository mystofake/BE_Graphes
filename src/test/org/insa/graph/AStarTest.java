package org.insa.graph;

import org.insa.algo.shortestpath.AStarAlgorithm;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class AStarTest {

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1_x2, x1_x3, x2_x4, x2_x5, x2_x6, x3_x1, x3_x2, x3_x6, x5_x3, x5_x4 ,x5_x6, x6_x5;

    @Test
    public void testOnInsaMap() {
    	String mapName = "../insa.mapgr";

    	try
    	{
	        GraphReader reader = new BinaryGraphReader(
	                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	
	        Graph graph = reader.read();

	    	//for(int from=0;from<graph.size();++from)
		    for(int from=0;from<100;++from)
	        //for(int from=31;from<32;++from)
	    	{
	    		//for(int to=from+1;to<graph.size();++to)
		    	for(int to=from+1;to<100;++to)
	        	//for(int to=954;to<955;++to)
	    		{
		    		for(int filter = 0;filter < ArcInspectorFactory.getAllFilters().size();++filter)
		    		{
		    			ArcInspector arcInspector1 = ArcInspectorFactory.getAllFilters().get(filter);
		    	        ShortestPathData spd = new ShortestPathData(graph, graph.get(from), graph.get(to), arcInspector1);
		    			ArcInspector arcInspector2 = ArcInspectorFactory.getAllFilters().get(filter);
		    	        ShortestPathData spd2 = new ShortestPathData(graph, graph.get(from), graph.get(to), arcInspector2);
		    	        BellmanFordAlgorithm bf = new BellmanFordAlgorithm(spd);
		    	        ShortestPathSolution solutionBellmanFord = bf.run();
		    	        
		    	        AStarAlgorithm astar = new AStarAlgorithm(spd2);
		    	        ShortestPathSolution solutionAStar = astar.run();
		
		    	        assertTrue(solutionBellmanFord.isFeasible() == solutionAStar.isFeasible());
		    	        if(solutionBellmanFord.isFeasible())
		    	        {
		    	        	System.out.println("node "+from+" to "+to+" , filter : "+filter);
		    	            
		    	        	assertTrue(Math.abs(solutionBellmanFord.getPath().getLength()-solutionAStar.getPath().getLength()) <= 3.4);
		    	        }
		    		}
	    		}
	    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println("file insa.mapgr not found or cant read");
    	}
    }

}
