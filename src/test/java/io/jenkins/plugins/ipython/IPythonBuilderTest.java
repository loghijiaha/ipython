package io.jenkins.plugins.ipython;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class IPythonBuilderTest {
           @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    final String name = "%text 37";

    @Test
    public void testBuild() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
        IPythonBuilder builder =
                new IPythonBuilder("32+5");
        project.getBuildersList().add(builder);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        jenkins.assertLogContains( name, build);
    }



}
