package exchangepath;

import org.insa.algo.AbstractAlgorithm;
import org.insa.algo.AbstractInputData;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathObserver;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Node;

public abstract class ExchangePathAlgorithm extends AbstractAlgorithm<ShortestPathObserver> {

	protected final AbstractInputData data2;
	
    protected ExchangePathAlgorithm(ShortestPathData data1, ShortestPathData data2) {
        super(data1);
        this.data2 = data2;
    }

    @Override
    public ShortestPathSolution run() {
        return (ShortestPathSolution) super.run();
    }

    @Override
    protected abstract ShortestPathSolution doRun();

    @Override
    public ShortestPathData getInputData() {
        return (ShortestPathData) super.getInputData();
    }

    public ShortestPathData getInputData2() {
        return (ShortestPathData) this.data2;
    }

    /**
     * Notify all observers that the origin has been processed.
     * 
     * @param node Origin.
     */
    public void notifyOriginProcessed(Node node) {
        for (ShortestPathObserver obs: getObservers()) {
            obs.notifyOriginProcessed(node);
        }
    }

    /**
     * Notify all observers that a node has been reached for the first time.
     * 
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node) {
        for (ShortestPathObserver obs: getObservers()) {
            obs.notifyNodeReached(node);
        }
    }

    /**
     * Notify all observers that a node has been marked, i.e. its final value has
     * been set.
     * 
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node) {
        for (ShortestPathObserver obs: getObservers()) {
            obs.notifyNodeMarked(node);
        }
    }

    /**
     * Notify all observers that the destination has been reached.
     * 
     * @param node Destination.
     */
    public void notifyDestinationReached(Node node) {
        for (ShortestPathObserver obs: getObservers()) {
            obs.notifyDestinationReached(node);
        }
    }
}
