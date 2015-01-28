/*******************************************************************************
 * Copyright (c) 2014 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 * Note: Parts of this interface have been transferred from org.eclipse.gef.EditDomain.
 *
 *******************************************************************************/
package org.eclipse.gef4.mvc.domain;

import java.util.Map;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.gef4.common.activate.IActivatable;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.IAdaptable;
import org.eclipse.gef4.common.dispose.IDisposable;
import org.eclipse.gef4.mvc.policies.IPolicy;
import org.eclipse.gef4.mvc.tools.ITool;
import org.eclipse.gef4.mvc.viewer.IViewer;

/**
 * A domain represents the collective state of a MVC application. It brings
 * together a set of {@link IViewer}s and related {@link ITool}s to interact
 * with these. It also holds a reference to the {@link IOperationHistory} and
 * {@link UndoContext} used by all {@link ITool} as well as {@link IPolicy}s (in
 * the {@link IViewer}s) to execute {@link IUndoableOperation}s.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 *              Instead, {@link AbstractDomain} should be sub-classed.
 *
 * @author anyssen
 *
 * @param <VR>
 *            The visual root node of the UI toolkit used, e.g.
 *            javafx.scene.Node in case of JavaFX.
 */
public interface IDomain<VR> extends IAdaptable, IActivatable, IDisposable {

	/**
	 * Closes the active execution transition (see
	 * {@link #openExecutionTransaction()}).
	 */
	public void closeExecutionTransaction();

	/**
	 * Will execute the given {@link IUndoableOperation} on the
	 * {@link IOperationHistory} used by this {@link IDomain} (see
	 * {@link #getOperationHistory()}), using the {@link IUndoContext} of this
	 * {@link IDomain}.
	 *
	 * In case an execution transition is currently open (see
	 * {@link #openExecutionTransaction()}, {@link #closeExecutionTransaction()}
	 * ) the enclosing transaction will refer to the {@link IUndoContext} used
	 * by this {@link IDomain}) (so that no specific {@link IUndoContext} is set
	 * on the passed in {@link IUndoableOperation}). If no transaction is
	 * currently open, the {@link IUndoContext} of this {@link IDomain} will be
	 * set on the passed in {@link IUndoableOperation}.
	 *
	 * @param operation
	 *            The {@link IUndoableOperation} to be executed on the
	 *            {@link IOperationHistory} of this {@link IDomain}.
	 */
	public void execute(IUndoableOperation operation);

	/**
	 * Returns the {@link IOperationHistory} that is used by this domain.
	 *
	 * @return The {@link IOperationHistory}.
	 */
	public IOperationHistory getOperationHistory();

	/**
	 * Returns the {@link ITool}s registered at this {@link IDomain} (via
	 * {@link #setAdapter(AdapterKey, Object)}) with the {@link AdapterKey}s
	 * used for registration.
	 *
	 * @return A {@link Map} containing the registered {@link ITool}s mapped to
	 *         their respective {@link AdapterKey}s.
	 *
	 * @see IAdaptable#setAdapter(AdapterKey, Object)
	 */
	public Map<AdapterKey<? extends ITool<VR>>, ITool<VR>> getTools();

	/**
	 * Returns the {@link UndoContext} that is used by this domain.
	 *
	 * @return The {@link UndoContext}.
	 */
	public IUndoContext getUndoContext();

	/**
	 * Returns the {@link IViewer}s registered at this {@link IDomain} (via
	 * {@link #setAdapter(AdapterKey, Object)}) with the {@link AdapterKey}s
	 * used for registration.
	 *
	 * @return A {@link Map} containing the registered {@link IViewer}s mapped
	 *         to their respective {@link AdapterKey}s.
	 *
	 * @see IAdaptable#setAdapter(AdapterKey, Object)
	 */
	public Map<AdapterKey<? extends IViewer<VR>>, IViewer<VR>> getViewers();

	/**
	 * Opens a new transaction for executing operations (via
	 * {@link #execute(IUndoableOperation)}) on the {@link IOperationHistory}
	 * used by this {@link IDomain} (see {@link #getOperationHistory()}), using
	 * the {@link IUndoContext} of this {@link IDomain}.
	 */
	public void openExecutionTransaction();

}