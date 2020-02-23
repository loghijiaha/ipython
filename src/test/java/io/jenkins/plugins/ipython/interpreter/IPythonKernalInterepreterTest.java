package io.jenkins.plugins.ipython.interpreter;

import org.junit.Test;

import static org.junit.Assert.*;

public class IPythonKernalInterepreterTest {

    @Test
    public void sendAndInterpret() throws Exception {
        IPythonKernalInterepreter interepreter = IPythonKernalInterepreter.getInstance();
        String expected = "%text 5";
        String actual = interepreter.sendAndInterpret("5").toString();
        assertEquals(actual,expected);
    }
}