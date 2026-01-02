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
public class AndroidIDEProjectDependency extends DelegatingProjectDependency {

    @Inject
    public AndroidIDEProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":annotation"
     */
    public AnnotationProjectDependency getAnnotation() { return new AnnotationProjectDependency(getFactory(), create(":annotation")); }

    /**
     * Creates a project dependency on the project at path ":core"
     */
    public CoreProjectDependency getCore() { return new CoreProjectDependency(getFactory(), create(":core")); }

    /**
     * Creates a project dependency on the project at path ":editor"
     */
    public EditorProjectDependency getEditor() { return new EditorProjectDependency(getFactory(), create(":editor")); }

    /**
     * Creates a project dependency on the project at path ":event"
     */
    public EventProjectDependency getEvent() { return new EventProjectDependency(getFactory(), create(":event")); }

    /**
     * Creates a project dependency on the project at path ":java"
     */
    public JavaProjectDependency getJava() { return new JavaProjectDependency(getFactory(), create(":java")); }

    /**
     * Creates a project dependency on the project at path ":logging"
     */
    public LoggingProjectDependency getLogging() { return new LoggingProjectDependency(getFactory(), create(":logging")); }

    /**
     * Creates a project dependency on the project at path ":termux"
     */
    public TermuxProjectDependency getTermux() { return new TermuxProjectDependency(getFactory(), create(":termux")); }

    /**
     * Creates a project dependency on the project at path ":testing"
     */
    public TestingProjectDependency getTesting() { return new TestingProjectDependency(getFactory(), create(":testing")); }

    /**
     * Creates a project dependency on the project at path ":tooling"
     */
    public ToolingProjectDependency getTooling() { return new ToolingProjectDependency(getFactory(), create(":tooling")); }

    /**
     * Creates a project dependency on the project at path ":utilities"
     */
    public UtilitiesProjectDependency getUtilities() { return new UtilitiesProjectDependency(getFactory(), create(":utilities")); }

    /**
     * Creates a project dependency on the project at path ":xml"
     */
    public XmlProjectDependency getXml() { return new XmlProjectDependency(getFactory(), create(":xml")); }

}
