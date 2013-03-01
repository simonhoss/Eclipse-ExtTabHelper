package exttabhelper.handlers;

import java.util.Vector;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.ui.DebugPopup;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import exttabhelper.SelectTab;

public class ShowTabList extends AbstractHandler {
	Tree tree = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Rectangle rect = window.getShell().getBounds();
		DebugPopup popupDialog = new DebugPopup(window.getShell(), new Point(
				rect.width / 2, rect.height / 2), "extfullpath") {
			@Override
			protected Control createDialogArea(Composite arg0) {
				Composite parent = arg0;
				Composite composite = new Composite(parent, parent.getStyle());
				GridLayout layout = new GridLayout();
				composite.setLayout(layout);
				composite.setLayoutData(new GridData(1808));
				
				tree = new Tree(composite, parent.getStyle());

				tree.addKeyListener(new KeyListener() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						if (arg0.keyCode == 13)
							ShowTabList.this.selectSelectedTreeItem();
					}

					@Override
					public void keyPressed(KeyEvent arg0) {
					}
				});
				ShowTabList.this.tree.addMouseListener(new MouseListener() {
					@Override
					public void mouseUp(MouseEvent arg0) {
					}

					@Override
					public void mouseDown(MouseEvent arg0) {
					}

					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
						ShowTabList.this.selectSelectedTreeItem();
					}
				});
				tree.setLayoutData(new GridData(1808));

				SelectTab selectTab = new SelectTab();
				selectTab.generateLists();

				if (!selectTab.viewList.isEmpty()) {
					TreeItem treeItem = new TreeItem(ShowTabList.this.tree, 0);
					treeItem.setText("View");
					treeItem.setFont(new Font(parent.getDisplay(), "Verdana",
							8, 1));
					ShowTabList.this.addListToTreeItem(treeItem,
							selectTab.viewList);
					treeItem.setExpanded(true);
				}
				if (!selectTab.controllerList.isEmpty()) {
					TreeItem treeItem = new TreeItem(ShowTabList.this.tree, 0);
					treeItem.setFont(new Font(parent.getDisplay(), "Verdana",
							8, 1));
					treeItem.setText("Controller");
					ShowTabList.this.addListToTreeItem(treeItem,
							selectTab.controllerList);
					treeItem.setExpanded(true);
				}
				if (!selectTab.modelList.isEmpty()) {
					TreeItem treeItem = new TreeItem(ShowTabList.this.tree, 0);
					treeItem.setFont(new Font(parent.getDisplay(), "Verdana",
							8, 1));
					treeItem.setText("Model");
					ShowTabList.this.addListToTreeItem(treeItem,
							selectTab.modelList);
					treeItem.setExpanded(true);
				}
				if (!selectTab.storeList.isEmpty()) {
					TreeItem treeItem = new TreeItem(ShowTabList.this.tree, 0);
					treeItem.setFont(new Font(parent.getDisplay(), "Verdana",
							8, 1));
					treeItem.setText("Store");
					ShowTabList.this.addListToTreeItem(treeItem,
							selectTab.storeList);
					treeItem.setExpanded(true);
				}
				if (!selectTab.sonstiges.isEmpty()) {
					TreeItem treeItem = new TreeItem(ShowTabList.this.tree, 0);
					treeItem.setFont(new Font(parent.getDisplay(), "Verdana",
							8, 1));
					treeItem.setText("Other");
					ShowTabList.this.addListToTreeItem(treeItem,
							selectTab.sonstiges);
					treeItem.setExpanded(true);
				}

				return composite;
			}
		};
		popupDialog.open();

		return null;
	}

	private void selectSelectedTreeItem() {
		TreeItem[] treeItems = this.tree.getSelection();
		if (treeItems.length > 0)
			SelectTab.selectWithFilePath(treeItems[0].getText());
	}

	private void addListToTreeItem(TreeItem treeItem, Vector<String> list) {
		for (String listItem : list) {
			TreeItem treeSubItem = new TreeItem(treeItem, 0);
			treeSubItem.setText(listItem);
		}
	}
}