package org.insa.algo.shortestpath;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;

import org.insa.graph.Node;
import org.insa.graph.Path;

import java.util.concurrent.TimeUnit;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

		// Retrieve the graph.
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();

		final int nbNodes = graph.size();
		
		//Label label = new Label(graph.get(0));

		/*BinaryHeap<Label> allLabels = new BinaryHeap<Label>();
		BinaryHeap<Label> currentLabels = new BinaryHeap<Label>();
		Iterator<Node> it = graph.iterator();
		
		while(it.hasNext())
		{
			Node n = it.next();
			Label label = new Label(n);
			allLabels.insert(label
import java.util.ArrayList;);
			if(n.equals(data.getOrigin()))
			{
				label.setCost(0.0);
				currentLabels.insert(label);
			}
		}*/
		
		boolean debugVar = false;
		
		BinaryHeap<Label> labels = new BinaryHeap<Label>();
		Node n = graph.get(data.getOrigin().getId());
		Label originLabel = new Label(n);
		originLabel.setPreviousNode(n);
		originLabel.setCost(0);
		labels.insert(originLabel);
		
		HashMap<Integer, Label> allLabels = new HashMap<Integer, Label>();
		Iterator<Node> it = graph.iterator();
		
		int cptIteration = 0;
		
		while(it.hasNext())
		{
			Node currentNode = it.next();
			Label l = new Label(currentNode);
			Integer id = currentNode.getId();
			allLabels.put(id, l);
		}
		
		notifyOriginProcessed(data.getOrigin());
		
		
		do
		{
			++cptIteration;
			
			Label previousLabel = labels.deleteMin();
			previousLabel.setInHeap(false);
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
					label.setCost(w);
					label.setPreviousNode(previousLabel.getNode());
					if(!label.isInHeap())
					{
						label.setInHeap(true);
						labels.insert(label);
					}
					labels.// le pb est la ; percolate etc
				}
			}
			
			System.out.println("etape "+cptIteration+" on point "+previousLabel.getNode().getId()+" (cost : "+previousLabel.getCost()+")");
			labels.debug_print();
			if(!labels.firstIsSmallest())
			{
				System.out.println("oula");
				/*try {
					TimeUnit.SECONDS.sleep(1);
				}
				catch(Exception e)
				{
					
				}*/
			}
			
			/*if(previousLabel.getNode().getId() == 118)
			{
				debugVar = true;
			}
			if(debugVar)
			{
				try {
					TimeUnit.SECONDS.sleep(1);
				}
				catch(Exception e)
				{
					
				}
			}*/

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

