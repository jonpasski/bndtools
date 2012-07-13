package org.bndtools.core.ui.resource;

import org.bndtools.core.utils.resources.ResourceUtils;
import org.eclipse.jface.viewers.StyledString;
import org.osgi.framework.Version;
import org.osgi.resource.Capability;
import org.osgi.resource.Namespace;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;

public class RequirementWithResourceLabelProvider extends RequirementLabelProvider {

    @Override
    public StyledString getLabel(Requirement requirement) {
        StyledString label = super.getLabel(requirement);

        if (Namespace.RESOLUTION_OPTIONAL.equals(requirement.getDirectives().get(Namespace.REQUIREMENT_RESOLUTION_DIRECTIVE)))
            label.append(" OPTIONALLY", StyledString.QUALIFIER_STYLER);
        label.append(" REQUIRED BY ", StyledString.QUALIFIER_STYLER);

        Resource resource = requirement.getResource();
        if (resource != null)
            appendResourceLabel(label, resource);
        else
            label.append(" INITIAL");

        return label;
    }

    protected void appendResourceLabel(StyledString label, Resource resource) {
        Capability identity = ResourceUtils.getIdentityCapability(resource);
        String name = ResourceUtils.getIdentity(identity);
        if (name == null)
            name = resource.toString();
        if (name == null)
            name = "<unknown>";
        label.append(name);

        Version version = ResourceUtils.getVersion(identity);
        if (version != null)
            label.append(" " + version, StyledString.COUNTER_STYLER);
    }

}
