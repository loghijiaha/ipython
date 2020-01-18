package io.jenkins.plugins.ipython.interpreter;

import org.apache.zeppelin.display.GUI;
import org.apache.zeppelin.interpreter.*;
import org.apache.zeppelin.python.IPythonInterpreter;
import org.apache.zeppelin.user.AuthenticationInfo;

import java.util.HashMap;
import java.util.Properties;

public class IPythonKernalInterepreter {
    private IPythonInterpreter interpreter;
    private InterpreterContext context;
    private InterpreterResultMessageOutput interpreterResultMessage ;
    private Properties properties;
    private static IPythonKernalInterepreter instance;
    private IPythonKernalInterepreter() throws Exception {
        properties = new Properties();
        interpreter = new IPythonInterpreter(properties);
        InterpreterGroup mockInterpreterGroup = new InterpreterGroup();
        interpreter.setInterpreterGroup(mockInterpreterGroup);
        try {
            interpreter.open();
        }catch (UnsupportedOperationException e){
            System.out.println("Error to be fixed");
        }

    }

    public static IPythonKernalInterepreter getInstance() throws Exception {
        if(instance == null){
            synchronized (IPythonKernalInterepreter.class) {
                if(instance == null){
                    try {
                        instance = new IPythonKernalInterepreter();
                    } catch (InterpreterException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }
    public InterpreterResultMessageOutput sendAndInterpret(String script) throws InterruptedException {

        InterpreterResult result;
        context = getInterpreterContext();
        result = interpreter.interpret(script, context);
        Thread.sleep(1000);
        System.out.println(result.code());
        interpreterResultMessage = context.out.getCurrentOutput();
        return interpreterResultMessage;
    }
    private static InterpreterContext getInterpreterContext() {
        return new InterpreterContext(
                "noteId",
                "paragraphId",
                "replName",
                "paragraphTitle",
                "paragraphText",
                new AuthenticationInfo(),
                new HashMap<String, Object>(),
                new GUI(),
                new GUI(),
                null,
                null,
                null,
                new InterpreterOutput(null));
    }
    public void shutdown() throws InterpreterException {
        interpreter.close();
    }
    public static void main(String[] args) throws Exception {
        try {
            IPythonKernalInterepreter o = IPythonKernalInterepreter.getInstance();
            System.out.println(o.sendAndInterpret("print(5)"));
            System.out.println(o.sendAndInterpret("print(78)"));
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
