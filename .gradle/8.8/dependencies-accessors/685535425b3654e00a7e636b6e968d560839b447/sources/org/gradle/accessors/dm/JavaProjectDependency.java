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
public class JavaProjectDependency extends DelegatingProjectDependency {

    @Inject
    public JavaProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":java:javac-services"
     */
    public Java_JavacServicesProjectDependency getJavacServices() { return new Java_JavacServicesProjectDependency(getFactory(), create(":java:javac-services")); }

    /**
     * Creates a project dependency on the project at path ":java:lsp"
     */
    public Java_LspProjectDependency getLsp() { return new Java_LspProjectDependency(getFactory(), create(":java:lsp")); }

}
