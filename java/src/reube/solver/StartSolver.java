// Andrew Farrell
// 2014

package reube.solver;

import org.apache.log4j.Logger;

/**
 * Sets up solver environment -
 * - initiates creation of pipes for solve request/responses
 * - forwards solve requests to Eclipse CLP
 * - forwards unifications and final result to requester
 */
public class StartSolver {

    static Logger _log = Logger.getLogger(StartSolver.class);

    private String requestName = "reube.solver.req";
    private String responseName = "reube.solver.resp";

    private void createPipes() throws PipeManagement.PipeManagementException{
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);

        //Create request pipe
        PipeManagement.createPipe(requestName);

        //Create response pipe
        PipeManagement.createPipe(responseName);
    }
    private void primeEclipse(){
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);



    }
    private void interactEclipse(){
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);

    }

    public static void main(String[] args){
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
        StartSolver solver = new StartSolver();
        try {
            solver.createPipes();
            solver.primeEclipse();
            solver.interactEclipse();
        } catch (PipeManagement.PipeManagementException e) {
            e.printStackTrace();
        }
    }
}
