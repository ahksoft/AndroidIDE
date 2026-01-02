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
public class TermuxProjectDependency extends DelegatingProjectDependency {

    @Inject
    public TermuxProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":termux:application"
     */
    public Termux_ApplicationProjectDependency getApplication() { return new Termux_ApplicationProjectDependency(getFactory(), create(":termux:application")); }

    /**
     * Creates a project dependency on the project at path ":termux:emulator"
     */
    public Termux_EmulatorProjectDependency getEmulator() { return new Termux_EmulatorProjectDependency(getFactory(), create(":termux:emulator")); }

    /**
     * Creates a project dependency on the project at path ":termux:shared"
     */
    public Termux_SharedProjectDependency getShared() { return new Termux_SharedProjectDependency(getFactory(), create(":termux:shared")); }

    /**
     * Creates a project dependency on the project at path ":termux:view"
     */
    public Termux_ViewProjectDependency getView() { return new Termux_ViewProjectDependency(getFactory(), create(":termux:view")); }

}
