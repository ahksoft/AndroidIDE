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
public class UtilitiesProjectDependency extends DelegatingProjectDependency {

    @Inject
    public UtilitiesProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":utilities:build-info"
     */
    public Utilities_BuildInfoProjectDependency getBuildInfo() { return new Utilities_BuildInfoProjectDependency(getFactory(), create(":utilities:build-info")); }

    /**
     * Creates a project dependency on the project at path ":utilities:flashbar"
     */
    public Utilities_FlashbarProjectDependency getFlashbar() { return new Utilities_FlashbarProjectDependency(getFactory(), create(":utilities:flashbar")); }

    /**
     * Creates a project dependency on the project at path ":utilities:framework-stubs"
     */
    public Utilities_FrameworkStubsProjectDependency getFrameworkStubs() { return new Utilities_FrameworkStubsProjectDependency(getFactory(), create(":utilities:framework-stubs")); }

    /**
     * Creates a project dependency on the project at path ":utilities:lookup"
     */
    public Utilities_LookupProjectDependency getLookup() { return new Utilities_LookupProjectDependency(getFactory(), create(":utilities:lookup")); }

    /**
     * Creates a project dependency on the project at path ":utilities:preferences"
     */
    public Utilities_PreferencesProjectDependency getPreferences() { return new Utilities_PreferencesProjectDependency(getFactory(), create(":utilities:preferences")); }

    /**
     * Creates a project dependency on the project at path ":utilities:shared"
     */
    public Utilities_SharedProjectDependency getShared() { return new Utilities_SharedProjectDependency(getFactory(), create(":utilities:shared")); }

    /**
     * Creates a project dependency on the project at path ":utilities:templates-api"
     */
    public Utilities_TemplatesApiProjectDependency getTemplatesApi() { return new Utilities_TemplatesApiProjectDependency(getFactory(), create(":utilities:templates-api")); }

    /**
     * Creates a project dependency on the project at path ":utilities:templates-impl"
     */
    public Utilities_TemplatesImplProjectDependency getTemplatesImpl() { return new Utilities_TemplatesImplProjectDependency(getFactory(), create(":utilities:templates-impl")); }

    /**
     * Creates a project dependency on the project at path ":utilities:treeview"
     */
    public Utilities_TreeviewProjectDependency getTreeview() { return new Utilities_TreeviewProjectDependency(getFactory(), create(":utilities:treeview")); }

    /**
     * Creates a project dependency on the project at path ":utilities:uidesigner"
     */
    public Utilities_UidesignerProjectDependency getUidesigner() { return new Utilities_UidesignerProjectDependency(getFactory(), create(":utilities:uidesigner")); }

    /**
     * Creates a project dependency on the project at path ":utilities:xml-inflater"
     */
    public Utilities_XmlInflaterProjectDependency getXmlInflater() { return new Utilities_XmlInflaterProjectDependency(getFactory(), create(":utilities:xml-inflater")); }

}
