package io.jenkins.plugins.ipython;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class IPythonBuilderTest {
           @Rule
    public JenkinsRule jenkins = new JenkinsRule();


    @Test
    public void testAdditionBuild() throws Exception {
        String name = "%text 37";
        FreeStyleProject project = jenkins.createFreeStyleProject();
        IPythonBuilder builder =
                new IPythonBuilder("32+5");
        project.getBuildersList().add(builder);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        jenkins.assertLogContains( name, build);
    }
    @Test
    public void testConditionalBuild() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
        IPythonBuilder builder =
                new IPythonBuilder("if 2 > 0:\n\tprint('True')\nelse:\n\tprint('False')");
        project.getBuildersList().add(builder);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        String expected = "True\n";
        jenkins.assertLogContains( expected, build);
    }

    @Test
    public void testZeroDivisionErrorBuild() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
        IPythonBuilder builder =
                new IPythonBuilder("1/0");
        project.getBuildersList().add(builder);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        String expected = "ZeroDivisionError";
        jenkins.assertLogContains( expected, build);
    }
}
