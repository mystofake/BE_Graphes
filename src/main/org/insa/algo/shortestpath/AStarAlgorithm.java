package org.insa.algo.shortestpath;

import java.util.HashMap;
import java.util.Iterator;

import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected HashMap<Integer, Label> getLabelHashMap()
    {
    	ShortestPathData data = getInputData();
    	
    	HashMap<Integer, Label> hashMap = new HashMap<Integer, Label>();
		
    	Iterator<Node> it = data.getGraph().iterator();
    	
		while(it.hasNext())
		{
			Node currentNode = it.next();
			LabelStar l = new LabelStar(currentNode, data);
			hashMap.put(currentNode.getId(), l);
		}
    	
    	return hashMap;
    }

    @Override
    protected Label copyLabel(Label other)
    {
    	return new LabelStar((LabelStar)other);
    }

}
