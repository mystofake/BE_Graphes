package org.insa.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
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

    public static void main(String[] args) throws Exception {
/*// test afficher path
        // Visit these directory to see the list of available files on Commetud.
        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        String pathName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        Graph graph = reader.read();

        // Create the drawing:
        Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);
      
        // TODO: Create a PathReader.
        //PathReader pathReader = null;
        PathReader pathReader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

        // TODO: Read the path.
        Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        drawing.drawPath(path);
        */
        
        
     // Visit these directory to see the list of available files on Commetud.
        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr";

        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        Graph graph = reader.read();
        System.out.println(ArcInspectorFactory.getAllFilters().toArray()[0]);
        //ShortestPathAlgorithm sp = new ShortestPathAlgorithm();
        ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        ShortestPathData spd = new ShortestPathData(graph, graph.get(0), graph.get(300), arcInspector);
        AStarAlgorithm astar = new AStarAlgorithm(spd);
        ShortestPathSolution solution = astar.run();


        // Create the drawing:
        Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);

        // TODO: Read the path.
        Path path = solution.getPath();

        // TODO: Draw the path.
        drawing.drawPath(path);
        TimeUnit.SECONDS.sleep(10);
    }

}
