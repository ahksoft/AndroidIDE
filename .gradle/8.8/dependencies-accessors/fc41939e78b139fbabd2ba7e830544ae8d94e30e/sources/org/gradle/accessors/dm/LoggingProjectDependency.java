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
public class LoggingProjectDependency extends DelegatingProjectDependency {

    @Inject
    public LoggingProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":logging:idestats"
     */
    public Logging_IdestatsProjectDependency getIdestats() { return new Logging_IdestatsProjectDependency(getFactory(), create(":logging:idestats")); }

    /**
     * Creates a project dependency on the project at path ":logging:logger"
     */
    public Logging_LoggerProjectDependency getLogger() { return new Logging_LoggerProjectDependency(getFactory(), create(":logging:logger")); }

    /**
     * Creates a project dependency on the project at path ":logging:logsender"
     */
    public Logging_LogsenderProjectDependency getLogsender() { return new Logging_LogsenderProjectDependency(getFactory(), create(":logging:logsender")); }

    /**
     * Creates a project dependency on the project at path ":logging:logsender-sample"
     */
    public Logging_LogsenderSampleProjectDependency getLogsenderSample() { return new Logging_LogsenderSampleProjectDependency(getFactory(), create(":logging:logsender-sample")); }

}
