package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class Label implements Comparable<Label>
{
	private Node node;
	private double cout;
	private boolean marked;
	private Node chosenPrevious;
	
	Label(Node node)
	{
		this.node = node;
		this.cout = Double.POSITIVE_INFINITY;
		this.marked = false;
		this.chosenPrevious = null;
	}
	
	@Override
	public int compareTo(Label other) {
		float cmp = Double.compare(this.getCost(), other.getCost());
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
	/*@Override
    public int compareTo(Label other) {
        return 0;
    }*/
	
	public Node getNode()
	{
		return node;
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
		return this.cout;
	}
	
	public void setCost(double cout)
	{
		this.cout = cout;
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
