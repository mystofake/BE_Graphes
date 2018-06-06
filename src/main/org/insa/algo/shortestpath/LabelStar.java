package org.insa.algo.shortestpath;

import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;

public class LabelStar extends Label
{
	private double estimatedLeft;
	private boolean computed;
	private Node destination;
	private double maxSpeed;
	private String mode;
	
	LabelStar(Node n, ShortestPathData data)
	{
		super(n);
		this.destination = data.getDestination();
		this.maxSpeed = data.getGraph().getGraphInformation().getMaximumSpeed();
		this.mode = data.getMode().name();
		this.estimatedLeft = 0.0;
		this.computed = false;
		
	}
	
	/*LabelStar(Label other)
	{
		super(other);
		this.estimatedLeft = 0.0;
	}*/
	
	LabelStar(LabelStar other)
	{
		super(other);
		this.destination = other.destination;
		this.maxSpeed = other.maxSpeed;
		this.mode = other.mode;
		this.estimatedLeft = other.estimatedLeft;
		this.computed = other.computed;
	}
	
	public void computeEstimatedLeft()
	{
		if(this.mode == "TIME" && maxSpeed != GraphStatistics.NO_MAXIMUM_SPEED)
		{
			this.estimatedLeft = this.getNode().getPoint().distanceTo(this.destination.getPoint())/maxSpeed*3.6;
		}
		else
		{
			this.estimatedLeft = this.getNode().getPoint().distanceTo(this.destination.getPoint());
		}
		computed = true;
	}
	
	@Override
	public int compareTo(Label other) {
		if(!computed)
		{
			computeEstimatedLeft();
		}
		double cmp = Double.compare(this.getTotalCost(), other.getTotalCost());
		if(cmp > 0.0)
		{
			return 1;
		}
		if(cmp < 0.0)
		{
			return -1;
		}
		
		// equality
		cmp = Double.compare(this.getCost(), other.getCost());
		
		if(cmp > 0.0)
		{ // getCost1 < getCost2 => getEstimatedLeft1 > getEstimatedLeft2
			return -1;
		}
		if(cmp < 0.0)
		{
			return 1;
		}
		
		return 0;
		//return Double(this.getCout().compareTo(Double(other.getCout())));
	}
	
	public double getTotalCost()
	{
		return this.estimatedLeft + this.getCost();
	}
	
	/*// should now be set through computeEstimatedLeft()
	public double getEstimatedLeft()
	{
		return this.estimatedLeft;
	}
	
	public void setEstimatedLeft(double estimatedLeft)
	{
		this.estimatedLeft = estimatedLeft;
	}*/
}
