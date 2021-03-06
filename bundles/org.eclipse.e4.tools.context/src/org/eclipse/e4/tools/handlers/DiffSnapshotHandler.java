/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.tools.handlers;

import org.eclipse.core.commands.*;
import org.eclipse.e4.core.internal.contexts.EclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Generates a diff of the main application context, showing changes since the
 * last snapshot.
 */
public class DiffSnapshotHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		MApplication app = (MApplication) HandlerUtil.getVariableChecked(event,
				MApplication.class.getName());
		((EclipseContext) app.getContext()).debugDiff();
		return null;
	}

}
