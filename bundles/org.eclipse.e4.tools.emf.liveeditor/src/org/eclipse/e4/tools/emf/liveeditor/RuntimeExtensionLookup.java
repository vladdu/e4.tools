package org.eclipse.e4.tools.emf.liveeditor;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.e4.tools.emf.ui.common.IExtensionLookup;

public class RuntimeExtensionLookup implements IExtensionLookup {

	public IExtension[] findExtensions(String extensionPointId, boolean liveModel) {
		IExtensionRegistry registry = RegistryFactory.getRegistry();
		return registry.getExtensionPoint(extensionPointId).getExtensions();
	}

}
