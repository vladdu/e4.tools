/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package org.eclipse.e4.tools.emf.ui.internal.common.component.virtual;

import java.util.List;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.tools.emf.ui.common.component.AbstractComponentEditor;
import org.eclipse.e4.tools.emf.ui.internal.Messages;
import org.eclipse.e4.tools.emf.ui.internal.common.ComponentLabelProvider;
import org.eclipse.e4.tools.emf.ui.internal.common.ModelEditor;
import org.eclipse.e4.tools.emf.ui.internal.common.VirtualEntry;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContributions;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuPackageImpl;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class VMenuContributionsEditor extends AbstractComponentEditor {
	private Composite composite;
	private EMFDataBindingContext context;
	private TableViewer viewer;

	public VMenuContributionsEditor(EditingDomain editingDomain, ModelEditor editor) {
		super(editingDomain, editor);
	}

	@Override
	public Image getImage(Object element, Display display) {
		return null;
	}

	@Override
	public String getLabel(Object element) {
		return Messages.VMenuContributionsEditor_TreeLabel;
	}

	@Override
	public String getDetailLabel(Object element) {
		return null;
	}

	@Override
	public String getDescription(Object element) {
		return Messages.VMenuContributionsEditor_TreeLabelDescription;
	}

	@Override
	public Composite getEditor(Composite parent, Object object) {
		if (composite == null) {
			context = new EMFDataBindingContext();
			composite = createForm(parent, context, getMaster());
		}
		VirtualEntry<?> o = (VirtualEntry<?>) object;
		viewer.setInput(o.getList());
		getMaster().setValue(o.getOriginalParent());
		return composite;
	}

	private Composite createForm(Composite parent, EMFDataBindingContext context, WritableValue master) {
		parent = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(3, false);
		gl.horizontalSpacing = 10;
		parent.setLayout(gl);

		{
			Label l = new Label(parent, SWT.NONE);
			l.setText(Messages.VMenuContributionsEditor_MenuContributions);
			l.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

			viewer = new TableViewer(parent);
			ObservableListContentProvider cp = new ObservableListContentProvider();
			viewer.setContentProvider(cp);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.heightHint = 300;
			viewer.getControl().setLayoutData(gd);
			viewer.setLabelProvider(new ComponentLabelProvider(getEditor()));

			Composite buttonComp = new Composite(parent, SWT.NONE);
			buttonComp.setLayoutData(new GridData(GridData.FILL, GridData.END, false, false));
			gl = new GridLayout();
			gl.marginLeft = 0;
			gl.marginRight = 0;
			gl.marginWidth = 0;
			gl.marginHeight = 0;
			buttonComp.setLayout(gl);

			Button b = new Button(buttonComp, SWT.PUSH | SWT.FLAT);
			b.setText(Messages.ModelTooling_Common_Up);
			b.setImage(getImage(b.getDisplay(), ARROW_UP));
			b.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!viewer.getSelection().isEmpty()) {
						IStructuredSelection s = (IStructuredSelection) viewer.getSelection();
						if (s.size() == 1) {
							Object obj = s.getFirstElement();
							MMenuContributions container = (MMenuContributions) getMaster().getValue();
							int idx = container.getMenuContributions().indexOf(obj) - 1;
							if (idx >= 0) {
								Command cmd = MoveCommand.create(getEditingDomain(), getMaster().getValue(), MenuPackageImpl.Literals.MENU_CONTRIBUTIONS__MENU_CONTRIBUTIONS, obj, idx);

								if (cmd.canExecute()) {
									getEditingDomain().getCommandStack().execute(cmd);
									viewer.setSelection(new StructuredSelection(obj));
								}
							}

						}
					}
				}
			});

			b = new Button(buttonComp, SWT.PUSH | SWT.FLAT);
			b.setText(Messages.ModelTooling_Common_Down);
			b.setImage(getImage(b.getDisplay(), ARROW_DOWN));
			b.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!viewer.getSelection().isEmpty()) {
						IStructuredSelection s = (IStructuredSelection) viewer.getSelection();
						if (s.size() == 1) {
							Object obj = s.getFirstElement();
							MMenuContributions container = (MMenuContributions) getMaster().getValue();
							int idx = container.getMenuContributions().indexOf(obj) + 1;
							if (idx < container.getMenuContributions().size()) {
								Command cmd = MoveCommand.create(getEditingDomain(), getMaster().getValue(), MenuPackageImpl.Literals.MENU_CONTRIBUTIONS__MENU_CONTRIBUTIONS, obj, idx);

								if (cmd.canExecute()) {
									getEditingDomain().getCommandStack().execute(cmd);
									viewer.setSelection(new StructuredSelection(obj));
								}
							}

						}
					}
				}
			});

			b = new Button(buttonComp, SWT.PUSH | SWT.FLAT);
			b.setText(Messages.ModelTooling_Common_AddEllipsis);
			b.setImage(getImage(b.getDisplay(), TABLE_ADD_IMAGE));
			b.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					MMenuContribution command = MMenuFactory.INSTANCE.createMenuContribution();
					setElementId(command);

					Command cmd = AddCommand.create(getEditingDomain(), getMaster().getValue(), MenuPackageImpl.Literals.MENU_CONTRIBUTIONS__MENU_CONTRIBUTIONS, command);

					if (cmd.canExecute()) {
						getEditingDomain().getCommandStack().execute(cmd);
						getEditor().setSelection(command);
					}
				}
			});

			b = new Button(buttonComp, SWT.PUSH | SWT.FLAT);
			b.setText(Messages.ModelTooling_Common_Remove);
			b.setImage(getImage(b.getDisplay(), TABLE_DELETE_IMAGE));
			b.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!viewer.getSelection().isEmpty()) {
						List<?> commands = ((IStructuredSelection) viewer.getSelection()).toList();
						Command cmd = RemoveCommand.create(getEditingDomain(), getMaster().getValue(), MenuPackageImpl.Literals.MENU_CONTRIBUTIONS__MENU_CONTRIBUTIONS, commands);
						if (cmd.canExecute()) {
							getEditingDomain().getCommandStack().execute(cmd);
						}
					}
				}
			});
		}

		return parent;
	}

	@Override
	public IObservableList getChildList(Object element) {
		return null;
	}
}
