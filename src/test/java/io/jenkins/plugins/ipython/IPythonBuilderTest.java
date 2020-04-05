package io.jenkins.plugins.ipython;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.IOException;

import static org.junit.experimental.theories.internal.ParameterizedAssertionError.join;


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
                new IPythonBuilder("%matplotlib inline\\nimport matplotlib.pyplot as plt\\ndata=[1,1,2,3,4]\\nplt.figure()\\nplt.plot(data)");
        project.getBuildersList().add(builder);


        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
//        String expected = "True\n";
//        jenkins.assertLogContains( expected, build);
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
    @Test
    public void testSimplePipeLine() throws Exception {
        WorkflowJob p = jenkins.createProject(WorkflowJob.class, "p");
        p.setDefinition(new CpsFlowDefinition(
                "node {\n" +
                        " python 'a=10'\n" +
                        "  python 'a**2'\n" +// make sure that 'println' in groovy script works
                        "}"
        ));
        WorkflowRun run = p.scheduleBuild2(0).get();
//        jenkins.assertBuildStatus(Result.SUCCESS,run);
        jenkins.assertLogContains("loghi",run);

    }
}
