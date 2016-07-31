/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.gef.mvc;

import org.eclipse.core.commands.operations.DefaultOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.common.adapt.inject.AdaptableTypeListener;
import org.eclipse.gef.common.adapt.inject.AdapterInjectionSupport;
import org.eclipse.gef.common.adapt.inject.AdapterMap;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.domain.AbstractDomain;
import org.eclipse.gef.mvc.domain.IDomain;
import org.eclipse.gef.mvc.parts.AbstractContentPart;
import org.eclipse.gef.mvc.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.parts.AbstractHandlePart;
import org.eclipse.gef.mvc.parts.AbstractRootPart;
import org.eclipse.gef.mvc.parts.AbstractVisualPart;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.multibindings.MapBinder;

/**
 * The Guice module which contains all (default) bindings related to the MVC
 * bundle. It is extended by the MVC.FX Guice module of the MVC.FX bundle, which
 * adds FX-specific (default) bindings.
 * <p>
 * In an Eclipse UI-integration scenario this module is intended to be
 * overwritten by the MVC.UI Guice module, which is provided by the MVC.UI
 * bundle (or, in case of the MVC.FX module, by the MVC.FX.UI module, which is
 * provided by the MVC.FX.UI bundle).
 * <p>
 * Generally, we recommended that all clients should create an own non-UI
 * module, which extends this module (or the MVC.FX module), as well as an own
 * UI module, which extends the MVC.UI (or the MVC.FX.UI module), being used to
 * override the non-UI module in an Eclipse-UI integration scenario, as follows:
 *
 * <pre>
 *
 *      MVC   &lt;--extends--    MVC.FX   &lt;--extends--  Client-Non-UI-Module
 *       ^                       ^                           ^
 *       |                       |                           |
 *   overrides               overrides                   overrides
 *       |                       |                           |
 *       |                       |                           |
 *    MVC.UI  &lt;--extends--  MVC.FX.UI  &lt;--extends--   Client-UI-Module
 * </pre>
 *
 * In addition to 'normal' Guice bindings, the MVC modules makes use of
 * <em>AdapterMap</em> bindings, which is used to inject class-key/adapter pairs
 * into {@link IAdaptable}s. Therefore, it enables support for adapter map
 * injections within {@link #enableAdapterMapInjection()}, which gets called
 * from within {@link #configure()}. Clients extending the MVC or MVC.FX module
 * should make use this is not lost.
 *
 * @see AdapterMap
 * @see AdaptableTypeListener
 *
 * @author anyssen
 *
 * @param <VR>
 *            The visual root node of the UI toolkit used, e.g.
 *            javafx.scene.Node in case of JavaFX.
 */
public class MvcModule<VR> extends AbstractModule {

	/**
	 * Adds (default) {@link AdapterMap} bindings for
	 * {@link AbstractContentPart} and all sub-classes. May be overwritten by
	 * sub-classes to change the default bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractContentPart} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractContentPartAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to do by default
	}

	/**
	 * Adds (default) {@link AdapterMap} bindings for {@link AbstractDomain} and
	 * all sub-classes. May be overwritten by sub-classes to change the default
	 * bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractDomain} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractDomainAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to bind by default
	}

	/**
	 * Adds (default) {@link AdapterMap} bindings for
	 * {@link AbstractFeedbackPart} and all sub-classes. May be overwritten by
	 * sub-classes to change the default bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractFeedbackPart} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractFeedbackPartAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to bind by default
	}

	/**
	 * Adds (default) {@link AdapterMap} bindings for {@link AbstractHandlePart}
	 * and all sub-classes. May be overwritten by sub-classes to change the
	 * default bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractHandlePart} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractHandlePartAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to bind by default
	}

	/**
	 * Adds (default) {@link AdapterMap} bindings for {@link AbstractRootPart}
	 * and all sub-classes. May be overwritten by sub-classes to change the
	 * default bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractRootPart} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractRootPartAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to bind by default
	}

	/**
	 * Adds (default) {@link AdapterMap} binding for {@link AbstractVisualPart}
	 * and all sub-classes. May be overwritten by sub-classes to change the
	 * default bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link AbstractVisualPart} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	protected void bindAbstractVisualPartAdapters(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// nothing to bind by default
	}

	/**
	 * Binds {@link IOperationHistory} to {@link DefaultOperationHistory} in
	 * adaptable scope of {@link IDomain}.
	 */
	protected void bindIOperationHistory() {
		binder().bind(IOperationHistory.class).to(DefaultOperationHistory.class)
				.in(AdaptableScopes.typed(IDomain.class));
	}

	/**
	 * Binds {@link IUndoContext} to {@link UndoContext} in adaptable scope of
	 * {@link IDomain}.
	 */
	protected void bindIUndoContext() {
		binder().bind(IUndoContext.class).to(UndoContext.class)
				.in(AdaptableScopes.typed(IDomain.class));
	}

	@Override
	protected void configure() {
		// TODO: could rather install a module that is provided by
		// org.eclipse.gef.common.inject (which contains the enabling code)
		enableAdapterMapInjection();

		bindIUndoContext();
		bindIOperationHistory();

		// bind domain adapters
		bindAbstractDomainAdapters(AdapterMaps.getAdapterMapBinder(binder(),
				AbstractDomain.class));

		// bind visual part adapters
		bindAbstractVisualPartAdapters(AdapterMaps.getAdapterMapBinder(binder(),
				AbstractVisualPart.class));
		bindAbstractRootPartAdapters(AdapterMaps.getAdapterMapBinder(binder(),
				AbstractRootPart.class));
		bindAbstractContentPartAdapters(AdapterMaps
				.getAdapterMapBinder(binder(), AbstractContentPart.class));
		bindAbstractFeedbackPartAdapters(AdapterMaps
				.getAdapterMapBinder(binder(), AbstractFeedbackPart.class));
		bindAbstractHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(),
				AbstractHandlePart.class));
	}

	/**
	 * Installs an {@link AdapterInjectionSupport} module, which binds an
	 * {@link AdaptableTypeListener} and ensures it gets properly injected.
	 */
	protected void enableAdapterMapInjection() {
		install(new AdapterInjectionSupport());
	}
}