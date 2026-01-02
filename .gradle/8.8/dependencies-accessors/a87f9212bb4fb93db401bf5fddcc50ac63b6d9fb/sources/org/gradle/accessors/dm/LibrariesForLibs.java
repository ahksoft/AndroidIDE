package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final Aapt2LibraryAccessors laccForAapt2LibraryAccessors = new Aapt2LibraryAccessors(owner);
    private final AndroidLibraryAccessors laccForAndroidLibraryAccessors = new AndroidLibraryAccessors(owner);
    private final AndroidideLibraryAccessors laccForAndroidideLibraryAccessors = new AndroidideLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final CommonLibraryAccessors laccForCommonLibraryAccessors = new CommonLibraryAccessors(owner);
    private final CompositeLibraryAccessors laccForCompositeLibraryAccessors = new CompositeLibraryAccessors(owner);
    private final GitLibraryAccessors laccForGitLibraryAccessors = new GitLibraryAccessors(owner);
    private final GoogleLibraryAccessors laccForGoogleLibraryAccessors = new GoogleLibraryAccessors(owner);
    private final KotlinLibraryAccessors laccForKotlinLibraryAccessors = new KotlinLibraryAccessors(owner);
    private final LoggingLibraryAccessors laccForLoggingLibraryAccessors = new LoggingLibraryAccessors(owner);
    private final MavenLibraryAccessors laccForMavenLibraryAccessors = new MavenLibraryAccessors(owner);
    private final NavLibraryAccessors laccForNavLibraryAccessors = new NavLibraryAccessors(owner);
    private final TestsLibraryAccessors laccForTestsLibraryAccessors = new TestsLibraryAccessors(owner);
    private final ToolingLibraryAccessors laccForToolingLibraryAccessors = new ToolingLibraryAccessors(owner);
    private final XmlLibraryAccessors laccForXmlLibraryAccessors = new XmlLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>aapt2</b>
     */
    public Aapt2LibraryAccessors getAapt2() {
        return laccForAapt2LibraryAccessors;
    }

    /**
     * Group of libraries at <b>android</b>
     */
    public AndroidLibraryAccessors getAndroid() {
        return laccForAndroidLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidide</b>
     */
    public AndroidideLibraryAccessors getAndroidide() {
        return laccForAndroidideLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>common</b>
     */
    public CommonLibraryAccessors getCommon() {
        return laccForCommonLibraryAccessors;
    }

    /**
     * Group of libraries at <b>composite</b>
     */
    public CompositeLibraryAccessors getComposite() {
        return laccForCompositeLibraryAccessors;
    }

    /**
     * Group of libraries at <b>git</b>
     */
    public GitLibraryAccessors getGit() {
        return laccForGitLibraryAccessors;
    }

    /**
     * Group of libraries at <b>google</b>
     */
    public GoogleLibraryAccessors getGoogle() {
        return laccForGoogleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>kotlin</b>
     */
    public KotlinLibraryAccessors getKotlin() {
        return laccForKotlinLibraryAccessors;
    }

    /**
     * Group of libraries at <b>logging</b>
     */
    public LoggingLibraryAccessors getLogging() {
        return laccForLoggingLibraryAccessors;
    }

    /**
     * Group of libraries at <b>maven</b>
     */
    public MavenLibraryAccessors getMaven() {
        return laccForMavenLibraryAccessors;
    }

    /**
     * Group of libraries at <b>nav</b>
     */
    public NavLibraryAccessors getNav() {
        return laccForNavLibraryAccessors;
    }

    /**
     * Group of libraries at <b>tests</b>
     */
    public TestsLibraryAccessors getTests() {
        return laccForTestsLibraryAccessors;
    }

    /**
     * Group of libraries at <b>tooling</b>
     */
    public ToolingLibraryAccessors getTooling() {
        return laccForToolingLibraryAccessors;
    }

    /**
     * Group of libraries at <b>xml</b>
     */
    public XmlLibraryAccessors getXml() {
        return laccForXmlLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class Aapt2LibraryAccessors extends SubDependencyFactory {
        private final Aapt2JbLibraryAccessors laccForAapt2JbLibraryAccessors = new Aapt2JbLibraryAccessors(owner);

        public Aapt2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.android.tools:annotations</b> coordinates and
         * with version <b>31.2.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("aapt2.annotations");
        }

        /**
         * Dependency provider for <b>common</b> with <b>com.android.tools:common</b> coordinates and
         * with version <b>31.4.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("aapt2.common");
        }

        /**
         * Dependency provider for <b>proto</b> with <b>com.android.tools.build:aapt2-proto</b> coordinates and
         * with version <b>8.3.0-10880808</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getProto() {
            return create("aapt2.proto");
        }

        /**
         * Group of libraries at <b>aapt2.jb</b>
         */
        public Aapt2JbLibraryAccessors getJb() {
            return laccForAapt2JbLibraryAccessors;
        }

    }

    public static class Aapt2JbLibraryAccessors extends SubDependencyFactory {

        public Aapt2JbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>org.jetbrains:annotations</b> coordinates and
         * with version <b>24.1.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("aapt2.jb.annotations");
        }

    }

    public static class AndroidLibraryAccessors extends SubDependencyFactory {
        private final AndroidGradleLibraryAccessors laccForAndroidGradleLibraryAccessors = new AndroidGradleLibraryAccessors(owner);

        public AndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>android.gradle</b>
         */
        public AndroidGradleLibraryAccessors getGradle() {
            return laccForAndroidGradleLibraryAccessors;
        }

    }

    public static class AndroidGradleLibraryAccessors extends SubDependencyFactory {

        public AndroidGradleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>plugin</b> with <b>com.android.tools.build:gradle</b> coordinates and
         * with version reference <b>agp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlugin() {
            return create("android.gradle.plugin");
        }

    }

    public static class AndroidideLibraryAccessors extends SubDependencyFactory {
        private final AndroidideTsLibraryAccessors laccForAndroidideTsLibraryAccessors = new AndroidideTsLibraryAccessors(owner);

        public AndroidideLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidide.ts</b>
         */
        public AndroidideTsLibraryAccessors getTs() {
            return laccForAndroidideTsLibraryAccessors;
        }

    }

    public static class AndroidideTsLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidideTsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ts</b> with <b>com.itsaky.androidide.treesitter:android-tree-sitter</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidide.ts");
        }

        /**
         * Dependency provider for <b>java</b> with <b>com.itsaky.androidide.treesitter:tree-sitter-java</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("androidide.ts.java");
        }

        /**
         * Dependency provider for <b>json</b> with <b>com.itsaky.androidide.treesitter:tree-sitter-json</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJson() {
            return create("androidide.ts.json");
        }

        /**
         * Dependency provider for <b>kotlin</b> with <b>com.itsaky.androidide.treesitter:tree-sitter-kotlin</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKotlin() {
            return create("androidide.ts.kotlin");
        }

        /**
         * Dependency provider for <b>log</b> with <b>com.itsaky.androidide.treesitter:tree-sitter-log</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLog() {
            return create("androidide.ts.log");
        }

        /**
         * Dependency provider for <b>xml</b> with <b>com.itsaky.androidide.treesitter:tree-sitter-xml</b> coordinates and
         * with version reference <b>tree.sitter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getXml() {
            return create("androidide.ts.xml");
        }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxAnimatedLibraryAccessors laccForAndroidxAnimatedLibraryAccessors = new AndroidxAnimatedLibraryAccessors(owner);
        private final AndroidxBenchmarkLibraryAccessors laccForAndroidxBenchmarkLibraryAccessors = new AndroidxBenchmarkLibraryAccessors(owner);
        private final AndroidxCoreLibraryAccessors laccForAndroidxCoreLibraryAccessors = new AndroidxCoreLibraryAccessors(owner);
        private final AndroidxFragmentLibraryAccessors laccForAndroidxFragmentLibraryAccessors = new AndroidxFragmentLibraryAccessors(owner);
        private final AndroidxNavLibraryAccessors laccForAndroidxNavLibraryAccessors = new AndroidxNavLibraryAccessors(owner);
        private final AndroidxTracingLibraryAccessors laccForAndroidxTracingLibraryAccessors = new AndroidxTracingLibraryAccessors(owner);
        private final AndroidxWindowLibraryAccessors laccForAndroidxWindowLibraryAccessors = new AndroidxWindowLibraryAccessors(owner);
        private final AndroidxWorkLibraryAccessors laccForAndroidxWorkLibraryAccessors = new AndroidxWorkLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotation</b> with <b>androidx.annotation:annotation</b> coordinates and
         * with version <b>1.7.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotation() {
            return create("androidx.annotation");
        }

        /**
         * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
         * with version <b>1.6.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAppcompat() {
            return create("androidx.appcompat");
        }

        /**
         * Dependency provider for <b>cardview</b> with <b>androidx.cardview:cardview</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCardview() {
            return create("androidx.cardview");
        }

        /**
         * Dependency provider for <b>collection</b> with <b>androidx.collection:collection</b> coordinates and
         * with version <b>1.4.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCollection() {
            return create("androidx.collection");
        }

        /**
         * Dependency provider for <b>constraintlayout</b> with <b>androidx.constraintlayout:constraintlayout</b> coordinates and
         * with version <b>2.1.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConstraintlayout() {
            return create("androidx.constraintlayout");
        }

        /**
         * Dependency provider for <b>coordinatorlayout</b> with <b>androidx.coordinatorlayout:coordinatorlayout</b> coordinates and
         * with version <b>1.2.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCoordinatorlayout() {
            return create("androidx.coordinatorlayout");
        }

        /**
         * Dependency provider for <b>drawer</b> with <b>androidx.drawerlayout:drawerlayout</b> coordinates and
         * with version <b>1.2.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getDrawer() {
            return create("androidx.drawer");
        }

        /**
         * Dependency provider for <b>grid</b> with <b>androidx.gridlayout:gridlayout</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGrid() {
            return create("androidx.grid");
        }

        /**
         * Dependency provider for <b>libDesugaring</b> with <b>com.android.tools:desugar_jdk_libs</b> coordinates and
         * with version <b>2.0.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLibDesugaring() {
            return create("androidx.libDesugaring");
        }

        /**
         * Dependency provider for <b>preference</b> with <b>androidx.preference:preference</b> coordinates and
         * with version <b>1.2.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPreference() {
            return create("androidx.preference");
        }

        /**
         * Dependency provider for <b>recyclerview</b> with <b>androidx.recyclerview:recyclerview</b> coordinates and
         * with version <b>1.3.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRecyclerview() {
            return create("androidx.recyclerview");
        }

        /**
         * Dependency provider for <b>splashscreen</b> with <b>androidx.core:core-splashscreen</b> coordinates and
         * with version <b>1.0.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSplashscreen() {
            return create("androidx.splashscreen");
        }

        /**
         * Dependency provider for <b>transition</b> with <b>androidx.transition:transition-ktx</b> coordinates and
         * with version <b>1.5.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTransition() {
            return create("androidx.transition");
        }

        /**
         * Dependency provider for <b>vectors</b> with <b>androidx.vectordrawable:vectordrawable</b> coordinates and
         * with version reference <b>androidx.vectordrawable</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getVectors() {
            return create("androidx.vectors");
        }

        /**
         * Dependency provider for <b>viewpager</b> with <b>androidx.viewpager:viewpager</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getViewpager() {
            return create("androidx.viewpager");
        }

        /**
         * Dependency provider for <b>viewpager2</b> with <b>androidx.viewpager2:viewpager2</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getViewpager2() {
            return create("androidx.viewpager2");
        }

        /**
         * Group of libraries at <b>androidx.animated</b>
         */
        public AndroidxAnimatedLibraryAccessors getAnimated() {
            return laccForAndroidxAnimatedLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.benchmark</b>
         */
        public AndroidxBenchmarkLibraryAccessors getBenchmark() {
            return laccForAndroidxBenchmarkLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.core</b>
         */
        public AndroidxCoreLibraryAccessors getCore() {
            return laccForAndroidxCoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.fragment</b>
         */
        public AndroidxFragmentLibraryAccessors getFragment() {
            return laccForAndroidxFragmentLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.nav</b>
         */
        public AndroidxNavLibraryAccessors getNav() {
            return laccForAndroidxNavLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.tracing</b>
         */
        public AndroidxTracingLibraryAccessors getTracing() {
            return laccForAndroidxTracingLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.window</b>
         */
        public AndroidxWindowLibraryAccessors getWindow() {
            return laccForAndroidxWindowLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.work</b>
         */
        public AndroidxWorkLibraryAccessors getWork() {
            return laccForAndroidxWorkLibraryAccessors;
        }

    }

    public static class AndroidxAnimatedLibraryAccessors extends SubDependencyFactory {

        public AndroidxAnimatedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>vectors</b> with <b>androidx.vectordrawable:vectordrawable-animated</b> coordinates and
         * with version reference <b>androidx.vectordrawable</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getVectors() {
            return create("androidx.animated.vectors");
        }

    }

    public static class AndroidxBenchmarkLibraryAccessors extends SubDependencyFactory {

        public AndroidxBenchmarkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit4</b> with <b>androidx.benchmark:benchmark-junit4</b> coordinates and
         * with version reference <b>benchmark.junit4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit4() {
            return create("androidx.benchmark.junit4");
        }

    }

    public static class AndroidxCoreLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxCoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.core:core</b> coordinates and
         * with version <b>1.13.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidx.core");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version <b>1.13.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.core.ktx");
        }

    }

    public static class AndroidxFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.fragment:fragment-ktx</b> coordinates and
         * with version <b>1.6.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.fragment.ktx");
        }

    }

    public static class AndroidxNavLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>fragment</b> with <b>androidx.navigation:navigation-fragment-ktx</b> coordinates and
         * with version reference <b>androidx.navigation</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFragment() {
            return create("androidx.nav.fragment");
        }

        /**
         * Dependency provider for <b>ui</b> with <b>androidx.navigation:navigation-ui-ktx</b> coordinates and
         * with version reference <b>androidx.navigation</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUi() {
            return create("androidx.nav.ui");
        }

    }

    public static class AndroidxTracingLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxTracingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>tracing</b> with <b>androidx.tracing:tracing</b> coordinates and
         * with version <b>1.2.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidx.tracing");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.tracing:tracing-ktx</b> coordinates and
         * with version <b>1.2.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.tracing.ktx");
        }

    }

    public static class AndroidxWindowLibraryAccessors extends SubDependencyFactory {

        public AndroidxWindowLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>v1alpha9</b> with <b>androidx.window:window</b> coordinates and
         * with version <b>1.0.0-alpha09</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getV1alpha9() {
            return create("androidx.window.v1alpha9");
        }

    }

    public static class AndroidxWorkLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxWorkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>work</b> with <b>androidx.work:work-runtime</b> coordinates and
         * with version reference <b>androidx.work</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidx.work");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.work:work-runtime-ktx</b> coordinates and
         * with version reference <b>androidx.work</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.work.ktx");
        }

    }

    public static class CommonLibraryAccessors extends SubDependencyFactory {
        private final CommonAntlr4LibraryAccessors laccForCommonAntlr4LibraryAccessors = new CommonAntlr4LibraryAccessors(owner);
        private final CommonEventbusLibraryAccessors laccForCommonEventbusLibraryAccessors = new CommonEventbusLibraryAccessors(owner);
        private final CommonGlideLibraryAccessors laccForCommonGlideLibraryAccessors = new CommonGlideLibraryAccessors(owner);
        private final CommonKotlinLibraryAccessors laccForCommonKotlinLibraryAccessors = new CommonKotlinLibraryAccessors(owner);
        private final CommonMarkwonLibraryAccessors laccForCommonMarkwonLibraryAccessors = new CommonMarkwonLibraryAccessors(owner);
        private final CommonRetrofitLibraryAccessors laccForCommonRetrofitLibraryAccessors = new CommonRetrofitLibraryAccessors(owner);

        public CommonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>charts</b> with <b>com.github.AppDevNext:AndroidChart</b> coordinates and
         * with version <b>3.1.0.21</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCharts() {
            return create("common.charts");
        }

        /**
         * Dependency provider for <b>editor</b> with <b>io.github.Rosemoe.sora-editor:editor</b> coordinates and
         * with version reference <b>editor</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEditor() {
            return create("common.editor");
        }

        /**
         * Dependency provider for <b>hiddenApiBypass</b> with <b>org.lsposed.hiddenapibypass:hiddenapibypass</b> coordinates and
         * with version <b>4.3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHiddenApiBypass() {
            return create("common.hiddenApiBypass");
        }

        /**
         * Dependency provider for <b>io</b> with <b>commons-io:commons-io</b> coordinates and
         * with version <b>2.15.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getIo() {
            return create("common.io");
        }

        /**
         * Dependency provider for <b>javaparser</b> with <b>com.github.javaparser:javaparser-symbol-solver-core</b> coordinates and
         * with version <b>3.26.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavaparser() {
            return create("common.javaparser");
        }

        /**
         * Dependency provider for <b>javapoet</b> with <b>com.squareup:javapoet</b> coordinates and
         * with version <b>1.13.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavapoet() {
            return create("common.javapoet");
        }

        /**
         * Dependency provider for <b>jkotlin</b> with <b>org.jetbrains.kotlin:kotlin-stdlib-jdk8</b> coordinates and
         * with version reference <b>kotlin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJkotlin() {
            return create("common.jkotlin");
        }

        /**
         * Dependency provider for <b>jsonrpc</b> with <b>org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc</b> coordinates and
         * with version <b>0.22.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJsonrpc() {
            return create("common.jsonrpc");
        }

        /**
         * Dependency provider for <b>jsoup</b> with <b>org.jsoup:jsoup</b> coordinates and
         * with version <b>1.17.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJsoup() {
            return create("common.jsoup");
        }

        /**
         * Dependency provider for <b>ksp</b> with <b>com.google.devtools.ksp:symbol-processing-api</b> coordinates and
         * with version reference <b>ksp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKsp() {
            return create("common.ksp");
        }

        /**
         * Dependency provider for <b>lang3</b> with <b>org.apache.commons:commons-lang3</b> coordinates and
         * with version <b>3.14.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLang3() {
            return create("common.lang3");
        }

        /**
         * Dependency provider for <b>leakcanary</b> with <b>com.squareup.leakcanary:leakcanary-android</b> coordinates and
         * with version <b>2.13</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLeakcanary() {
            return create("common.leakcanary");
        }

        /**
         * Dependency provider for <b>termuxAmLib</b> with <b>com.termux:termux-am-library</b> coordinates and
         * with version <b>v2.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTermuxAmLib() {
            return create("common.termuxAmLib");
        }

        /**
         * Dependency provider for <b>utilcode</b> with <b>com.blankj:utilcodex</b> coordinates and
         * with version <b>1.31.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUtilcode() {
            return create("common.utilcode");
        }

        /**
         * Group of libraries at <b>common.antlr4</b>
         */
        public CommonAntlr4LibraryAccessors getAntlr4() {
            return laccForCommonAntlr4LibraryAccessors;
        }

        /**
         * Group of libraries at <b>common.eventbus</b>
         */
        public CommonEventbusLibraryAccessors getEventbus() {
            return laccForCommonEventbusLibraryAccessors;
        }

        /**
         * Group of libraries at <b>common.glide</b>
         */
        public CommonGlideLibraryAccessors getGlide() {
            return laccForCommonGlideLibraryAccessors;
        }

        /**
         * Group of libraries at <b>common.kotlin</b>
         */
        public CommonKotlinLibraryAccessors getKotlin() {
            return laccForCommonKotlinLibraryAccessors;
        }

        /**
         * Group of libraries at <b>common.markwon</b>
         */
        public CommonMarkwonLibraryAccessors getMarkwon() {
            return laccForCommonMarkwonLibraryAccessors;
        }

        /**
         * Group of libraries at <b>common.retrofit</b>
         */
        public CommonRetrofitLibraryAccessors getRetrofit() {
            return laccForCommonRetrofitLibraryAccessors;
        }

    }

    public static class CommonAntlr4LibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public CommonAntlr4LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>antlr4</b> with <b>org.antlr:antlr4</b> coordinates and
         * with version reference <b>antlr4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("common.antlr4");
        }

        /**
         * Dependency provider for <b>runtime</b> with <b>org.antlr:antlr4-runtime</b> coordinates and
         * with version reference <b>antlr4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("common.antlr4.runtime");
        }

    }

    public static class CommonEventbusLibraryAccessors extends SubDependencyFactory {

        public CommonEventbusLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ap</b> with <b>org.greenrobot:eventbus-annotation-processor</b> coordinates and
         * with version <b>3.3.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAp() {
            return create("common.eventbus.ap");
        }

    }

    public static class CommonGlideLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public CommonGlideLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>glide</b> with <b>com.github.bumptech.glide:glide</b> coordinates and
         * with version reference <b>glide</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("common.glide");
        }

        /**
         * Dependency provider for <b>ap</b> with <b>com.github.bumptech.glide:compiler</b> coordinates and
         * with version reference <b>glide</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAp() {
            return create("common.glide.ap");
        }

    }

    public static class CommonKotlinLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final CommonKotlinCoroutinesLibraryAccessors laccForCommonKotlinCoroutinesLibraryAccessors = new CommonKotlinCoroutinesLibraryAccessors(owner);

        public CommonKotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>kotlin</b> with <b>org.jetbrains.kotlin:kotlin-stdlib-jdk8</b> coordinates and
         * with version reference <b>kotlin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("common.kotlin");
        }

        /**
         * Group of libraries at <b>common.kotlin.coroutines</b>
         */
        public CommonKotlinCoroutinesLibraryAccessors getCoroutines() {
            return laccForCommonKotlinCoroutinesLibraryAccessors;
        }

    }

    public static class CommonKotlinCoroutinesLibraryAccessors extends SubDependencyFactory {

        public CommonKotlinCoroutinesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-android</b> coordinates and
         * with version reference <b>kotlin.coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("common.kotlin.coroutines.android");
        }

    }

    public static class CommonMarkwonLibraryAccessors extends SubDependencyFactory {

        public CommonMarkwonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>io.noties.markwon:core</b> coordinates and
         * with version reference <b>markwon</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("common.markwon.core");
        }

        /**
         * Dependency provider for <b>extStrikethrough</b> with <b>io.noties.markwon:ext-strikethrough</b> coordinates and
         * with version reference <b>markwon</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getExtStrikethrough() {
            return create("common.markwon.extStrikethrough");
        }

        /**
         * Dependency provider for <b>linkify</b> with <b>io.noties.markwon:linkify</b> coordinates and
         * with version reference <b>markwon</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLinkify() {
            return create("common.markwon.linkify");
        }

        /**
         * Dependency provider for <b>recycler</b> with <b>io.noties.markwon:recycler</b> coordinates and
         * with version reference <b>markwon</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRecycler() {
            return create("common.markwon.recycler");
        }

    }

    public static class CommonRetrofitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public CommonRetrofitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>retrofit</b> with <b>com.squareup.retrofit2:retrofit</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("common.retrofit");
        }

        /**
         * Dependency provider for <b>gson</b> with <b>com.squareup.retrofit2:converter-gson</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("common.retrofit.gson");
        }

    }

    public static class CompositeLibraryAccessors extends SubDependencyFactory {

        public CompositeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>appintro</b> with <b>com.itsaky.androidide.build:appintro</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAppintro() {
            return create("composite.appintro");
        }

        /**
         * Dependency provider for <b>desugaringCore</b> with <b>com.itsaky.androidide.build:desugaring-core</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getDesugaringCore() {
            return create("composite.desugaringCore");
        }

        /**
         * Dependency provider for <b>fuzzysearch</b> with <b>com.itsaky.androidide.build:fuzzysearch</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFuzzysearch() {
            return create("composite.fuzzysearch");
        }

        /**
         * Dependency provider for <b>googleJavaFormat</b> with <b>com.itsaky.androidide.build:google-java-format</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGoogleJavaFormat() {
            return create("composite.googleJavaFormat");
        }

        /**
         * Dependency provider for <b>javaCompiler</b> with <b>com.itsaky.androidide.build:java-compiler</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavaCompiler() {
            return create("composite.javaCompiler");
        }

        /**
         * Dependency provider for <b>javac</b> with <b>com.itsaky.androidide.build:javac</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavac() {
            return create("composite.javac");
        }

        /**
         * Dependency provider for <b>javapoet</b> with <b>com.itsaky.androidide.build:javapoet</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavapoet() {
            return create("composite.javapoet");
        }

        /**
         * Dependency provider for <b>jaxp</b> with <b>com.itsaky.androidide.build:jaxp</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJaxp() {
            return create("composite.jaxp");
        }

        /**
         * Dependency provider for <b>jdkCompiler</b> with <b>com.itsaky.androidide.build:jdk-compiler</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJdkCompiler() {
            return create("composite.jdkCompiler");
        }

        /**
         * Dependency provider for <b>jdkJdeps</b> with <b>com.itsaky.androidide.build:jdk-jdeps</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJdkJdeps() {
            return create("composite.jdkJdeps");
        }

        /**
         * Dependency provider for <b>jdt</b> with <b>com.itsaky.androidide.build:jdt</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJdt() {
            return create("composite.jdt");
        }

        /**
         * Dependency provider for <b>layoutlibApi</b> with <b>com.itsaky.androidide.build:layoutlib-api</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLayoutlibApi() {
            return create("composite.layoutlibApi");
        }

    }

    public static class GitLibraryAccessors extends SubDependencyFactory {

        public GitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jgit</b> with <b>org.eclipse.jgit:org.eclipse.jgit</b> coordinates and
         * with version <b>6.8.0.202311291450-r</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJgit() {
            return create("git.jgit");
        }

    }

    public static class GoogleLibraryAccessors extends SubDependencyFactory {
        private final GoogleAutoLibraryAccessors laccForGoogleAutoLibraryAccessors = new GoogleAutoLibraryAccessors(owner);
        private final GoogleJavaLibraryAccessors laccForGoogleJavaLibraryAccessors = new GoogleJavaLibraryAccessors(owner);

        public GoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>flexbox</b> with <b>com.google.android.flexbox:flexbox</b> coordinates and
         * with version <b>3.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFlexbox() {
            return create("google.flexbox");
        }

        /**
         * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
         * with version <b>2.10.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("google.gson");
        }

        /**
         * Dependency provider for <b>guava</b> with <b>com.google.guava:guava</b> coordinates and
         * with version <b>32.1.3-android</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGuava() {
            return create("google.guava");
        }

        /**
         * Dependency provider for <b>material</b> with <b>com.google.android.material:material</b> coordinates and
         * with version <b>1.11.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaterial() {
            return create("google.material");
        }

        /**
         * Dependency provider for <b>protobuf</b> with <b>com.google.protobuf:protobuf-javalite</b> coordinates and
         * with version <b>3.25.3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getProtobuf() {
            return create("google.protobuf");
        }

        /**
         * Group of libraries at <b>google.auto</b>
         */
        public GoogleAutoLibraryAccessors getAuto() {
            return laccForGoogleAutoLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.java</b>
         */
        public GoogleJavaLibraryAccessors getJava() {
            return laccForGoogleJavaLibraryAccessors;
        }

    }

    public static class GoogleAutoLibraryAccessors extends SubDependencyFactory {
        private final GoogleAutoServiceLibraryAccessors laccForGoogleAutoServiceLibraryAccessors = new GoogleAutoServiceLibraryAccessors(owner);
        private final GoogleAutoValueLibraryAccessors laccForGoogleAutoValueLibraryAccessors = new GoogleAutoValueLibraryAccessors(owner);

        public GoogleAutoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.auto.service</b>
         */
        public GoogleAutoServiceLibraryAccessors getService() {
            return laccForGoogleAutoServiceLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.auto.value</b>
         */
        public GoogleAutoValueLibraryAccessors getValue() {
            return laccForGoogleAutoValueLibraryAccessors;
        }

    }

    public static class GoogleAutoServiceLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public GoogleAutoServiceLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>service</b> with <b>com.google.auto.service:auto-service</b> coordinates and
         * with version <b>1.1.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("google.auto.service");
        }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.google.auto.service:auto-service-annotations</b> coordinates and
         * with version <b>1.1.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("google.auto.service.annotations");
        }

    }

    public static class GoogleAutoValueLibraryAccessors extends SubDependencyFactory {

        public GoogleAutoValueLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.google.auto.value:auto-value-annotations</b> coordinates and
         * with version <b>1.10.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("google.auto.value.annotations");
        }

        /**
         * Dependency provider for <b>ap</b> with <b>com.google.auto.value:auto-value</b> coordinates and
         * with version <b>1.10.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAp() {
            return create("google.auto.value.ap");
        }

    }

    public static class GoogleJavaLibraryAccessors extends SubDependencyFactory {

        public GoogleJavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>format</b> with <b>com.google.googlejavaformat:google-java-format</b> coordinates and
         * with version <b>1.20.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFormat() {
            return create("google.java.format");
        }

    }

    public static class KotlinLibraryAccessors extends SubDependencyFactory {
        private final KotlinGradleLibraryAccessors laccForKotlinGradleLibraryAccessors = new KotlinGradleLibraryAccessors(owner);

        public KotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>kotlin.gradle</b>
         */
        public KotlinGradleLibraryAccessors getGradle() {
            return laccForKotlinGradleLibraryAccessors;
        }

    }

    public static class KotlinGradleLibraryAccessors extends SubDependencyFactory {

        public KotlinGradleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>plugin</b> with <b>org.jetbrains.kotlin:kotlin-gradle-plugin</b> coordinates and
         * with version reference <b>kotlin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlugin() {
            return create("kotlin.gradle.plugin");
        }

    }

    public static class LoggingLibraryAccessors extends SubDependencyFactory {
        private final LoggingLogbackLibraryAccessors laccForLoggingLogbackLibraryAccessors = new LoggingLogbackLibraryAccessors(owner);

        public LoggingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>logging.logback</b>
         */
        public LoggingLogbackLibraryAccessors getLogback() {
            return laccForLoggingLogbackLibraryAccessors;
        }

    }

    public static class LoggingLogbackLibraryAccessors extends SubDependencyFactory {

        public LoggingLogbackLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>classic</b> with <b>ch.qos.logback:logback-classic</b> coordinates and
         * with version reference <b>logback</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClassic() {
            return create("logging.logback.classic");
        }

        /**
         * Dependency provider for <b>core</b> with <b>com.itsaky.androidide.build:logback-core</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("logging.logback.core");
        }

    }

    public static class MavenLibraryAccessors extends SubDependencyFactory {

        public MavenLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>publish</b> with <b>com.vanniktech:gradle-maven-publish-plugin</b> coordinates and
         * with version reference <b>maven.publish.plugin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPublish() {
            return create("maven.publish");
        }

    }

    public static class NavLibraryAccessors extends SubDependencyFactory {
        private final NavSafeLibraryAccessors laccForNavSafeLibraryAccessors = new NavSafeLibraryAccessors(owner);

        public NavLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>nav.safe</b>
         */
        public NavSafeLibraryAccessors getSafe() {
            return laccForNavSafeLibraryAccessors;
        }

    }

    public static class NavSafeLibraryAccessors extends SubDependencyFactory {
        private final NavSafeArgsLibraryAccessors laccForNavSafeArgsLibraryAccessors = new NavSafeArgsLibraryAccessors(owner);

        public NavSafeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>nav.safe.args</b>
         */
        public NavSafeArgsLibraryAccessors getArgs() {
            return laccForNavSafeArgsLibraryAccessors;
        }

    }

    public static class NavSafeArgsLibraryAccessors extends SubDependencyFactory {
        private final NavSafeArgsGradleLibraryAccessors laccForNavSafeArgsGradleLibraryAccessors = new NavSafeArgsGradleLibraryAccessors(owner);

        public NavSafeArgsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>nav.safe.args.gradle</b>
         */
        public NavSafeArgsGradleLibraryAccessors getGradle() {
            return laccForNavSafeArgsGradleLibraryAccessors;
        }

    }

    public static class NavSafeArgsGradleLibraryAccessors extends SubDependencyFactory {

        public NavSafeArgsGradleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>plugin</b> with <b>androidx.navigation:navigation-safe-args-gradle-plugin</b> coordinates and
         * with version reference <b>androidx.navigation</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlugin() {
            return create("nav.safe.args.gradle.plugin");
        }

    }

    public static class TestsLibraryAccessors extends SubDependencyFactory {
        private final TestsAndroidxLibraryAccessors laccForTestsAndroidxLibraryAccessors = new TestsAndroidxLibraryAccessors(owner);
        private final TestsGoogleLibraryAccessors laccForTestsGoogleLibraryAccessors = new TestsGoogleLibraryAccessors(owner);
        private final TestsJunitLibraryAccessors laccForTestsJunitLibraryAccessors = new TestsJunitLibraryAccessors(owner);
        private final TestsMockitoLibraryAccessors laccForTestsMockitoLibraryAccessors = new TestsMockitoLibraryAccessors(owner);

        public TestsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>barista</b> with <b>com.adevinta.android:barista</b> coordinates and
         * with version <b>4.3.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBarista() {
            return create("tests.barista");
        }

        /**
         * Dependency provider for <b>mockk</b> with <b>io.mockk:mockk</b> coordinates and
         * with version <b>1.13.10</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMockk() {
            return create("tests.mockk");
        }

        /**
         * Dependency provider for <b>robolectric</b> with <b>org.robolectric:robolectric</b> coordinates and
         * with version <b>4.11.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRobolectric() {
            return create("tests.robolectric");
        }

        /**
         * Group of libraries at <b>tests.androidx</b>
         */
        public TestsAndroidxLibraryAccessors getAndroidx() {
            return laccForTestsAndroidxLibraryAccessors;
        }

        /**
         * Group of libraries at <b>tests.google</b>
         */
        public TestsGoogleLibraryAccessors getGoogle() {
            return laccForTestsGoogleLibraryAccessors;
        }

        /**
         * Group of libraries at <b>tests.junit</b>
         */
        public TestsJunitLibraryAccessors getJunit() {
            return laccForTestsJunitLibraryAccessors;
        }

        /**
         * Group of libraries at <b>tests.mockito</b>
         */
        public TestsMockitoLibraryAccessors getMockito() {
            return laccForTestsMockitoLibraryAccessors;
        }

    }

    public static class TestsAndroidxLibraryAccessors extends SubDependencyFactory {
        private final TestsAndroidxEspressoLibraryAccessors laccForTestsAndroidxEspressoLibraryAccessors = new TestsAndroidxEspressoLibraryAccessors(owner);
        private final TestsAndroidxTestLibraryAccessors laccForTestsAndroidxTestLibraryAccessors = new TestsAndroidxTestLibraryAccessors(owner);
        private final TestsAndroidxWorkLibraryAccessors laccForTestsAndroidxWorkLibraryAccessors = new TestsAndroidxWorkLibraryAccessors(owner);

        public TestsAndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version <b>1.1.5</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("tests.androidx.junit");
        }

        /**
         * Dependency provider for <b>uiautomator</b> with <b>androidx.test.uiautomator:uiautomator</b> coordinates and
         * with version <b>2.3.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUiautomator() {
            return create("tests.androidx.uiautomator");
        }

        /**
         * Group of libraries at <b>tests.androidx.espresso</b>
         */
        public TestsAndroidxEspressoLibraryAccessors getEspresso() {
            return laccForTestsAndroidxEspressoLibraryAccessors;
        }

        /**
         * Group of libraries at <b>tests.androidx.test</b>
         */
        public TestsAndroidxTestLibraryAccessors getTest() {
            return laccForTestsAndroidxTestLibraryAccessors;
        }

        /**
         * Group of libraries at <b>tests.androidx.work</b>
         */
        public TestsAndroidxWorkLibraryAccessors getWork() {
            return laccForTestsAndroidxWorkLibraryAccessors;
        }

    }

    public static class TestsAndroidxEspressoLibraryAccessors extends SubDependencyFactory {

        public TestsAndroidxEspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>contrib</b> with <b>androidx.test.espresso:espresso-contrib</b> coordinates and
         * with version reference <b>androidx.espresso</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getContrib() {
            return create("tests.androidx.espresso.contrib");
        }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test.espresso:espresso-core</b> coordinates and
         * with version reference <b>androidx.espresso</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("tests.androidx.espresso.core");
        }

    }

    public static class TestsAndroidxTestLibraryAccessors extends SubDependencyFactory {

        public TestsAndroidxTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test:core</b> coordinates and
         * with version <b>1.5.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("tests.androidx.test.core");
        }

        /**
         * Dependency provider for <b>rules</b> with <b>androidx.test:rules</b> coordinates and
         * with version <b>1.5.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRules() {
            return create("tests.androidx.test.rules");
        }

        /**
         * Dependency provider for <b>runner</b> with <b>androidx.test:runner</b> coordinates and
         * with version <b>1.5.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRunner() {
            return create("tests.androidx.test.runner");
        }

    }

    public static class TestsAndroidxWorkLibraryAccessors extends SubDependencyFactory {

        public TestsAndroidxWorkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>testing</b> with <b>androidx.work:work-testing</b> coordinates and
         * with version reference <b>androidx.work</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTesting() {
            return create("tests.androidx.work.testing");
        }

    }

    public static class TestsGoogleLibraryAccessors extends SubDependencyFactory {

        public TestsGoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>truth</b> with <b>com.google.truth:truth</b> coordinates and
         * with version <b>1.4.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTruth() {
            return create("tests.google.truth");
        }

    }

    public static class TestsJunitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public TestsJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
         * with version <b>4.13.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("tests.junit");
        }

        /**
         * Dependency provider for <b>jupiter</b> with <b>org.junit.jupiter:junit-jupiter</b> coordinates and
         * with version reference <b>junit.jupiter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJupiter() {
            return create("tests.junit.jupiter");
        }

        /**
         * Dependency provider for <b>platformLauncher</b> with <b>org.junit.platform:junit-platform-launcher</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlatformLauncher() {
            return create("tests.junit.platformLauncher");
        }

    }

    public static class TestsMockitoLibraryAccessors extends SubDependencyFactory {

        public TestsMockitoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>kotlin</b> with <b>org.mockito.kotlin:mockito-kotlin</b> coordinates and
         * with version <b>5.2.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKotlin() {
            return create("tests.mockito.kotlin");
        }

    }

    public static class ToolingLibraryAccessors extends SubDependencyFactory {

        public ToolingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>builderModel</b> with <b>com.android.tools.build:builder-model</b> coordinates and
         * with version reference <b>agp.tooling</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBuilderModel() {
            return create("tooling.builderModel");
        }

        /**
         * Dependency provider for <b>gradleApi</b> with <b>com.itsaky.androidide.gradle:gradle-tooling-api</b> coordinates and
         * with version reference <b>gradle.tooling</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradleApi() {
            return create("tooling.gradleApi");
        }

        /**
         * Dependency provider for <b>slf4j</b> with <b>org.slf4j:slf4j-api</b> coordinates and
         * with version <b>2.0.12</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSlf4j() {
            return create("tooling.slf4j");
        }

    }

    public static class XmlLibraryAccessors extends SubDependencyFactory {
        private final XmlJbLibraryAccessors laccForXmlJbLibraryAccessors = new XmlJbLibraryAccessors(owner);

        public XmlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>apis</b> with <b>xml-apis:xml-apis</b> coordinates and
         * with version <b>2.0.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApis() {
            return create("xml.apis");
        }

        /**
         * Dependency provider for <b>remark</b> with <b>com.kotcrab.remark:remark</b> coordinates and
         * with version <b>1.2.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRemark() {
            return create("xml.remark");
        }

        /**
         * Dependency provider for <b>resolver</b> with <b>xml-resolver:xml-resolver</b> coordinates and
         * with version <b>1.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getResolver() {
            return create("xml.resolver");
        }

        /**
         * Dependency provider for <b>xercesImpl</b> with <b>xerces:xercesImpl</b> coordinates and
         * with version <b>2.12.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getXercesImpl() {
            return create("xml.xercesImpl");
        }

        /**
         * Group of libraries at <b>xml.jb</b>
         */
        public XmlJbLibraryAccessors getJb() {
            return laccForXmlJbLibraryAccessors;
        }

    }

    public static class XmlJbLibraryAccessors extends SubDependencyFactory {

        public XmlJbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>org.jetbrains:annotations</b> coordinates and
         * with version <b>24.1.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("xml.jb.annotations");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final AgpVersionAccessors vaccForAgpVersionAccessors = new AgpVersionAccessors(providers, config);
        private final AndroidxVersionAccessors vaccForAndroidxVersionAccessors = new AndroidxVersionAccessors(providers, config);
        private final BenchmarkVersionAccessors vaccForBenchmarkVersionAccessors = new BenchmarkVersionAccessors(providers, config);
        private final GradleVersionAccessors vaccForGradleVersionAccessors = new GradleVersionAccessors(providers, config);
        private final JunitVersionAccessors vaccForJunitVersionAccessors = new JunitVersionAccessors(providers, config);
        private final KotlinVersionAccessors vaccForKotlinVersionAccessors = new KotlinVersionAccessors(providers, config);
        private final MavenVersionAccessors vaccForMavenVersionAccessors = new MavenVersionAccessors(providers, config);
        private final TreeVersionAccessors vaccForTreeVersionAccessors = new TreeVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>antlr4</b> with value <b>4.13.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAntlr4() { return getVersion("antlr4"); }

        /**
         * Version alias <b>editor</b> with value <b>0.23.4-ce8de8e-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEditor() { return getVersion("editor"); }

        /**
         * Version alias <b>glide</b> with value <b>4.16.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGlide() { return getVersion("glide"); }

        /**
         * Version alias <b>ksp</b> with value <b>1.9.24-1.0.20</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKsp() { return getVersion("ksp"); }

        /**
         * Version alias <b>logback</b> with value <b>1.5.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLogback() { return getVersion("logback"); }

        /**
         * Version alias <b>markwon</b> with value <b>4.6.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMarkwon() { return getVersion("markwon"); }

        /**
         * Version alias <b>realm</b> with value <b>10.18.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRealm() { return getVersion("realm"); }

        /**
         * Version alias <b>retrofit</b> with value <b>2.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRetrofit() { return getVersion("retrofit"); }

        /**
         * Group of versions at <b>versions.agp</b>
         */
        public AgpVersionAccessors getAgp() {
            return vaccForAgpVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.androidx</b>
         */
        public AndroidxVersionAccessors getAndroidx() {
            return vaccForAndroidxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.benchmark</b>
         */
        public BenchmarkVersionAccessors getBenchmark() {
            return vaccForBenchmarkVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.gradle</b>
         */
        public GradleVersionAccessors getGradle() {
            return vaccForGradleVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.junit</b>
         */
        public JunitVersionAccessors getJunit() {
            return vaccForJunitVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.kotlin</b>
         */
        public KotlinVersionAccessors getKotlin() {
            return vaccForKotlinVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.maven</b>
         */
        public MavenVersionAccessors getMaven() {
            return vaccForMavenVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.tree</b>
         */
        public TreeVersionAccessors getTree() {
            return vaccForTreeVersionAccessors;
        }

    }

    public static class AgpVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public AgpVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>agp</b> with value <b>8.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("agp"); }

        /**
         * Version alias <b>agp.tooling</b> with value <b>8.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTooling() { return getVersion("agp.tooling"); }

    }

    public static class AndroidxVersionAccessors extends VersionFactory  {

        public AndroidxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>androidx.espresso</b> with value <b>3.5.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEspresso() { return getVersion("androidx.espresso"); }

        /**
         * Version alias <b>androidx.navigation</b> with value <b>2.7.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNavigation() { return getVersion("androidx.navigation"); }

        /**
         * Version alias <b>androidx.vectordrawable</b> with value <b>1.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getVectordrawable() { return getVersion("androidx.vectordrawable"); }

        /**
         * Version alias <b>androidx.work</b> with value <b>2.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getWork() { return getVersion("androidx.work"); }

    }

    public static class BenchmarkVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public BenchmarkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>benchmark</b> with value <b>1.2.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("benchmark"); }

        /**
         * Version alias <b>benchmark.junit4</b> with value <b>1.2.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit4() { return getVersion("benchmark.junit4"); }

    }

    public static class GradleVersionAccessors extends VersionFactory  {

        public GradleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>gradle.tooling</b> with value <b>8.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTooling() { return getVersion("gradle.tooling"); }

    }

    public static class JunitVersionAccessors extends VersionFactory  {

        public JunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>junit.jupiter</b> with value <b>5.10.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJupiter() { return getVersion("junit.jupiter"); }

    }

    public static class KotlinVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public KotlinVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>kotlin</b> with value <b>1.9.24</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("kotlin"); }

        /**
         * Version alias <b>kotlin.coroutines</b> with value <b>1.8.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoroutines() { return getVersion("kotlin.coroutines"); }

    }

    public static class MavenVersionAccessors extends VersionFactory  {

        private final MavenPublishVersionAccessors vaccForMavenPublishVersionAccessors = new MavenPublishVersionAccessors(providers, config);
        public MavenVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.maven.publish</b>
         */
        public MavenPublishVersionAccessors getPublish() {
            return vaccForMavenPublishVersionAccessors;
        }

    }

    public static class MavenPublishVersionAccessors extends VersionFactory  {

        public MavenPublishVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>maven.publish.plugin</b> with value <b>0.29.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPlugin() { return getVersion("maven.publish.plugin"); }

    }

    public static class TreeVersionAccessors extends VersionFactory  {

        public TreeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>tree.sitter</b> with value <b>4.3.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSitter() { return getVersion("tree.sitter"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final GradlePluginAccessors paccForGradlePluginAccessors = new GradlePluginAccessors(providers, config);
        private final KotlinPluginAccessors paccForKotlinPluginAccessors = new KotlinPluginAccessors(providers, config);
        private final MavenPluginAccessors paccForMavenPluginAccessors = new MavenPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>benchmark</b> with plugin id <b>androidx.benchmark</b> and
         * with version reference <b>benchmark</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getBenchmark() { return createPlugin("benchmark"); }

        /**
         * Plugin provider for <b>protobuf</b> with plugin id <b>com.google.protobuf</b> and
         * with version <b>0.9.4</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getProtobuf() { return createPlugin("protobuf"); }

        /**
         * Group of plugins at <b>plugins.android</b>
         */
        public AndroidPluginAccessors getAndroid() {
            return paccForAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.gradle</b>
         */
        public GradlePluginAccessors getGradle() {
            return paccForGradlePluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.kotlin</b>
         */
        public KotlinPluginAccessors getKotlin() {
            return paccForKotlinPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.maven</b>
         */
        public MavenPluginAccessors getMaven() {
            return paccForMavenPluginAccessors;
        }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>android.application</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

        /**
         * Plugin provider for <b>android.library</b> with plugin id <b>com.android.library</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLibrary() { return createPlugin("android.library"); }

    }

    public static class GradlePluginAccessors extends PluginFactory {

        public GradlePluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>gradle.publish</b> with plugin id <b>com.gradle.plugin-publish</b> and
         * with version <b>1.2.1</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getPublish() { return createPlugin("gradle.publish"); }

    }

    public static class KotlinPluginAccessors extends PluginFactory {

        public KotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>kotlin.android</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("kotlin.android"); }

        /**
         * Plugin provider for <b>kotlin.jvm</b> with plugin id <b>org.jetbrains.kotlin.jvm</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getJvm() { return createPlugin("kotlin.jvm"); }

    }

    public static class MavenPluginAccessors extends PluginFactory {

        public MavenPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>maven.publish</b> with plugin id <b>com.vanniktech.maven.publish.base</b> and
         * with version reference <b>maven.publish.plugin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getPublish() { return createPlugin("maven.publish"); }

    }

}
