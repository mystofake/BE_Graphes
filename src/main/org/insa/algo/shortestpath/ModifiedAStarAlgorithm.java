package org.insa.algo.shortestpath;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;

import org.insa.graph.Node;
import org.insa.graph.Path;

public class ModifiedAStarAlgorithm extends AStarAlgorithm {

    public ModifiedAStarAlgorithm(ShortestPathData data) {
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
			ModifiedLabelStar l = new ModifiedLabelStar(currentNode, data);
			hashMap.put(currentNode.getId(), l);
		}
    	
    	return hashMap;
    }

    @Override
    protected Label copyLabel(Label other)
    {
    	return new ModifiedLabelStar((ModifiedLabelStar)other);
    }

}

