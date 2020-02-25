package io.jenkins.plugins.ipython.interpreter;

import org.apache.zeppelin.display.GUI;
import org.apache.zeppelin.interpreter.*;

import org.apache.zeppelin.python.IPythonInterpreter;
import org.apache.zeppelin.user.AuthenticationInfo;
import org.apache.zeppelin.interpreter.LazyOpenInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class IPythonKernalInterepreter {
    private LazyOpenInterpreter interpreter;
    private InterpreterContext context;
    private InterpreterResultMessageOutput interpreterResultMessage ;
    private Properties properties;
    private static IPythonKernalInterepreter instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(IPythonInterpreter.class);
    private IPythonKernalInterepreter() throws Exception {
        properties = new Properties();
        properties.setProperty("zeppelin.python.maxResult", "3");
        properties.setProperty("zeppelin.python.gatewayserver_address", "127.0.0.1");
        interpreter = new LazyOpenInterpreter(new IPythonInterpreter(properties));
        InterpreterGroup mockInterpreterGroup = new InterpreterGroup();
        mockInterpreterGroup.put("session_1", new ArrayList<Interpreter>());
        mockInterpreterGroup.get("session_1").add(interpreter);
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
        try {
            result = interpreter.interpret(script, context);
            Thread.sleep(1000);
            System.out.println(result.code());
            interpreterResultMessage = context.out.getCurrentOutput();
        } catch (InterpreterException e) {
            e.printStackTrace();
        }

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
}
