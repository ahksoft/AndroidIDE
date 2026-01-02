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
public class ToolingProjectDependency extends DelegatingProjectDependency {

    @Inject
    public ToolingProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":tooling:api"
     */
    public Tooling_ApiProjectDependency getApi() { return new Tooling_ApiProjectDependency(getFactory(), create(":tooling:api")); }

    /**
     * Creates a project dependency on the project at path ":tooling:builder-model-impl"
     */
    public Tooling_BuilderModelImplProjectDependency getBuilderModelImpl() { return new Tooling_BuilderModelImplProjectDependency(getFactory(), create(":tooling:builder-model-impl")); }

    /**
     * Creates a project dependency on the project at path ":tooling:events"
     */
    public Tooling_EventsProjectDependency getEvents() { return new Tooling_EventsProjectDependency(getFactory(), create(":tooling:events")); }

    /**
     * Creates a project dependency on the project at path ":tooling:impl"
     */
    public Tooling_ImplProjectDependency getImpl() { return new Tooling_ImplProjectDependency(getFactory(), create(":tooling:impl")); }

    /**
     * Creates a project dependency on the project at path ":tooling:model"
     */
    public Tooling_ModelProjectDependency getModel() { return new Tooling_ModelProjectDependency(getFactory(), create(":tooling:model")); }

    /**
     * Creates a project dependency on the project at path ":tooling:plugin"
     */
    public Tooling_PluginProjectDependency getPlugin() { return new Tooling_PluginProjectDependency(getFactory(), create(":tooling:plugin")); }

    /**
     * Creates a project dependency on the project at path ":tooling:plugin-config"
     */
    public Tooling_PluginConfigProjectDependency getPluginConfig() { return new Tooling_PluginConfigProjectDependency(getFactory(), create(":tooling:plugin-config")); }

}
