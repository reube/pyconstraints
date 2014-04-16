package reube.solver;
// Andrew Farrell
// 2014


import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Creates/ Destroys Pipes
 */
public class PipeManagement {
    public static class PipeManagementException extends Exception {
        public static int E_EXISTS = 0x1;
        public static int E_NOTEXISTS = 0x11;
        public static int E_CREATE_FAIL = 0x1;
        public static int E_DESTROY_FAIL = 0x11;

        private int code;
        public PipeManagementException(int code, String msg){
            this(code,msg,null);
        }
        public PipeManagementException(int code, String msg, Throwable t){
            super(msg,t);
            this.code=code;
        }
        public int getCode(){
            if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
            return this.code;
        }
    }

    public static Logger _log = Logger.getLogger(PipeManagement.class);

    /**
     * Create an OS pipe
     * @param name
     * @throws PipeManagementException
     */
    public static void createPipe(String name) throws PipeManagementException{
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
        managePipe(name, true);
    }

    public static void destroyPipe(String name) throws PipeManagementException{
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
        managePipe(name,false);
    }

    private static void managePipe(String name, boolean create) throws PipeManagementException{
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
        String pname= "/tmp/"+name;
        File f = new File(pname);
        if (create){
            //create pipe
            if (f.exists()){
                throw new PipeManagementException(PipeManagementException.E_EXISTS, "Pipe with name: "+pname+" exists already!");
            }
            try {
                Runtime.getRuntime().exec("mkfifo "+pname);
            } catch (IOException e) {
                throw new PipeManagementException(PipeManagementException.E_CREATE_FAIL, "Pipe with name: "+pname+" - failed to create");
            }
            if (_log.isDebugEnabled()) _log.debug("Have created pipe: "+pname);

        } else {
            //destroy pipe
            if (!f.exists()){
                throw new PipeManagementException(PipeManagementException.E_NOTEXISTS, "Pipe with name: "+pname+" does not exist!");
            }
            if (!f.delete() ) throw new PipeManagementException(PipeManagementException.E_DESTROY_FAIL, "Pipe with name: "+pname+" - failed to destroy");
            if (_log.isDebugEnabled()) _log.debug("Have destroyed pipe: "+pname);
        }
    }

    static public void main(String[] args){
        if (_log.isDebugEnabled()) _log.debug(Thread.currentThread().getStackTrace()[1]);
        if (args.length!=2){
            _log.error("Incorrect number of arguments supplied");
            System.exit(0);
        }
        try {
            if (args[0].compareTo("create")==0){
                createPipe(args[1]);
            } else {
                    destroyPipe(args[1]);
            }
        } catch (PipeManagementException e) {
            e.printStackTrace();
        }
    }
}
