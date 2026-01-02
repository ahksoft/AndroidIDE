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
public class CoreProjectDependency extends DelegatingProjectDependency {

    @Inject
    public CoreProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":core:actions"
     */
    public Core_ActionsProjectDependency getActions() { return new Core_ActionsProjectDependency(getFactory(), create(":core:actions")); }

    /**
     * Creates a project dependency on the project at path ":core:ai-cli"
     */
    public Core_AiCliProjectDependency getAiCli() { return new Core_AiCliProjectDependency(getFactory(), create(":core:ai-cli")); }

    /**
     * Creates a project dependency on the project at path ":core:app"
     */
    public Core_AppProjectDependency getApp() { return new Core_AppProjectDependency(getFactory(), create(":core:app")); }

    /**
     * Creates a project dependency on the project at path ":core:common"
     */
    public Core_CommonProjectDependency getCommon() { return new Core_CommonProjectDependency(getFactory(), create(":core:common")); }

    /**
     * Creates a project dependency on the project at path ":core:indexing-api"
     */
    public Core_IndexingApiProjectDependency getIndexingApi() { return new Core_IndexingApiProjectDependency(getFactory(), create(":core:indexing-api")); }

    /**
     * Creates a project dependency on the project at path ":core:indexing-core"
     */
    public Core_IndexingCoreProjectDependency getIndexingCore() { return new Core_IndexingCoreProjectDependency(getFactory(), create(":core:indexing-core")); }

    /**
     * Creates a project dependency on the project at path ":core:lsp-api"
     */
    public Core_LspApiProjectDependency getLspApi() { return new Core_LspApiProjectDependency(getFactory(), create(":core:lsp-api")); }

    /**
     * Creates a project dependency on the project at path ":core:lsp-models"
     */
    public Core_LspModelsProjectDependency getLspModels() { return new Core_LspModelsProjectDependency(getFactory(), create(":core:lsp-models")); }

    /**
     * Creates a project dependency on the project at path ":core:projects"
     */
    public Core_ProjectsProjectDependency getProjects() { return new Core_ProjectsProjectDependency(getFactory(), create(":core:projects")); }

    /**
     * Creates a project dependency on the project at path ":core:resources"
     */
    public Core_ResourcesProjectDependency getResources() { return new Core_ResourcesProjectDependency(getFactory(), create(":core:resources")); }

}
