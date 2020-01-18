package io.jenkins.plugins.ipython.interpreter

class IPythonKernalInterepreterTest extends GroovyTestCase {
    void testSendAndInterpret() {
        IPythonKernalInterepreter o = IPythonKernalInterepreter.getInstance();
        assert(o.sendAndInterpret("print(5)"));
    }
}
