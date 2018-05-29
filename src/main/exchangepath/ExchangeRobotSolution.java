package exchangepath;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.AbstractInputData;
import org.insa.algo.AbstractSolution;
import org.insa.graph.Arc;
import org.insa.graph.Path;

public class ExchangeRobotSolution extends AbstractSolution {

    // Optimal solution.
    private Path path1;
    private Path path2;
    
    private final AbstractInputData data2;

    /**
     * {@inheritDoc}
     */
    public ExchangeRobotSolution(ShortestPathData data1, ShortestPathData data2) {
        super(data1);
        this.data2 = data2;
    }

    /**
     * Create a new infeasible shortest-path solution for the given input and
     * status.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (UNKNOWN / INFEASIBLE).
     */
    public ExchangeRobotSolution(ShortestPathData data1, ShortestPathData data2, Status status) {
        super(data1, status);
        this.data2 = data2;
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public ExchangeRobotSolution(ShortestPathData data1, ShortestPathData data2, Status status, Path path) {
        super(data1, status);
        this.data2 = data2;
        this.path1 = path;
    }

    @Override
    public ShortestPathData getInputData() {
        return (ShortestPathData) super.getInputData();
    }

    /**
     * @return The path for robot 1
     */
    public Path getPath1() {
        return this.path1;
    }

    public Path getPath2() {
        return this.path2;
    }

    @Override
    public String toString() {
        String info = null;
        if (!isFeasible()) {
            info = String.format("No path found from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
        }
        else {
            double cost = 0;
            for (Arc arc: getPath1().getArcs()) {
                cost += getInputData().getCost(arc);
            }
            info = String.format("Found a path from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
            if (getInputData().getMode() == Mode.LENGTH) {
                info = String.format("%s, %.4f kilometers", info, cost / 1000.0);
            }
            else {
                info = String.format("%s, %.4f minutes", info, cost / 60.0);
            }
        }
        info += " in " + getSolvingTime().getSeconds() + " seconds.";
        return info;
    }

}
