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
public class BuildDepsProjectDependency extends DelegatingProjectDependency {

    @Inject
    public BuildDepsProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":appintro"
     */
    public AppintroProjectDependency getAppintro() { return new AppintroProjectDependency(getFactory(), create(":appintro")); }

    /**
     * Creates a project dependency on the project at path ":fuzzysearch"
     */
    public FuzzysearchProjectDependency getFuzzysearch() { return new FuzzysearchProjectDependency(getFactory(), create(":fuzzysearch")); }

    /**
     * Creates a project dependency on the project at path ":google-java-format"
     */
    public GoogleJavaFormatProjectDependency getGoogleJavaFormat() { return new GoogleJavaFormatProjectDependency(getFactory(), create(":google-java-format")); }

    /**
     * Creates a project dependency on the project at path ":java-compiler"
     */
    public JavaCompilerProjectDependency getJavaCompiler() { return new JavaCompilerProjectDependency(getFactory(), create(":java-compiler")); }

    /**
     * Creates a project dependency on the project at path ":javac"
     */
    public JavacProjectDependency getJavac() { return new JavacProjectDependency(getFactory(), create(":javac")); }

    /**
     * Creates a project dependency on the project at path ":javapoet"
     */
    public JavapoetProjectDependency getJavapoet() { return new JavapoetProjectDependency(getFactory(), create(":javapoet")); }

    /**
     * Creates a project dependency on the project at path ":jaxp"
     */
    public JaxpProjectDependency getJaxp() { return new JaxpProjectDependency(getFactory(), create(":jaxp")); }

    /**
     * Creates a project dependency on the project at path ":jdk-compiler"
     */
    public JdkCompilerProjectDependency getJdkCompiler() { return new JdkCompilerProjectDependency(getFactory(), create(":jdk-compiler")); }

    /**
     * Creates a project dependency on the project at path ":jdk-jdeps"
     */
    public JdkJdepsProjectDependency getJdkJdeps() { return new JdkJdepsProjectDependency(getFactory(), create(":jdk-jdeps")); }

    /**
     * Creates a project dependency on the project at path ":jdt"
     */
    public JdtProjectDependency getJdt() { return new JdtProjectDependency(getFactory(), create(":jdt")); }

    /**
     * Creates a project dependency on the project at path ":layoutlib-api"
     */
    public LayoutlibApiProjectDependency getLayoutlibApi() { return new LayoutlibApiProjectDependency(getFactory(), create(":layoutlib-api")); }

    /**
     * Creates a project dependency on the project at path ":logback-core"
     */
    public LogbackCoreProjectDependency getLogbackCore() { return new LogbackCoreProjectDependency(getFactory(), create(":logback-core")); }

}
