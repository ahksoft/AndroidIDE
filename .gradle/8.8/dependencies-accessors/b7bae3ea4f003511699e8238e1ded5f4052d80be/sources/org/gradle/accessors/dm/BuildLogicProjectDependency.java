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
public class BuildLogicProjectDependency extends DelegatingProjectDependency {

    @Inject
    public BuildLogicProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":common"
     */
    public CommonProjectDependency getCommon() { return new CommonProjectDependency(getFactory(), create(":common")); }

    /**
     * Creates a project dependency on the project at path ":desugaring"
     */
    public DesugaringProjectDependency getDesugaring() { return new DesugaringProjectDependency(getFactory(), create(":desugaring")); }

    /**
     * Creates a project dependency on the project at path ":desugaring-core"
     */
    public DesugaringCoreProjectDependency getDesugaringCore() { return new DesugaringCoreProjectDependency(getFactory(), create(":desugaring-core")); }

    /**
     * Creates a project dependency on the project at path ":plugins"
     */
    public PluginsProjectDependency getPlugins() { return new PluginsProjectDependency(getFactory(), create(":plugins")); }

    /**
     * Creates a project dependency on the project at path ":properties-parser"
     */
    public PropertiesParserProjectDependency getPropertiesParser() { return new PropertiesParserProjectDependency(getFactory(), create(":properties-parser")); }

}
