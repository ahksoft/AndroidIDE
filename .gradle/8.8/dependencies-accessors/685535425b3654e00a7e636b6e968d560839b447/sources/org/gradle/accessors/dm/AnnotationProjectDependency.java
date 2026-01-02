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
public class AnnotationProjectDependency extends DelegatingProjectDependency {

    @Inject
    public AnnotationProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":annotation:annotations"
     */
    public Annotation_AnnotationsProjectDependency getAnnotations() { return new Annotation_AnnotationsProjectDependency(getFactory(), create(":annotation:annotations")); }

    /**
     * Creates a project dependency on the project at path ":annotation:processors"
     */
    public Annotation_ProcessorsProjectDependency getProcessors() { return new Annotation_ProcessorsProjectDependency(getFactory(), create(":annotation:processors")); }

    /**
     * Creates a project dependency on the project at path ":annotation:processors-ksp"
     */
    public Annotation_ProcessorsKspProjectDependency getProcessorsKsp() { return new Annotation_ProcessorsKspProjectDependency(getFactory(), create(":annotation:processors-ksp")); }

}
