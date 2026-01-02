package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class TestingProjectDependency extends DelegatingProjectDependency {

    @Inject
    public TestingProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":testing:androidTest"
     */
    public Testing_AndroidTestProjectDependency getAndroidTest() { return new Testing_AndroidTestProjectDependency(getFactory(), create(":testing:androidTest")); }

    /**
     * Creates a project dependency on the project at path ":testing:benchmarks"
     */
    public Testing_BenchmarksProjectDependency getBenchmarks() { return new Testing_BenchmarksProjectDependency(getFactory(), create(":testing:benchmarks")); }

    /**
     * Creates a project dependency on the project at path ":testing:commonTest"
     */
    public Testing_CommonTestProjectDependency getCommonTest() { return new Testing_CommonTestProjectDependency(getFactory(), create(":testing:commonTest")); }

    /**
     * Creates a project dependency on the project at path ":testing:gradleToolingTest"
     */
    public Testing_GradleToolingTestProjectDependency getGradleToolingTest() { return new Testing_GradleToolingTestProjectDependency(getFactory(), create(":testing:gradleToolingTest")); }

    /**
     * Creates a project dependency on the project at path ":testing:lspTest"
     */
    public Testing_LspTestProjectDependency getLspTest() { return new Testing_LspTestProjectDependency(getFactory(), create(":testing:lspTest")); }

    /**
     * Creates a project dependency on the project at path ":testing:unitTest"
     */
    public Testing_UnitTestProjectDependency getUnitTest() { return new Testing_UnitTestProjectDependency(getFactory(), create(":testing:unitTest")); }

}
