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

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected HashMap<Integer, Label> getLabelHashMap()
    {
    	HashMap<Integer, Label> hashMap = new HashMap<Integer, Label>();
		
    	Iterator<Node> it = data.getGraph().iterator();
    	
		while(it.hasNext())
		{
			Node currentNode = it.next();
			Label l = new Label(currentNode);
			hashMap.put(currentNode.getId(), l);
		}
    	
    	return hashMap;
    }
    
    protected Label copyLabel(Label other)
    {
    	return new Label(other);
    }
    
    /*private Label replaceLabel(double newCost, Label oldLabel, HashMap<Integer, Label> labelHashMap, BinaryHeap<Label> labelHeap)
    {
    	Label newLabel = new Label(oldLabel);
    	labelHeap.insert(newLabel);
    	labelHashMap.put(newLabel.getNode().getId(), newLabel);
    	return newLabel;
    }*/

    @Override
    protected ShortestPathSolution doRun() {

		// Retrieve the graph.
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		
		BinaryHeap<Label> labels = new BinaryHeap<Label>();
		
		HashMap<Integer, Label> allLabels = getLabelHashMap();
		
		//int cptIteration = 0;
		
		Node n = graph.get(data.getOrigin().getId());
		Label originLabel = allLabels.get(n.getId());
		originLabel.setPreviousNode(n);
		originLabel.setCost(0);
		labels.insert(originLabel);
		
		notifyOriginProcessed(data.getOrigin());
		
		
		do
		{
			//++cptIteration;
			
			Label previousLabel = labels.deleteMin();
			if(previousLabel.getNode().equals(data.getDestination()))
			{
				notifyDestinationReached(data.getDestination());
				break;
			}
			
			Iterator<Arc> successors = previousLabel.getNode().iterator();
			
			while(successors.hasNext())
			{
				Arc arc = successors.next();
				if (!data.isAllowed(arc)) {
					continue;
				}
				Label label = allLabels.get(arc.getDestination().getId());
				
				if(label.isMarked())
				{
					continue;
				}

				double w = data.getCost(arc) + previousLabel.getCost();

				if (Double.isInfinite(label.getCost()) && Double.isFinite(w)) {
					notifyNodeReached(arc.getDestination());
				}

				if(w < label.getCost())
				{
					Label newLabel = copyLabel(label);
					newLabel.setCost(w);
					newLabel.setPreviousNode(previousLabel.getNode());
					labels.insert(newLabel);
					allLabels.put(label.getNode().getId(), newLabel);
				}
			}

			previousLabel.mark();
			
			notifyNodeMarked(previousLabel.getNode());
		}while(!labels.isEmpty());

		ShortestPathSolution solution = null;
		Label destLabel = allLabels.get(data.getDestination().getId());

		if(destLabel.getPreviousNode() == null)
		{
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		else
		{
			List<Node> ret = new ArrayList<Node>();
			
			do
			{
				ret.add(destLabel.getNode());
				destLabel = allLabels.get(destLabel.getPreviousNode().getId());
			}while(!destLabel.getNode().equals(data.getOrigin()));
			
			ret.add(data.getOrigin());
			Collections.reverse(ret);
			Path path = Path.createShortestPathFromNodes(graph, ret);
			
			solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
		}

		return solution;
	}

}

