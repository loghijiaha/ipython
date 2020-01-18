package io.jenkins.plugins.ipython.interpreter

import org.junit.After
import org.junit.Before
import org.junit.Test

class IPythonKernalInterepreterTest extends GroovyTestCase {

    IPythonKernalInterepreter o = IPythonKernalInterepreter.getInstance();
    @Test
    void testSendAndInterpret() {

        assert(o.sendAndInterpret("print(5)"));
        o.shutdown();
    }
    @After
    void close(){
        o.shutdown();
    }
}
