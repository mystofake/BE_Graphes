package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class LabelStar extends Label
{
	private double estimatedLeft;
	
	LabelStar(Node n)
	{
		super(n);
		estimatedLeft = 0.0;
	}
	
	/*LabelStar(Label other)
	{
		super(other);
		this.estimatedLeft = 0.0;
	}*/
	
	LabelStar(LabelStar other)
	{
		super(other);
		this.estimatedLeft = other.estimatedLeft;
	}
	
	@Override
	public int compareTo(Label other) {
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
	
	public double getEstimatedLeft()
	{
		return this.estimatedLeft;
	}
	
	public void setEstimatedLeft(double estimatedLeft)
	{
		this.estimatedLeft = estimatedLeft;
	}
}
