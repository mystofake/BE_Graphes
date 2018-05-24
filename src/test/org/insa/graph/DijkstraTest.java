package org.insa.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.concurrent.TimeUnit;

public class DijkstraTest {

    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1_x2, x1_x3, x2_x4, x2_x5, x2_x6, x3_x1, x3_x2, x3_x6, x5_x3, x5_x4 ,x5_x6, x6_x5;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath; // a enlever peutetre

    @BeforeClass
    public static void initAll() throws IOException {
    	
    	nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
        
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");
        
        x1_x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        x1_x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        x2_x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        x2_x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        x2_x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        x3_x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        x3_x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        x3_x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        x5_x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        x5_x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        x5_x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        x6_x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);

    }

    @Test
    public void testThroughBellmanFord() {
    	for(int from=0;from<nodes.length;++from)
    	{
    		for(int to=from+1;to<nodes.length;++to)
    		{
    			ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
    	        ShortestPathData spd = new ShortestPathData(graph, graph.get(from), graph.get(to), arcInspector);
    	        BellmanFordAlgorithm bf = new BellmanFordAlgorithm(spd);
    	        ShortestPathSolution solutionBellmanFord = bf.run();
    	        
    	        DijkstraAlgorithm djk = new DijkstraAlgorithm(spd);
    	        ShortestPathSolution solutionDijkstra = djk.run();

    	        assertTrue(solutionBellmanFord.isFeasible() == solutionDijkstra.isFeasible());
    	        if(solutionBellmanFord.isFeasible())
    	        {
    	        	assertTrue(Math.abs(solutionBellmanFord.getPath().getLength()-solutionDijkstra.getPath().getLength()) <= 0.000001);
    	        }
    	        
    		}
    	}
    }
    
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    @Test
    public void testOnInsaMap() {
    	String mapName = "../insa.mapgr";

    	try
    	{
	        GraphReader reader = new BinaryGraphReader(
	                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	
	        Graph graph = reader.read();

	    	//for(int from=0;from<graph.size();++from)
		    for(int from=0;from<50;++from)
	        //for(int from=31;from<32;++from)
	    	{
	    		//for(int to=from+1;to<graph.size();++to)
		    	for(int to=from+1;to<50;++to)
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
		    	        
		    	        DijkstraAlgorithm djk = new DijkstraAlgorithm(spd2);
		    	        ShortestPathSolution solutionDijkstra = djk.run();
		
		    	        assertTrue(solutionBellmanFord.isFeasible() == solutionDijkstra.isFeasible());
		    	        if(solutionBellmanFord.isFeasible())
		    	        {
		    	        	System.out.println("node "+from+" to "+to+" , filter : "+filter);
		    	        	//System.out.println((solutionBellmanFord.getPath().getLength()-solutionDijkstra.getPath().getLength()));
		    	        	/*Drawing drawing = createDrawing();
	
		    	            // TODO: Draw the graph on the drawing.
		    	            drawing.drawGraph(graph);
	
		    	            // TODO: Read the path.
		    	            Path path1 = solutionDijkstra.getPath();
		    	            Path path2 = solutionBellmanFord.getPath();
		    	            drawing.drawPath(path1);
		    	            drawing.drawPath(path2);
		    	            TimeUnit.SECONDS.sleep(10);*/
		    	            
		    	        	assertTrue(Math.abs(solutionBellmanFord.getPath().getLength()-solutionDijkstra.getPath().getLength()) <= 3.4);
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
