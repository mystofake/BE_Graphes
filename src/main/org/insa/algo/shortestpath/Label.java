package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class Label implements Comparable<Label>
{
	private Node node;
	private double cost;
	private boolean marked;
	private Node chosenPrevious;
	
	Label(Node node)
	{
		this.node = node;
		this.cost = Double.POSITIVE_INFINITY;
		this.marked = false;
		this.chosenPrevious = null;
	}
	
	Label(Label other)
	{ // copy
		this.node = other.node;
		this.cost = other.cost;
		this.marked = other.marked;
		this.chosenPrevious = other.chosenPrevious;
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
		return 0;
		//return Double(this.getCout().compareTo(Double(other.getCout())));
	}
	
	public double getTotalCost()
	{
		return this.getCost();
	}
	
	
	public Node getNode()
	{
		return node;
	}
	
	public void setNode(Node node)
	{
		this.node = node;
	}
	
	public Node getPreviousNode()
	{
		return this.chosenPrevious;
	}
	
	public void setPreviousNode(Node node)
	{
		this.chosenPrevious = node;
	}
	
	public double getCost()
	{
		return this.cost;
	}
	
	public void setCost(double cost)
	{
		this.cost = cost;
	}
	
	public boolean isMarked()
	{
		return this.marked;
	}
	
	public void mark()
	{
		this.marked = true;
	}
}
