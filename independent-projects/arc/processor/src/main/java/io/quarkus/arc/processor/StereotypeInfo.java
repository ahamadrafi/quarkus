package io.quarkus.arc.processor;

import java.util.List;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;

public class StereotypeInfo {

    private final ScopeInfo defaultScope;
    private final List<AnnotationInstance> interceptorBindings;
    private final boolean alternative;
    private final Integer alternativePriority;
    private final boolean isNamed;
    private final boolean isInherited;
    private final List<AnnotationInstance> parentStereotypes;
    private final ClassInfo target;
    // used to differentiate between standard stereotype and one that was added through StereotypeRegistrarBuildItem
    private final boolean isAdditionalStereotype;

    /**
     *
     * @deprecated This constructor will be removed at some time after Quarkus 3.0
     */
    @Deprecated
    public StereotypeInfo(ScopeInfo defaultScope, List<AnnotationInstance> interceptorBindings, boolean alternative,
            Integer alternativePriority, boolean isNamed, boolean isAdditionalBeanDefiningAnnotation,
            boolean isAdditionalStereotype,
            ClassInfo target, boolean isInherited, List<AnnotationInstance> parentStereotypes) {
        this(defaultScope, interceptorBindings, alternative, alternativePriority, isNamed, isAdditionalStereotype, target,
                isInherited, parentStereotypes);
    }

    public StereotypeInfo(ScopeInfo defaultScope, List<AnnotationInstance> interceptorBindings, boolean alternative,
            Integer alternativePriority, boolean isNamed, boolean isAdditionalStereotype, ClassInfo target, boolean isInherited,
            List<AnnotationInstance> parentStereotypes) {
        this.defaultScope = defaultScope;
        this.interceptorBindings = interceptorBindings;
        this.alternative = alternative;
        this.alternativePriority = alternativePriority;
        this.isNamed = isNamed;
        this.isInherited = isInherited;
        this.parentStereotypes = parentStereotypes;
        this.target = target;
        this.isAdditionalStereotype = isAdditionalStereotype;
    }

    public StereotypeInfo(ScopeInfo defaultScope, List<AnnotationInstance> interceptorBindings, boolean alternative,
            Integer alternativePriority, boolean isNamed, ClassInfo target, boolean isInherited,
            List<AnnotationInstance> parentStereotype) {
        this(defaultScope, interceptorBindings, alternative, alternativePriority, isNamed, false, false, target, isInherited,
                parentStereotype);
    }

    public ScopeInfo getDefaultScope() {
        return defaultScope;
    }

    public List<AnnotationInstance> getInterceptorBindings() {
        return interceptorBindings;
    }

    public boolean isAlternative() {
        return alternative;
    }

    public boolean isInherited() {
        return isInherited;
    }

    public Integer getAlternativePriority() {
        return alternativePriority;
    }

    public boolean isNamed() {
        return isNamed;
    }

    public ClassInfo getTarget() {
        return target;
    }

    public DotName getName() {
        return target.name();
    }

    /**
     *
     * @deprecated This method will be removed at some time after Quarkus 3.0
     */
    @Deprecated
    public boolean isAdditionalBeanDefiningAnnotation() {
        return false;
    }

    /**
     * @deprecated use {@link #isAdditionalStereotype()};
     *             this method will be removed at some time after Quarkus 3.0
     */
    @Deprecated
    public boolean isAdditionalStereotypeBuildItem() {
        return isAdditionalStereotype;
    }

    public boolean isAdditionalStereotype() {
        return isAdditionalStereotype;
    }

    public boolean isGenuine() {
        return !isAdditionalStereotype;
    }

    public List<AnnotationInstance> getParentStereotypes() {
        return parentStereotypes;
    }
}
