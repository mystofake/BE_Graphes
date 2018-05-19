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
	
	public double getTotalCost()
	{
		return estimatedLeft + this.getCost();
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
