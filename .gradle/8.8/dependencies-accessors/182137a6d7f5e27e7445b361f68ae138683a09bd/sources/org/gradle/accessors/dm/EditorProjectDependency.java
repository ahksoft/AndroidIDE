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
public class EditorProjectDependency extends DelegatingProjectDependency {

    @Inject
    public EditorProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":editor:api"
     */
    public Editor_ApiProjectDependency getApi() { return new Editor_ApiProjectDependency(getFactory(), create(":editor:api")); }

    /**
     * Creates a project dependency on the project at path ":editor:impl"
     */
    public Editor_ImplProjectDependency getImpl() { return new Editor_ImplProjectDependency(getFactory(), create(":editor:impl")); }

    /**
     * Creates a project dependency on the project at path ":editor:lexers"
     */
    public Editor_LexersProjectDependency getLexers() { return new Editor_LexersProjectDependency(getFactory(), create(":editor:lexers")); }

    /**
     * Creates a project dependency on the project at path ":editor:treesitter"
     */
    public Editor_TreesitterProjectDependency getTreesitter() { return new Editor_TreesitterProjectDependency(getFactory(), create(":editor:treesitter")); }

}
