package org.eclipse.e4.tools.emf.ui.internal.common.component;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.e4.tools.emf.ui.common.component.AbstractComponentEditor;
import org.eclipse.e4.ui.model.application.MApplicationPackage;
import org.eclipse.e4.ui.model.application.MPartSashContainer;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class PartSashContainerEditor extends AbstractComponentEditor {
	private Composite composite;
	private WritableValue master = new WritableValue();
	private Image vImage;
	private Image hImage;
	private DataBindingContext context;

	private IListProperty ELEMENT_CONTAINER__CHILDREN = EMFProperties.list(MApplicationPackage.Literals.ELEMENT_CONTAINER__CHILDREN);

	@Override
	public Image getImage(Object element, Display display) {
		boolean horizontal = ((MPartSashContainer)element).isHorizontal();

		if( vImage == null && ! horizontal ) {
			vImage = new Image(display, getClass().getClassLoader().getResourceAsStream("/icons/application_tile_vertical.png"));
		}

		if( hImage == null && horizontal ) {
			hImage = new Image(display, getClass().getClassLoader().getResourceAsStream("/icons/application_tile_horizontal.png"));
		}

		if( horizontal ) {
			return hImage;
		} else {
			return vImage;
		}
	}

	@Override
	public String getLabel(Object element) {
		return "Sash";
	}

	@Override
	public String getDescription(Object element) {
		return "Sash bla bla bla";
	}

	@Override
	public Composite getEditor(Composite parent, Object object) {
		if( composite == null ) {
			context = new DataBindingContext();
			composite = createForm(parent,context, master);
		}
		master.setValue(object);
		return composite;
	}

	private Composite createForm(Composite parent, DataBindingContext context2,
			WritableValue master) {
		parent = new Composite(parent,SWT.NONE);
		return parent;
	}

	@Override
	public IObservableList getChildList(Object element) {
		return ELEMENT_CONTAINER__CHILDREN.observe(element);
	}

	@Override
	public String getDetailLabel(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

}
