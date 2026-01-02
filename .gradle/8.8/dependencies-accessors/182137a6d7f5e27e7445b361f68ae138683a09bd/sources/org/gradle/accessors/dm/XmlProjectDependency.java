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
public class XmlProjectDependency extends DelegatingProjectDependency {

    @Inject
    public XmlProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":xml:aaptcompiler"
     */
    public Xml_AaptcompilerProjectDependency getAaptcompiler() { return new Xml_AaptcompilerProjectDependency(getFactory(), create(":xml:aaptcompiler")); }

    /**
     * Creates a project dependency on the project at path ":xml:dom"
     */
    public Xml_DomProjectDependency getDom() { return new Xml_DomProjectDependency(getFactory(), create(":xml:dom")); }

    /**
     * Creates a project dependency on the project at path ":xml:lsp"
     */
    public Xml_LspProjectDependency getLsp() { return new Xml_LspProjectDependency(getFactory(), create(":xml:lsp")); }

    /**
     * Creates a project dependency on the project at path ":xml:resources-api"
     */
    public Xml_ResourcesApiProjectDependency getResourcesApi() { return new Xml_ResourcesApiProjectDependency(getFactory(), create(":xml:resources-api")); }

    /**
     * Creates a project dependency on the project at path ":xml:utils"
     */
    public Xml_UtilsProjectDependency getUtils() { return new Xml_UtilsProjectDependency(getFactory(), create(":xml:utils")); }

}
