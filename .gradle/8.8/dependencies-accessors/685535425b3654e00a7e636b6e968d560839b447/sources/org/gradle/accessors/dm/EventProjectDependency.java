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
public class EventProjectDependency extends DelegatingProjectDependency {

    @Inject
    public EventProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":event:eventbus"
     */
    public Event_EventbusProjectDependency getEventbus() { return new Event_EventbusProjectDependency(getFactory(), create(":event:eventbus")); }

    /**
     * Creates a project dependency on the project at path ":event:eventbus-android"
     */
    public Event_EventbusAndroidProjectDependency getEventbusAndroid() { return new Event_EventbusAndroidProjectDependency(getFactory(), create(":event:eventbus-android")); }

    /**
     * Creates a project dependency on the project at path ":event:eventbus-events"
     */
    public Event_EventbusEventsProjectDependency getEventbusEvents() { return new Event_EventbusEventsProjectDependency(getFactory(), create(":event:eventbus-events")); }

}
